package seminars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seminars.exeptions.SpaceOperationException;
import seminars.factory.ImagingSatelliteFactory;
import seminars.params.ImagingSatelliteParam;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-тесты ImagingSatelliteFactory")
public class ImagingSatelliteFactoryUnitTest {
    private static final String SATELLITE_NAME = "Satellite";
    private static final double RESOLUTION = 101.4;
    private static final double BATTERY_LEVEL = 75.0;
    private ImagingSatelliteFactory satelliteFactory;

    @BeforeEach
    void setUp() {
        satelliteFactory = new ImagingSatelliteFactory();
    }

    @Test
    @DisplayName("Создание ImagingSatellite с корректными параметрами")
    void testCreateImagingSatellite() throws SpaceOperationException {
        ImagingSatellite satellite = (ImagingSatellite) satelliteFactory.createSatelliteWithParameter(new ImagingSatelliteParam(SATELLITE_NAME, BATTERY_LEVEL, RESOLUTION));
        assertNotNull(satellite);
        assertInstanceOf(ImagingSatellite.class, satellite);
        assertEquals(SATELLITE_NAME, satellite.getName());
        assertEquals(BATTERY_LEVEL, satellite.getEnergy().getBatteryLevel());
        assertEquals(RESOLUTION, satellite.getResolution());
    }

    @Test
    @DisplayName("Создание спутника с минимальным зарядом батареи")
    void testCreateSatelliteWithMinBatteryLevel() throws SpaceOperationException {
        ImagingSatellite satellite = (ImagingSatellite) satelliteFactory.createSatelliteWithParameter(new ImagingSatelliteParam(SATELLITE_NAME, 0, RESOLUTION));
        assertEquals(0.0, satellite.getEnergy().getBatteryLevel());
        assertEquals(RESOLUTION, satellite.getResolution());
        assertEquals(SATELLITE_NAME, satellite.getName());
    }
}
