package seminars;

import java.util.ArrayList;

public class SatelliteConstellation {
    private String constellationName;
    private ArrayList<Satellite> satellites = new ArrayList<>();

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
    public ArrayList<Satellite> getSatellites() {
        return satellites;
    }
    public String getConstellationName() {
        return constellationName;
    }
}