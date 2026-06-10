package seminars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seminars.Satellite;
import seminars.SatelliteConstellation;
import seminars.aop.LogExecutionTime;
import seminars.domain.requests.AddSatelliteRequest;
import seminars.domain.requests.MissionRequest;
import seminars.exeptions.SpaceOperationException;
import seminars.params.SatelliteParam;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class SpaceOperationCenterService {

    private final ConstellationService constellationService;
    private final SatelliteService satelliteService;
    @LogExecutionTime
    public void addSatellite(AddSatelliteRequest addSatelliteRequest) throws SpaceOperationException {
        if (!constellationService.checkConstellationExists(addSatelliteRequest.constellationName())) {
            constellationService.createAndSaveConstellation(addSatelliteRequest.constellationName());
        }

        for (SatelliteParam satelliteParam : addSatelliteRequest.satelliteParams()) {
            Satellite satellite = satelliteService.createSatellite(satelliteParam);
            constellationService.addSatelliteToConstellation(addSatelliteRequest.constellationName(), satellite);
        }
    }

    public void executeMission(MissionRequest missionRequest) {
        switch (missionRequest.missionTargetType()) {
            case CONSTELLATION -> {
                constellationService.activateAllSatellites(missionRequest.constallationName());
                constellationService.executeConstellationMission(missionRequest.constallationName());
            }
            case SINGLE_SATELLITE -> {
                var constellation = constellationService.getConstellationByName(missionRequest.constallationName());
                var satellite = constellation.getSatellites().stream()
                        .filter(s -> s.getName().equals(missionRequest.satelliteName()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Спутник не найден: " + missionRequest.satelliteName()));
                satellite.activate();
            }
        }
    }

    public String getSystemOverview() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== СИСТЕМНАЯ СВОДКА ===\n\n");

        Collection<SatelliteConstellation> constellations = constellationService.getAllConstellations();

        sb.append("Всего группировок: ").append(constellations.size()).append("\n\n");

        for (var constellation : constellations) {
            sb.append("▸ Группировка: ").append(constellation.getConstellationName()).append("\n");
            sb.append("  Спутников: ").append(constellation.getSatellites().size()).append("\n");

            for (var satellite : constellation.getSatellites()) {
                sb.append("  • ")
                        .append(satellite.getName())
                        .append(" [")
                        .append(satellite.getClass().getSimpleName())
                        .append("] — ")
                        .append(satellite.isActive() ? "🟢 АКТИВЕН" : "🔴 НЕ АКТИВЕН")
                        .append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @LogExecutionTime
    public void decommissionSatellite(String constellationName, String satelliteName)
            throws SpaceOperationException {

        if (!constellationService.checkConstellationExists(constellationName)) {
            throw new SpaceOperationException("Группировка не найдена: " + constellationName);
        }

        constellationService.removeSatelliteFromConstellation(constellationName, satelliteName);
    }
}
