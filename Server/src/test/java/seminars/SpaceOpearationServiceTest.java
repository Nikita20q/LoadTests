package seminars;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seminars.domain.requests.AddSatelliteRequest;
import seminars.domain.requests.MissionRequest;
import seminars.domain.requests.MissionTargetType;
import seminars.exeptions.SpaceOperationException;
import seminars.params.CommunicationSatelliteParam;
import seminars.params.ImagingSatelliteParam;
import seminars.services.SpaceOperationCenterService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DisplayName("Интеграционные тесты фасада SpaceOpearationService")
public class SpaceOpearationServiceTest {
    @Autowired
    private SpaceOperationCenterService spaceOperationCenterService;

    @Autowired
    private ConstellationRepository constellationRepository;

    private String uniqueName(String base) {
        return base + "_" + System.currentTimeMillis();
    }

    @Test
    @DisplayName("Добавление спутников в группировку через фасад")
    void addSatelliteTest() throws SpaceOperationException {
        String constellationName = uniqueName("constellation");
        String commSatName = "CommSat-1";
        String imSatName = "ImSat-1";

        var commParam = new CommunicationSatelliteParam(commSatName, 100.0, 500.0);
        var imParam = new ImagingSatelliteParam(imSatName, 90.0, 2.5);
        var request = new AddSatelliteRequest(constellationName, List.of(commParam, imParam));

        spaceOperationCenterService.addSatellite(request);

        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        assertNotNull(constellation, "Группировка должна существовать");
        assertEquals(2, constellation.getSatellites().size(), "Должно быть 2 спутника");

        List<String> satelliteName = constellation.getSatellites().stream()
                .map(Satellite::getName)
                .toList();
        assertTrue(satelliteName.contains(commSatName));
        assertTrue(satelliteName.contains(imSatName));
    }

    @Test
    @DisplayName("Выполнение миссии для всей группировки")
    void executeConstellationMissionTest() throws SpaceOperationException {
        String constellationName = uniqueName("MissionConstellation");
        String satName = "MissionSat-1";

        var commParam = new CommunicationSatelliteParam(constellationName, 100.0, 500.0);
        var addRequest = new AddSatelliteRequest(constellationName, List.of(commParam));

        spaceOperationCenterService.addSatellite(addRequest);

        var missionRequst = new MissionRequest(MissionTargetType.CONSTELLATION, constellationName, null);

        spaceOperationCenterService.executeMission(missionRequst);

        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        Satellite satellite = constellation.getSatellites().get(0);
        assertTrue(satellite.getState().isActive());
    }
}
