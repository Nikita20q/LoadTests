package seminars;

public class ImagingSatellite extends Satellite {
    private double resolution;
    private int photosTaken;

    public double getResolution() {
        return resolution;
    }

    public int getPhotosTaken() {
        return photosTaken;
    }

    public ImagingSatellite(String name, double batteryLevel, double resolution) {
        super(name, batteryLevel);
        this.resolution = resolution;
        System.out.println(String.format("Создан спутник: %s (заряд: %f%%)", name, energy.getBatteryLevel()));
    }

    @Override
    public void performMission() {
        if (state.isActive()) {
            System.out.println(name + ": Съемка территории с разрешением " + resolution + "м/пиксель");
            takePhoto();
            energy.setBatteryLevel(0.08);
        }
        else {
            System.out.println("🛑 " + name + ": Не может выполнить съемку - не активен");
        }
    }

    private void takePhoto() {
        photosTaken ++;
    }

    @Override
    public String toString() {
        return String.format("seminars.ImagingSatellite{resolution=%.2f, photosTaken=%d, name='%s', isActive=%b, batteryLevel=%.2f}", resolution, photosTaken, name, state.isActive(), energy.getBatteryLevel());
    }
}
