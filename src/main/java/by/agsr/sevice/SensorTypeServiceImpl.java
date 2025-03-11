package by.agsr.sevice;


import by.agsr.exception.NotFoundException;
import by.agsr.exception.ServiceException;
import by.agsr.exception.ValidationException;
import by.agsr.model.SensorType;
import by.agsr.model.SensorTypeDto;
import by.agsr.repository.SensorTypeRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
@Slf4j
public class SensorTypeServiceImpl implements SensorTypeService {

    final SensorTypeRepository sensorTypeRepository;

    @Override
    public void save(@Valid SensorTypeDto sensorTypeDto) {
        try {
            if (sensorTypeDto.id() != null) {
                throw new ValidationException("id должно быть пустым");
            }
            sensorTypeRepository.saveAndFlush(new SensorType(sensorTypeDto));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка сохранения типа датчика");
        }
    }

    @Override
    public void update(@Valid SensorTypeDto sensorTypeDto) {
        try {
            if (sensorTypeDto.id() == null) {
                throw new ValidationException("Не заполнена id");
            }
            if (!sensorTypeRepository.existsById(sensorTypeDto.id())) {
                throw new NotFoundException("Тип датчика с таким id не существует");
            }
            sensorTypeRepository.saveAndFlush(new SensorType(sensorTypeDto));
        } catch (NotFoundException | ValidationException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка редактирования типа датчика");
        }
    }

    @Override
    public List<SensorTypeDto> get() {
        try {
            return sensorTypeRepository.findAll()
                    .stream()
                    .map(SensorType::buildDto)
                    .toList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка получения списка типа датчиков");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            SensorType sensorType = sensorTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Тип датчика с таким id не существует"));
            sensorTypeRepository.delete(sensorType);
            sensorTypeRepository.flush();
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка удаления типа датчика");
        }
    }
}
