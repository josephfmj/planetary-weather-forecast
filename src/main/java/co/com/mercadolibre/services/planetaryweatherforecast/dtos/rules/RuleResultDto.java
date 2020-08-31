package co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PlanetAlignmentType;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.ProcessMessages;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherIntensity;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherType;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleResultDto {

    private List<PlanetLocationInfoDto> planetsLocation;
    private Point2D inCenter;
    private PlanetAlignmentType planetAlignmentType;
    private WeatherIntensity weatherIntensity;
    private WeatherType weatherType;
    private ProcessMessages message;
}