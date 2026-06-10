package seminars;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString
@Getter
@Embeddable
public class SatelliteState {
    @Column(name = "state_is_active")
    private boolean isActive;
    @Column(name = "state_status_message")
    private String statusMessage;
    public SatelliteState() {
        this.isActive = false;
        this.statusMessage = "Не активирован";
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public void deactivate() {
        this.isActive = false;
        statusMessage = "Деактивирован";
    }
    public String getStatusMessage() {
        return statusMessage;
    }

    public boolean activate(boolean hasSufficientPower) {
        if (hasSufficientPower && !isActive) {
            isActive = true;
            statusMessage = "Активен";
            return true;
        }
        statusMessage = hasSufficientPower ? "Уже активен" : "Недостаточно энергии";
        return false;
    }

    @Override
    public String toString() {
        return "seminars.SatelliteState{" + "isActive=" + isActive + ", statusMessage='" + statusMessage + '\'' + '}';
    }
}
