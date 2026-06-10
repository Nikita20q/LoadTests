package seminars.domain.requests;

import seminars.Satellite;
import seminars.params.SatelliteParam;

import java.util.List;

public record AddSatelliteRequest(String constellationName, List<SatelliteParam> satelliteParams) {}
