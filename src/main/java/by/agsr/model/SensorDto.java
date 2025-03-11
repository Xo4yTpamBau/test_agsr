package by.agsr.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SensorDto(Long id,
                        @NotBlank(message = "Наименование датчика обязательно для заполнения")
                        @Size(min = 3, max = 30, message = "Наименования датчика должно быть от 3 до 30 символов")
                        String name,
                        @NotBlank(message = "Модель датчика обязательно для заполнения")
                        @Size(max = 15, message = "Модель датчика должна быть 15 символов")
                        String model,
                        RangeDto range,
                        SensorTypeDto type,
                        SensorUnitDto unit,
                        @Size(max = 40, message = "Расположение датчика должно быть до 40 символов")
                        String location,
                        @Size(max = 200, message = "Описание датчика должно быть до 200 символов")
                        String description) {
}
