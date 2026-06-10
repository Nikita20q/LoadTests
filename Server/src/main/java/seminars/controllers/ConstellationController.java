package seminars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminars.Satellite;
import seminars.SatelliteConstellation;
import seminars.exeptions.SpaceOperationException;
import seminars.params.SatelliteParam;
import seminars.services.ConstellationService;
import seminars.services.SatelliteService;

import java.util.List;

@RestController
@RequestMapping("/api/constellations")
@RequiredArgsConstructor
public class ConstellationController {
    private final ConstellationService constellationService;
    private final SatelliteService satelliteService;

    public record CreateConstellationRequest(String name) {}
    public record AddSatelliteRequest(SatelliteParam satelliteParam) {}

    @PostMapping
    public ResponseEntity<SatelliteConstellation> createConstellation(@RequestBody CreateConstellationRequest request) {
        SatelliteConstellation created = constellationService.createConstellation(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<SatelliteConstellation> getAllConstellations() {
        return constellationService.getAllConstellations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SatelliteConstellation> getConstellationById(@PathVariable Long id) {
        SatelliteConstellation constellation = constellationService.getConstellationById(id);
        return ResponseEntity.ok(constellation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SatelliteConstellation> deleteConstellationById(@PathVariable Long id) {
        constellationService.deleteConstellation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{name}/satellites")
    public ResponseEntity<Satellite> addSatelliteToConstellation(
            @PathVariable String name,
            @RequestBody SatelliteParam param) throws SpaceOperationException {

        Satellite satellite = satelliteService.createSatellite(param);
        constellationService.addSatelliteToConstellation(name, satellite);

        return ResponseEntity.status(HttpStatus.CREATED).body(satellite);
    }
}
