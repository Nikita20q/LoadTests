package seminars;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "energy_system")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnergySystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "battery_level", nullable = false)
    private double batteryLevel;

    @Column(name = "low_battery_threshold", nullable = false)
    private double LOW_BATTERY_THRESHOLD;

    @Column(name = "max_battery", nullable = false)
    private double MAX_BATTERY;

    @Column(name = "min_battery", nullable = false)
    private double MIN_BATTERY;

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

}
