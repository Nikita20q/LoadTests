package seminars;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "satellite")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "satellite_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
abstract public class Satellite {

    protected String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temperature_inside")
    private Double temperatureInside;

    @Column(name = "temperature_outside")
    private Double temperatureOutside;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constellation_id")
    protected SatelliteConstellation constellation;

    @Embedded
    protected SatelliteState state;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "energy_id", unique = true)
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.energy = EnergySystem.builder()
                .batteryLevel(batteryLevel)
                .MAX_BATTERY(EnergySystemConstants.MAX_ENERGY)
                .MIN_BATTERY(EnergySystemConstants.MIN_ENERGY)
                .LOW_BATTERY_THRESHOLD(EnergySystemConstants.LOW_BATTERY_TRESHOLD)
                .build();
        this.state = state = new SatelliteState();
        System.out.println("Создан спутник: " + this.name + " (" + energy.getBatteryLevel() + ")");
    }

    public Satellite() {

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

    public void deactivate() {
        if (state.isActive()) {
            state.deactivate();
            System.out.println("🛑 " + name + ": Деактивация успешна");
        }
    }
    abstract protected void performMission();

    public boolean isActive() {
        return state.isActive();
    }
}