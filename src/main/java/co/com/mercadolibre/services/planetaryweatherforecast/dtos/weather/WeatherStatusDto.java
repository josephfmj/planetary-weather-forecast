package co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherStatusDto {

    private String weatherIntensity;
    private String weatherType;
}