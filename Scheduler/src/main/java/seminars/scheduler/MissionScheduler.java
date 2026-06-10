package seminars.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import seminars.client.SpaceOperationClient;
import seminars.configuration.SchedulerProperties;
import seminars.domain.requests.MissionRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class MissionScheduler {

    private final TaskScheduler taskScheduler;
    private final SpaceOperationClient client;
    private final SchedulerProperties properties;

    @EventListener(ApplicationReadyEvent.class)
    public void scheduleMissions() {
        if (properties.missions() == null || properties.missions().isEmpty()) {
            log.warn("Нет запланированных миссий в конфигурации");
            return;
        }

        log.info("Регистрация {} запланированных миссий...", properties.missions().size());

        for (var mission : properties.missions()) {
            try {
                CronTrigger trigger = new CronTrigger(mission.cron());

                taskScheduler.schedule(() -> executeMission(mission), trigger);

                log.info("Запланировано: тип={} | группировка={} | спутник={} | cron={}",
                        mission.targetType(),
                        mission.constellationName(),
                        mission.satelliteName() != null ? mission.satelliteName() : "-",
                        mission.cron());

            } catch (Exception e) {
                log.error("Ошибка планирования миссии {}: {}", mission, e.getMessage(), e);
            }
        }
    }

    private void executeMission(SchedulerProperties.ScheduledMission mission) {
        try {
            log.info("Запуск миссии: {} для {}",
                    mission.targetType(), mission.constellationName());

            var request = new MissionRequest(
                    mission.targetType(),
                    mission.constellationName(),
                    mission.satelliteName()
            );

            client.executeMission(request);

            log.info("Миссия отправлена успешно");

        } catch (Exception e) {
            log.error("Ошибка выполнения миссии {}: {}", mission, e.getMessage(), e);
        }
    }
}