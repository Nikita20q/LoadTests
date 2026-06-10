package seminars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seminars.Satellite;
import seminars.SatelliteConstellation;
import seminars.exeptions.SpaceOperationException;
import seminars.repository.ConstellationRepository;
import seminars.repository.SatelliteRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ConstellationService {
    private final ConstellationRepository constellationRepository;
    private final SatelliteRepository satelliteRepository;

    public void addSatelliteToConstellation(String constellationName, Satellite satellite) throws SpaceOperationException {
        SatelliteConstellation constellation = constellationRepository.findByConstellationName(constellationName)
                .orElseThrow(() -> new SpaceOperationException("Группировка не найдена: " + constellationName));

        constellation.addSatellite(satellite);
        satellite.setConstellation(constellation);

        satelliteRepository.save(satellite);
    }

    public void createAndSaveConstellation(String constellationName) {
        SatelliteConstellation constellation = new SatelliteConstellation(constellationName);
        constellationRepository.save(constellation);
    }
    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.findByConstellationName(constellationName)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена"));
        System.out.println("\n===ВЫПОЛНЕНИЕ МИССИЙ ДЛЯ ГРУППИРОВКИ: " +  constellationName + "===");
        constellation.executeAllMission();
    }
    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.findByConstellationName(constellationName)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена"));
        System.out.println("\n=== АКТИВАЦИЯ СПУТНИКОВ В ГРУППИРОВКЕ: " + constellationName + " ===");
        for  (Satellite satellite : constellation.getSatellites()) {
            satellite.activate();
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.findByConstellationName(constellationName)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена"));
        System.out.println("\n=== СТАТУС ГРУППИРОВКИ:  " + constellationName + " ===");
        System.out.println("Количество спутников: " + constellation.getSatellites().size());
        for (Satellite satellite : constellation.getSatellites()) {
            System.out.println(satellite.getState());
        }
    }

    public boolean checkConstellationExists(String constellationName) {
        try {
            return constellationRepository.findByConstellationName(constellationName) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public SatelliteConstellation getConstellationByName(String constellationName) {
        return constellationRepository.findByConstellationName(constellationName)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена"));
    }

    @Transactional(readOnly = true)
    public SatelliteConstellation getConstellationById(Long id) {
        return constellationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена"));
    }

    @Transactional(readOnly = true)
    public List<SatelliteConstellation> getAllConstellations() {
        return constellationRepository.findAll();
    }

    public SatelliteConstellation createConstellation(String constellationName) {
        SatelliteConstellation constellation = new SatelliteConstellation(constellationName);
        return constellationRepository.save(constellation);
    }

    public void deleteConstellation(Long id) {
        if (!constellationRepository.existsById(id)) {
            throw new RuntimeException("Группировка с id" + id + " не найдена");
        }
        constellationRepository.deleteById(id);
    }
    public void removeSatelliteFromConstellation(String constellationName, String satelliteName) throws SpaceOperationException {
        SatelliteConstellation constellation = constellationRepository.findByConstellationName(constellationName)
                .orElseThrow(() -> new SpaceOperationException("Группировка не найдена: " + constellationName));

        Satellite satellite = constellation.getSatellites().stream()
                .filter(s -> s.getName().equals(satelliteName))
                .findFirst()
                .orElseThrow(() -> new SpaceOperationException(
                        String.format("Спутник '%s' не найден в группировке '%s'", satelliteName, constellationName)));

        constellation.getSatellites().remove(satellite);
        satellite.setConstellation(null);
        satelliteRepository.delete(satellite);
    }
}
