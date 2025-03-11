package by.agsr.controller;

import by.agsr.model.PaginationPage;
import by.agsr.model.SensorDto;
import by.agsr.sevice.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sensors")
@AllArgsConstructor
public class SensorController {

    final SensorService sensorService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<Void> save(@RequestBody SensorDto sensorDto) {
        sensorService.save(sensorDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<Void> update(@RequestBody SensorDto sensorDto) {
        sensorService.update(sensorDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(by.agsr.constant.Authority).Administrator.name())")
    public ResponseEntity<SensorDto> delete(@PathVariable Long id) {
        sensorService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority("
            + "T(by.agsr.constant.Authority).Administrator.name(), "
            + "T(by.agsr.constant.Authority).Viewer.name())")
    public ResponseEntity<PaginationPage<SensorDto>> search(@RequestParam(defaultValue = "1") Integer page,
                                                            @RequestParam(defaultValue = "20") Integer size,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String model) {
        return ResponseEntity.ok(sensorService.search(name, model, page, size));
    }
}
