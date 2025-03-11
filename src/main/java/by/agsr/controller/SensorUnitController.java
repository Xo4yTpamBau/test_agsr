package by.agsr.controller;

import by.agsr.model.SensorUnitDto;
import by.agsr.sevice.SensorUnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sensors/units")
@AllArgsConstructor
public class SensorUnitController {

    final SensorUnitService sensorUnitService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<Void> save(@RequestBody SensorUnitDto sensorUnitDto) {
        sensorUnitService.save(sensorUnitDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<Void> update(@RequestBody SensorUnitDto sensorUnitDto) {
        sensorUnitService.update(sensorUnitDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<List<SensorUnitDto>> get() {
        return ResponseEntity.ok(sensorUnitService.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<SensorUnitDto> delete(@PathVariable Long id) {
        sensorUnitService.delete(id);
        return ResponseEntity.ok().build();
    }

}
