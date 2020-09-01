package co.com.mercadolibre.services.planetaryweatherforecast.services;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastCreatedDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastPeriodDto;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;

public interface WeatherForecastService {

    Single<WeatherForecastDto> findById(String weatherForecastEntityId);
    Single<List<WeatherForecastDto>> findByReferenceId(int referenceId);
    Single<WeatherForecastDto> findByDayAndPlanetName(int day, String planetName);
    Single<List<WeatherForecastPeriodDto>> totalByWeatherTypeAndPlanetName(String weatherType, String planetName, int year);
    Single<List<WeatherForecastPeriodDto>> totalByTypeAndPlanetNameAndYearAndMaxCondition(String weatherType, String planetName, int year);
    Single<WeatherForecastCreatedDto> insertWeatherForecast(WeatherForecastDto weatherForecastDto);
}