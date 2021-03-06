package co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetCoordinatesDto {
    private String planetName;
    private double xCoordinate;
    private double yCoordinate;
    private float currentAngle;
}