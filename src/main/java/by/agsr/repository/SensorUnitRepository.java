package by.agsr.repository;

import by.agsr.model.SensorType;
import by.agsr.model.SensorUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorUnitRepository extends JpaRepository<SensorUnit, Long> {

}