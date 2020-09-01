package co.com.mercadolibre.services.planetaryweatherforecast.entities;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherIntensity;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherStatusInfo {

    private WeatherIntensity weatherIntensity;
    private WeatherType weatherType;

}