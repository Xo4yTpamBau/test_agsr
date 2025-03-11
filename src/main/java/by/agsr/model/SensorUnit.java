package by.agsr.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SENSOR_UNIT")
@Data
@NoArgsConstructor
public class SensorUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;

    public SensorUnit(SensorUnitDto sensorUnitDto) {
        this.id = sensorUnitDto.id();
        this.name = sensorUnitDto.name();
    }

    public SensorUnitDto buildDto() {
        return new SensorUnitDto(this.id, this.name);
    }
}
