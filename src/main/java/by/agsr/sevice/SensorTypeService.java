package by.agsr.sevice;

import by.agsr.model.SensorTypeDto;
import jakarta.validation.Valid;

import java.util.List;

public interface SensorTypeService {
    void save(@Valid SensorTypeDto sensorTypeDto);

    void update(@Valid SensorTypeDto sensorTypeDto);

    List<SensorTypeDto> get();

    void delete(Long id);
}
