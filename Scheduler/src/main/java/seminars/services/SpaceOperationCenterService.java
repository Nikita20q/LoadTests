package seminars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seminars.Satellite;
import seminars.aop.LogExecutionTime;
import seminars.domain.requests.AddSatelliteRequest;
import seminars.domain.requests.MissionRequest;
import seminars.exeptions.SpaceOperationException;
import seminars.params.SatelliteParam;

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
                constellationService.activateAllConstellation(missionRequest.constallationName());
                constellationService.executeConstellationMission(missionRequest.constallationName());
            }
            case SINGLE_SATELLITE -> {
                var constallation = constellationService.getConstellations(missionRequest.constallationName());
                var satellite = constallation.getSatellites().stream()
                        .filter(s -> s.getName().equals(missionRequest.satelliteName()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Спутник не найден " + missionRequest.satelliteName()));
                satellite.activate();
            }
        }
    }
}
