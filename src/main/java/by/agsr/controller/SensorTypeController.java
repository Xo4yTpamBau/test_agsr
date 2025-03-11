package by.agsr.controller;

import by.agsr.model.SensorTypeDto;
import by.agsr.sevice.SensorTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sensors/types")
@AllArgsConstructor
public class SensorTypeController {

    final SensorTypeService sensorTypeService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<Void> save(@RequestBody SensorTypeDto sensorTypeDto) {
        sensorTypeService.save(sensorTypeDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<Void> update(@RequestBody SensorTypeDto sensorTypeDto) {
        sensorTypeService.update(sensorTypeDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<List<SensorTypeDto>> get() {
        return ResponseEntity.ok(sensorTypeService.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<SensorTypeDto> delete(@PathVariable Long id) {
        sensorTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
