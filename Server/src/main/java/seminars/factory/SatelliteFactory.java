package seminars.factory;

import seminars.Satellite;
import seminars.enums.SatelliteType;
import seminars.exeptions.SpaceOperationException;
import seminars.params.SatelliteParam;

public abstract class SatelliteFactory {

    public abstract Satellite createSatelliteWithParameter(SatelliteParam param) throws SpaceOperationException;
    public abstract boolean isSatelliteTypeSupported(SatelliteType type);
}
