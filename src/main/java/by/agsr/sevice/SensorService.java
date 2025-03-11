package by.agsr.sevice;

import by.agsr.model.PaginationPage;
import by.agsr.model.SensorDto;
import jakarta.validation.Valid;

public interface SensorService {

    void save(@Valid SensorDto sensorDto);

    void update(@Valid SensorDto sensorDto);

    void delete(Long id);

    PaginationPage<SensorDto> search(String name, String model, int page, int size);
}
