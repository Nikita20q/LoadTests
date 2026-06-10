package seminars.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import seminars.domain.requests.MissionRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceOperationClient {
    private final RestClient restClient;
    public void executeMission(MissionRequest missionRequest) {
        try {
            restClient.post()
                    .uri("/missions")
                    .body(missionRequest)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            log.error("Ошибка при вызове {}: {}", getClass().getSimpleName(), e.getMessage(), e);
        }
    }
}
