package seminars.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seminars.domain.requests.AddSatelliteRequest;
import seminars.domain.requests.MissionRequest;
import seminars.exeptions.SpaceOperationException;
import seminars.services.SpaceOperationCenterService;

@RestController
@RequestMapping("api")
public class SpaceOperationController {
    private final SpaceOperationCenterService spaceOperationCenterService;

    public SpaceOperationController(SpaceOperationCenterService spaceOperationCenterService) {
        this.spaceOperationCenterService = spaceOperationCenterService;
    }

    @PostMapping("/missions")
    public ResponseEntity<Void> executeMission(@RequestBody MissionRequest request) {
        spaceOperationCenterService.executeMission(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-satellites")
    public ResponseEntity<Void> addSatellite(@RequestBody AddSatelliteRequest request) throws SpaceOperationException {
        spaceOperationCenterService.addSatellite(request);
        return ResponseEntity.ok().build();
    }
}
