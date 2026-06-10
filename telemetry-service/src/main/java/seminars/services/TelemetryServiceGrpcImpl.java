package seminars.services;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import seminars.Telemetry;
import seminars.TelemetryServiceGrpc;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@GrpcService
public class TelemetryServiceGrpcImpl extends TelemetryServiceGrpc.TelemetryServiceImplBase {
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();
    private static final List<Long> SATELLITE_IDS = List.of(1L, 2L, 3L);

    @Override
    public void streamTelemetry(Telemetry.TelemetryRequest request, StreamObserver<Telemetry.TelemetryUpdate> responseObserver) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                Long satId = SATELLITE_IDS.get(random.nextInt(SATELLITE_IDS.size()));
                Telemetry.TelemetryUpdate update = Telemetry.TelemetryUpdate.newBuilder()
                        .setSatelliteId(satId)
                        .setTemperatureInside(20.0 + random.nextDouble() * 10)
                        .setTemperatureOutside(-50.0 + random.nextDouble() * 30)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
                responseObserver.onNext(update);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
