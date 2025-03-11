package by.agsr.sevice;


import by.agsr.exception.NotFoundException;
import by.agsr.exception.ServiceException;
import by.agsr.exception.ValidationException;
import by.agsr.model.SensorUnit;
import by.agsr.model.SensorUnitDto;
import by.agsr.repository.SensorUnitRepository;
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
public class SensorUnitServiceImpl implements SensorUnitService {

    final SensorUnitRepository sensorUnitRepository;

    @Override
    public void save(@Valid SensorUnitDto sensorUnitDto) {
        try {
            if (sensorUnitDto.id() != null) {
                throw new ValidationException("id должно быть пустым");
            }
            sensorUnitRepository.saveAndFlush(new SensorUnit(sensorUnitDto));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка сохранения единицы измерения датчика");
        }
    }

    @Override
    public void update(@Valid SensorUnitDto sensorUnitDto) {
        try {
            if (sensorUnitDto.id() == null) {
                throw new ValidationException("id не заполнено");
            }
            if (!sensorUnitRepository.existsById(sensorUnitDto.id())) {
                throw new NotFoundException("Единица измерения датчика с таким id не существует");
            }
            sensorUnitRepository.saveAndFlush(new SensorUnit(sensorUnitDto));
        } catch (NotFoundException | ValidationException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка редактирования единицы измерения датчика");
        }
    }

    @Override
    public List<SensorUnitDto> get() {
        try {
            return sensorUnitRepository.findAll()
                    .stream()
                    .map(SensorUnit::buildDto)
                    .toList();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка получения списка единиц измерения датчика");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            SensorUnit sensorUnit = sensorUnitRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Единица измерения датчика с таким id не существует"));
            sensorUnitRepository.delete(sensorUnit);
            sensorUnitRepository.flush();
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ServiceException("Ошибка удаления единицы измерения датчика");
        }
    }
}
