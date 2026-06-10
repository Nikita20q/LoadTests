package seminars.factory;

import org.springframework.stereotype.Service;
import seminars.ImagingSatellite;
import seminars.Satellite;
import seminars.enums.SatelliteType;
import seminars.exeptions.SpaceOperationException;
import seminars.params.ImagingSatelliteParam;
import seminars.params.SatelliteParam;

@Service
public class ImagingSatelliteFactory extends SatelliteFactory {
    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) throws SpaceOperationException {
        if (SatelliteType.IMAGE.equals(param.getType()) && param instanceof ImagingSatelliteParam imagingSatelliteParam) {
            return new ImagingSatellite(imagingSatelliteParam.getName(), imagingSatelliteParam.getBatteryLevel(), imagingSatelliteParam.getResolution());
        }
        throw new SpaceOperationException("Некорректный тип параметров");
    }
    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return (SatelliteType.IMAGE.equals(type));
    }

}
