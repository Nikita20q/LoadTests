package seminars.telemetry;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import seminars.Telemetry;
import seminars.TelemetryServiceGrpc;
import seminars.repository.SatelliteRepository;

import jakarta.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryClientService {
    @GrpcClient("telemetry-service")
    private TelemetryServiceGrpc.TelemetryServiceStub asyncStub;

    private final SatelliteRepository satelliteRepository;

    @PostConstruct
    public void startStreaming() {
        log.info("Starting Telemetry Client");
        Telemetry.TelemetryRequest request = Telemetry.TelemetryRequest.getDefaultInstance();

        StreamObserver<Telemetry.TelemetryUpdate> responseObserver = new StreamObserver<Telemetry.TelemetryUpdate>() {
            @Override
            public void onNext(Telemetry.TelemetryUpdate telemetryUpdate) {
                log.info(telemetryUpdate.toString());
                satelliteRepository.findById(telemetryUpdate.getSatelliteId()).ifPresent(satellite -> {
                    satellite.setTemperatureInside(telemetryUpdate.getTemperatureInside());
                    satellite.setTemperatureOutside(telemetryUpdate.getTemperatureOutside());
                    satelliteRepository.save(satellite);
                });
            }
            @Override
            public void onError(Throwable t) {
                log.info("Ошибка стрима телеметрии", t);
            }

            @Override
            public void onCompleted() {
                log.info("Стрим завершён");
            }
        };
        asyncStub.streamTelemetry(request, responseObserver);
    }
}
