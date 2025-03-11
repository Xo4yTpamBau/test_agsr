package by.agsr.model;

import jakarta.validation.constraints.NotBlank;

public record SensorTypeDto(Long id,
                            @NotBlank(message = "Название типа датчика обязательно для заполнения")
                            String name) {
}
