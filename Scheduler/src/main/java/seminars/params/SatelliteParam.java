package seminars.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import seminars.enums.SatelliteType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,           // Использовать имя типа из @JsonSubTypes
        include = JsonTypeInfo.As.PROPERTY,   // Тип передаётся как поле в JSON
        property = "type"                      // Название поля-дискриминатора
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommunicationSatelliteParam.class, name = "COMMUNICATION"),
        @JsonSubTypes.Type(value = ImagingSatelliteParam.class, name = "IMAGE")
})

public class SatelliteParam {
    SatelliteType type;
    String name;
    double batteryLevel;

    public SatelliteParam(SatelliteType type,  String name, double batteryLevel) {
        this.type = type;
        this.name = name;
        this.batteryLevel = batteryLevel;
    }
    public SatelliteType getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public double getBatteryLevel() {
        return batteryLevel;
    }
}
