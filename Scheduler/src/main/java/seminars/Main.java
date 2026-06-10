package seminars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import seminars.configuration.SchedulerProperties;
import seminars.exeptions.SpaceOperationException;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(SchedulerProperties.class)
public class Main{
    public static void main(String[] args) throws SpaceOperationException {
        SpringApplication.run(Main.class, args);
    }
}
