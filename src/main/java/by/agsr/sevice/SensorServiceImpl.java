package by.agsr.sevice;

import by.agsr.exception.NotFoundException;
import by.agsr.exception.ServiceException;
import by.agsr.exception.ValidationException;
import by.agsr.model.PaginationPage;
import by.agsr.model.RangeDto;
import by.agsr.model.Sensor;
import by.agsr.model.SensorDto;
import by.agsr.repository.SensorRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
@Slf4j
public class SensorServiceImpl implements SensorService {

    final SensorRepository sensorRepository;

    @Override
    public void save(@Valid SensorDto sensorDto) {
        try {
            validationRange(sensorDto.range());
            if (sensorDto.id() != null) {
                throw new ValidationException("id должно быть пустым");
            }
            sensorRepository.saveAndFlush(new Sensor(sensorDto));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка сохранения датчика");
        }
    }

    @Override
    public void update(@Valid SensorDto sensorDto) {
        try {
            validationRange(sensorDto.range());
            if (sensorDto.id() == null) {
                throw new ValidationException("id не заполнено");
            }
            if (!sensorRepository.existsById(sensorDto.id())) {
                throw new NotFoundException("Датчик с таким id не существует");
            }
            sensorRepository.saveAndFlush(new Sensor(sensorDto));
        } catch (NotFoundException | ValidationException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка редактирования датчика");
        }
    }

    private void validationRange(RangeDto rangeDto) {
        if ((rangeDto.from() != null && rangeDto.to() != null) && (rangeDto.from() > rangeDto.to())) {
            throw new ValidationException("Нижнее значение диапазона должно быть меньше верхнего");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Sensor sensor = sensorRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Датчик с таким id не существует"));
            sensorRepository.delete(sensor);
            sensorRepository.flush();
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка удаления датчика");
        }
    }

    @Override
    public PaginationPage<SensorDto> search(String name, String model, int page, int size) {
        try {
            Page<Sensor> resultPage = sensorRepository.findAll(
                    Specification.where(hasName(name)).and(hasModel(model)),
                    PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "id")));

            var paginationPage = new PaginationPage<SensorDto>(page, size, resultPage.getTotalElements());

            return paginationPage.setContent(resultPage.getContent()
                    .stream()
                    .map(Sensor::buildDto)
                    .toList());
        } catch (RuntimeException e) {
            throw new ServiceException("Ошибка получения списка датчиков");
        }
    }

    private Specification<Sensor> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name + "%");
        };
    }

    private Specification<Sensor> hasModel(String model) {
        return (root, query, criteriaBuilder) -> {
            if (model == null || model.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("model")), "%" + model + "%");
        };
    }
}
