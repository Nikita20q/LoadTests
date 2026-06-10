package seminars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seminars.Satellite;
import seminars.exeptions.SpaceOperationException;
import seminars.factory.SatelliteFactory;
import seminars.params.SatelliteParam;

import java.util.List;

interface SatelliteService {
    Satellite createSatellite(SatelliteParam param) throws SpaceOperationException;
}
