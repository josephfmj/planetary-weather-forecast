package co.com.mercadolibre.services.planetaryweatherforecast.converter;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;

public interface WeatherForecastDtoConverter {

    WeatherForecastDto toWeatherStatusDto(WeatherForecastEntity weatherForecastEntity);
    WeatherForecastEntity toWeatherForecastEntity(WeatherForecastDto responseWeatherForecastDto);
}