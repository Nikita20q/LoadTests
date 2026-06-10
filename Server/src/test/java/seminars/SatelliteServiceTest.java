package seminars;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import seminars.exeptions.SpaceOperationException;
import seminars.params.ImagingSatelliteParam;
import seminars.params.SatelliteParam;
import seminars.services.SatelliteService;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DisplayName("Тесты сервиса")
public class SatelliteServiceTest {
    private static final String NAME = "Satellite";
    private static final Double BATTERY_LEVEL = 60.0;
    private static final Double PARAM = 0.1;

    @Autowired
    private SatelliteService satelliteService;

    @Test
    @DisplayName("Добавление нескольких группировок должно сохранять их все")
    void addAllConstellations() throws SpaceOperationException {
        SatelliteParam imagingParam = new ImagingSatelliteParam(NAME, BATTERY_LEVEL, PARAM);
        Satellite satellite = satelliteService.createSatellite(imagingParam);

        assertNotNull(satellite);
        assertInstanceOf(ImagingSatellite.class, satellite);
        ImagingSatellite imagingSatellite = (ImagingSatellite) satellite;

        assertEquals(NAME, imagingSatellite.getName());
        assertEquals(BATTERY_LEVEL, imagingSatellite.getEnergy().getBatteryLevel());
        assertEquals(PARAM, imagingSatellite.getResolution());
    }
}
