package seminars;

abstract public class Satellite {

    protected String name;
    protected SatelliteState state;
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.energy = EnergySystem.builder()
                .batteryLevel(batteryLevel)
                .MAX_BATTERY(EnergySystemConstants.MAX_ENERGY)
                .MIN_BATTERY(EnergySystemConstants.MIN_ENERGY)
                .LOW_BATTERY_THRESHOLD(EnergySystemConstants.LOW_BATTERY_TRESHOLD)
                .build();
//        this.energy = new EnergySystem(batteryLevel);
        this.state = state = new SatelliteState();
        System.out.println("Создан спутник: " + this.name + " (" + energy.getBatteryLevel() + ")");
    }

    public boolean activate() {
        if (state.activate(energy.hasSufficientPower())) {
            System.out.println("✅ " + name + ": Активация успешна");
            state.setActive(true);
            return true;
        }
        else {
            System.out.println(String.format("\uD83D\uDED1 %s: Ошибка активации (заряд: %.2f%%)", name, energy.getBatteryLevel()));
            return false;
        }
    }

    public EnergySystem getEnergy() {
        return energy;
    }

    public SatelliteState getState() {
        return state;
    }

    public String getName() {
        return name;
    }
    void setBatteryLevel(double batteryLevel)
    {
        System.out.println("Уровень заряда спутника: " + name + " изменён с " + energy.getBatteryLevel() + "% на " + batteryLevel + "%");
        energy.setBatteryLevel(batteryLevel);
    }

    public void deactivate() {
        if (state.isActive()) {
            state.deactivate();
            System.out.println("🛑 " + name + ": Деактивация успешна");
        }
    }
    abstract protected void performMission();
}