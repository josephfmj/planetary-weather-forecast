package co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecastDto {

    private String id;
    private String planetName;
    private String planetDay;
    private float exactDay;
    private long referenceId;
    private long year;
    private float orbitalAngle;
    private Point2D coordinates;
    private String planetAlignmentType;;
    private WeatherStatusDto weatherStatus;
}