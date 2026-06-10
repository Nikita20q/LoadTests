package seminars;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@DiscriminatorValue("IMAGING")
@NoArgsConstructor
public class ImagingSatellite extends Satellite {
    @Column(name = "resolution")
    private double resolution;
    @Column(name = "photos_taken")
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
        this.photosTaken = 0;
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
}
