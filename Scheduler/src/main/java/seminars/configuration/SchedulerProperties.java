package seminars.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import seminars.domain.requests.MissionTargetType;

import java.util.List;

@ConfigurationProperties(prefix = "app.space-center-service")
public record SchedulerProperties(
        String url,
        List<ScheduledMission> missions
) {
    public record ScheduledMission(
            MissionTargetType targetType,
            String constellationName,
            String satelliteName,
            String cron
    ) {}
}