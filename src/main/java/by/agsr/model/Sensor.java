package by.agsr.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SENSOR")
@Data
@NoArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "model")
    String model;
    @Column(name = "location")
    String location;
    @Column(name = "description")
    String description;
    @Column(name = "range_from")
    Integer rangeFrom;
    @Column(name = "range_to")
    Integer rangeTo;
    @ManyToOne
    @JoinColumn(name = "sensor_type_id", referencedColumnName = "id")
    SensorType type;
    @ManyToOne
    @JoinColumn(name = "sensor_unit_id", referencedColumnName = "id")
    SensorUnit unit;

    public Sensor(SensorDto sensorDto) {
        this.id = sensorDto.id();
        this.name = sensorDto.name();
        this.model = sensorDto.model();
        this.location = sensorDto.location();
        this.description = sensorDto.description();
        this.rangeFrom = sensorDto.range().from();
        this.rangeTo = sensorDto.range().to();
        this.type = new SensorType(sensorDto.type());
        this.unit = new SensorUnit(sensorDto.unit());
    }

    public SensorDto buildDto() {
        return new SensorDto(
                this.id,
                this.name,
                this.model,
                new RangeDto(this.rangeFrom, this.rangeTo),
                this.type.buildDto(),
                this.unit.buildDto(),
                this.location,
                this.description);
    }
}
