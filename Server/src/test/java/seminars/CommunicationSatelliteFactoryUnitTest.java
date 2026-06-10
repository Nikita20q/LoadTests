package seminars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seminars.exeptions.SpaceOperationException;
import seminars.factory.CommunicationSatelliteFactory;
import seminars.factory.ImagingSatelliteFactory;
import seminars.params.CommunicationSatelliteParam;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-тесты CommunicationSatelliteFactory")
public class CommunicationSatelliteFactoryUnitTest {
    private static final String SATELLITE_NAME = "Satellite";
    private static final double BAND_WITH = 52.4;
    private static final double BATTERY_LEVEL = 75.0;
    private CommunicationSatelliteFactory satelliteFactory;

    @BeforeEach
    void setUp() {
        satelliteFactory = new CommunicationSatelliteFactory();
    }

    @Test
    @DisplayName("Создание CommunicationSatellite с корректными параметрами")
    void testCreateCommunicationSatellite() throws SpaceOperationException {
        CommunicationSatellite satellite = (CommunicationSatellite) satelliteFactory.createSatelliteWithParameter(new CommunicationSatelliteParam(SATELLITE_NAME, BATTERY_LEVEL, BAND_WITH));
        assertNotNull(satellite);
        assertInstanceOf(CommunicationSatellite.class, satellite);
        assertEquals(SATELLITE_NAME, satellite.getName());
        assertEquals(BAND_WITH, satellite.getBandWidth());
        assertEquals(BATTERY_LEVEL, satellite.getEnergy().getBatteryLevel());
    }

    @Test
    @DisplayName("Создание спутника с минимальным зарядом батареи")
    void testCreateSatelliteWithMinBatteryLevel() throws SpaceOperationException {
        CommunicationSatellite satellite = (CommunicationSatellite) satelliteFactory.createSatelliteWithParameter(new CommunicationSatelliteParam(SATELLITE_NAME, 0, BAND_WITH));
        assertEquals(0.0, satellite.getEnergy().getBatteryLevel());
        assertEquals(BAND_WITH, satellite.getBandWidth());
        assertEquals(SATELLITE_NAME, satellite.getName());
    }
}
