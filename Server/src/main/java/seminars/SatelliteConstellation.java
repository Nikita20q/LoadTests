package seminars;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "satellite_constellation")
@Data
@NoArgsConstructor
public class SatelliteConstellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name", nullable = false, unique = true)
    private String constellationName;

    @JsonManagedReference
    @OneToMany(mappedBy = "constellation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Satellite> satellites = new ArrayList<>();

    public SatelliteConstellation(String name) {
        constellationName = name;
        System.out.println("---------------------------------------------");
        System.out.println("Создана спутниковая группировка: " + constellationName);
        System.out.println("---------------------------------------------");
    }

    public void addSatellite(Satellite satellite) {
        satellites.add(satellite);
        System.out.println(satellite.name + " добавлен в группировку " + "'" + constellationName + "'");
    }
    public void executeAllMission() {
        for (var i : satellites) {
            i.performMission();
        }
    }
    public List<Satellite> getSatellites() {
        return satellites;
    }
    public String getConstellationName() {
        return constellationName;
    }
}