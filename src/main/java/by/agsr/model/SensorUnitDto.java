package by.agsr.model;

import jakarta.validation.constraints.NotBlank;

public record SensorUnitDto(Long id,
                            @NotBlank(message = "Название единицы измерения датчика обязательно для заполнения")
                            String name) {
}
