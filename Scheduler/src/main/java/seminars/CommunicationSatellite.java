package seminars;

public class CommunicationSatellite extends Satellite{
    private double bandWidth;
    public double getBandWidth() {
        return bandWidth;
    }


    public CommunicationSatellite(String name, double batteryLevel, double bandWidth) {
        super(name, batteryLevel);
        this.bandWidth = bandWidth;
        System.out.println(String.format("Создан спутник: %s (заряд: %f%%)", name, energy.getBatteryLevel()));
    }

    @Override
    protected void performMission() {
        if (state.isActive()) {
            sendData(bandWidth);
            energy.consume(0.05);
        } else {
            System.out.println("🛑 " + name + ": Не может выполнить передачу данных - не активен");
        }
    }

    private void sendData(double data) {
        if (state.isActive()) {
            System.out.println(String.format("%s: Передача данных со скоростью %.2f Мбит/с", name, data));
            System.out.println(String.format("%s: Отправил %.2f Мбит данных!", name, data));
        }
    }

    @Override
    public String toString() {
        return String.format("seminars.CommunicationSatellite{bandwidth=%.2f, name='%s', isActive=%b, batteryLevel=%.2f}", bandWidth, name, state.isActive(), energy.getBatteryLevel());
    }
}
