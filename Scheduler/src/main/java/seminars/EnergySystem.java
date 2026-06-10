package seminars;

import lombok.Builder;

@Builder
public class EnergySystem {
    private double batteryLevel;

    private double LOW_BATTERY_THRESHOLD;
    private double MAX_BATTERY;
    private double MIN_BATTERY;
    public double getBatteryLevel() {
        return batteryLevel;
    }

//    public EnergySystem(double batteryLevel) {
//        this.batteryLevel = Math.max(MIN_BATTERY ,batteryLevel);
//    }

    public boolean consume(double amount) {
        if (amount <= 0 || batteryLevel <= MIN_BATTERY) {
            return false;
        }
        batteryLevel = Math.max(MIN_BATTERY, batteryLevel - amount);
        return true;
    }

    public boolean hasSufficientPower() {
        return batteryLevel > LOW_BATTERY_THRESHOLD;
    }
    public void setBatteryLevel(double batteryLevel) {
        if (batteryLevel > MAX_BATTERY) {
            this.batteryLevel = MAX_BATTERY;
        } else if  (batteryLevel < MIN_BATTERY) {
            this.batteryLevel = MIN_BATTERY;
        } else {
            this.batteryLevel = batteryLevel;
        }
    }
    @Override
    public String toString() {
        return "seminars.EnergySystem{" + "batteryLevel=" + batteryLevel + '}';
    }


}
