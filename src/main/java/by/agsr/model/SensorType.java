package by.agsr.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SENSOR_TYPE")
@Data
@NoArgsConstructor
public class SensorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;

    public SensorType(SensorTypeDto sensorTypeDto) {
        this.id = sensorTypeDto.id();
        this.name = sensorTypeDto.name();
    }

    public SensorTypeDto buildDto() {
        return new SensorTypeDto(this.id, this.name);
    }
}
