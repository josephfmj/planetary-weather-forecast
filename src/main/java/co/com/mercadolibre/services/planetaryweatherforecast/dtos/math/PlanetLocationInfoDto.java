package co.com.mercadolibre.services.planetaryweatherforecast.dtos.math;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetLocationInfoDto {
    private String planetName;
    private int planetDay;
    private long referenceId;
    private long year;
    private Point2D coordinates;
    private float angle;
    private float maxAngle;
}