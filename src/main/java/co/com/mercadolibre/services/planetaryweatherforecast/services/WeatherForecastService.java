package co.com.mercadolibre.services.planetaryweatherforecast.services;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastCreatedDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastDto;
import io.reactivex.Single;

import java.util.List;

public interface WeatherForecastService {

    Single<WeatherForecastDto> findById(String weatherForecastEntityId);
    Single<List<WeatherForecastDto>> findByReferenceId(int referenceId);
    Single<WeatherForecastDto> findByDayAndPlanetName(int day, String planetName);
    Single<WeatherForecastCreatedDto> insertWeatherForecast(WeatherForecastDto weatherForecastDto);
}