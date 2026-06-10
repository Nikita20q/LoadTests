package seminars.repository;
import org.springframework.stereotype.Service;
import seminars.SatelliteConstellation;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConstellationRepository {
    private final Map<String, SatelliteConstellation> constellations = new HashMap<>();
    public SatelliteConstellation getConstellation(String name) {
        SatelliteConstellation constellation = constellations.get(name);
        if (constellation == null) {
            throw new RuntimeException("Группировка не найдена: " + name);
        }
        return constellations.get(name);
    }
    public void addConstellation(SatelliteConstellation constellation) {
        constellations.put(constellation.getConstellationName(), constellation);
        System.out.println("Сохранена группировка: " + constellation.getConstellationName());
    }
    public Map<String, SatelliteConstellation> getAllConstellations() {
        return new HashMap<>(constellations);
    }
    public boolean containsConstellation(String name) {
        return constellations.containsKey(name);
    }
    public void removeConstellation(String name) {
        constellations.remove(name);
        System.out.println("Группировка удалена: " + name);
    }
    public void updateConstellation(SatelliteConstellation constellation) {
        String name = constellation.getConstellationName();
        if (!constellations.containsKey(name)) {
            throw new IllegalArgumentException("Группировка не существует: : " + name);
        }
        constellations.replace(name, constellation);
        System.out.println("Группировка обновлена: " + name);
    }
}
