package seminars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seminars.Satellite;
import seminars.exeptions.SpaceOperationException;
import seminars.factory.SatelliteFactory;
import seminars.params.SatelliteParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SatelliteServiceImpl implements SatelliteService {
    private final List<SatelliteFactory> factories;

    @Override
    public Satellite createSatellite(SatelliteParam param) throws SpaceOperationException {
        SatelliteFactory factory = factories.stream()
            .filter(satelliteFactory -> satelliteFactory.isSatelliteTypeSupported(param.getType()))
            .findFirst()
            .orElseThrow(() -> new SpaceOperationException("Некорректный тип параметров"));
        return factory.createSatelliteWithParameter(param);
    }
}
