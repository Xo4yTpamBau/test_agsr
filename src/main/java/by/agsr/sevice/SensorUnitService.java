package by.agsr.sevice;

import by.agsr.model.SensorUnitDto;
import jakarta.validation.Valid;

import java.util.List;

public interface SensorUnitService {
    void save(@Valid SensorUnitDto sensorUnitDto);

    void update(@Valid SensorUnitDto sensorUnitDto);

    List<SensorUnitDto> get();

    void delete(Long id);
}
