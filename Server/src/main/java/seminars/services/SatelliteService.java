package seminars.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seminars.Satellite;
import seminars.exeptions.SpaceOperationException;
import seminars.factory.SatelliteFactory;
import seminars.params.SatelliteParam;
import seminars.repository.SatelliteRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SatelliteService {
    private final List<SatelliteFactory> factories;
    private final SatelliteRepository satelliteRepository;

    public Satellite createSatellite(SatelliteParam param) throws SpaceOperationException {
        SatelliteFactory factory = factories.stream()
                .filter(f -> f.isSatelliteTypeSupported(param.getType()))
                .findFirst()
                .orElseThrow(() -> new SpaceOperationException("Неподдерживаемый тип спутника: " + param.getType()));

        Satellite satellite = factory.createSatelliteWithParameter(param);
        return satelliteRepository.save(satellite);
    }

    @Transactional(readOnly = true)
    public List<Satellite> getAllSatellites() {
        return satelliteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Satellite> getSatelliteById(Long id) {
        return satelliteRepository.findById(id);
    }

    public Satellite updateSatellite(Long id, Satellite upadatedSatellite) {
        if (!satelliteRepository.existsById(id)) {
            throw new RuntimeException("Спутник с id " + id + " не найден");
        }
        upadatedSatellite.setId(id);
        return satelliteRepository.save(upadatedSatellite);
    }

    public void deleteSatelliteById(Long id) {
        if (!satelliteRepository.existsById(id)) {
            throw new RuntimeException("Спутник с id=" + id + " не найден");
        }
        satelliteRepository.deleteById(id);
    }
}
