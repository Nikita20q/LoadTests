package seminars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminars.Satellite;
import seminars.exeptions.SpaceOperationException;
import seminars.params.SatelliteParam;
import seminars.services.SatelliteService;

import java.util.List;

@RestController
@RequestMapping("/api/satellites")
@RequiredArgsConstructor
public class SatelliteController {
    private final SatelliteService satelliteService;

    @PostMapping
    public ResponseEntity<Satellite> createSatellite(@RequestBody SatelliteParam satelliteParam) throws SpaceOperationException {
        Satellite createdSatellite = satelliteService.createSatellite(satelliteParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSatellite);
    }

    @GetMapping
    public List<Satellite> getAllSatellites() {
        return satelliteService.getAllSatellites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Satellite> getSatelliteById(@PathVariable Long id) {
        return satelliteService.getSatelliteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Satellite> updateSatellite(@PathVariable Long id, @RequestBody Satellite satellite) {
        Satellite updatedSatellite = satelliteService.updateSatellite(id, satellite);
        return ResponseEntity.ok(updatedSatellite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Satellite> deleteSatellite(@PathVariable Long id) {
        satelliteService.deleteSatelliteById(id);
        return ResponseEntity.noContent().build();
    }
}
