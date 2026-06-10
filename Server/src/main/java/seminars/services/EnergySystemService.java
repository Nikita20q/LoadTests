package seminars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seminars.EnergySystem;
import seminars.Satellite;
import seminars.SatelliteConstellation;
import seminars.repository.EnergySystemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnergySystemService {
    private final EnergySystemRepository energySystemRepository;

    @Transactional(readOnly = true)
    public List<EnergySystem> getAllEnergySystems() {
        return energySystemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EnergySystem> getEnergySystemById(Long id) {
        return energySystemRepository.findById(id);
    }

    public EnergySystem updateEnergySystem(Long id, EnergySystem energySystem) {
        EnergySystem oldEnergySystem = energySystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Энергосистема с id=" + id + " не найдена"));
        oldEnergySystem.setBatteryLevel(energySystem.getBatteryLevel());
        oldEnergySystem.setLOW_BATTERY_THRESHOLD(energySystem.getLOW_BATTERY_THRESHOLD());
        oldEnergySystem.setMAX_BATTERY(energySystem.getMAX_BATTERY());
        oldEnergySystem.setMIN_BATTERY(energySystem.getMIN_BATTERY());

        return energySystemRepository.save(oldEnergySystem);
    }
}
