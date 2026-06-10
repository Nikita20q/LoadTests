package seminars.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminars.EnergySystem;
import seminars.services.EnergySystemService;

import java.util.List;

@RestController
@RequestMapping("/api/energy-systems")
@RequiredArgsConstructor
public class EnergySystemController {
    private final EnergySystemService energySystemService;

    @GetMapping
    public List<EnergySystem> getAllEnergySystems() {
        return energySystemService.getAllEnergySystems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergySystem> getEnergySystemById(@PathVariable Long id) {
        return energySystemService.getEnergySystemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<EnergySystem> updateEnergySystem(@PathVariable Long id, @RequestBody EnergySystem energySystem) {
        EnergySystem updatedEnergySystem = energySystemService.updateEnergySystem(id, energySystem);
        return ResponseEntity.ok(updatedEnergySystem);
    }

}
