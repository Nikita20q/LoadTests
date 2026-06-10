package seminars.factory;

import org.springframework.stereotype.Service;
import seminars.CommunicationSatellite;
import seminars.ImagingSatellite;
import seminars.Satellite;
import seminars.enums.SatelliteType;
import seminars.exeptions.SpaceOperationException;
import seminars.params.CommunicationSatelliteParam;
import seminars.params.ImagingSatelliteParam;
import seminars.params.SatelliteParam;

@Service
public class CommunicationSatelliteFactory extends SatelliteFactory{

    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) throws SpaceOperationException {
        if (SatelliteType.COMMUNICATION.equals(param.getType()) && param instanceof CommunicationSatelliteParam communicationSatelliteParam) {
            return new CommunicationSatellite(communicationSatelliteParam.getName(), communicationSatelliteParam.getBatteryLevel(), communicationSatelliteParam.getBandwidth());
        }
        throw new SpaceOperationException("Некорректный тип параметров");
    }

    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return  (SatelliteType.COMMUNICATION.equals(type));
    }
}
