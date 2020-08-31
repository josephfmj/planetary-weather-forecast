package co.com.mercadolibre.services.planetaryweatherforecast.repository;

import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;
import io.reactivex.Single;

import java.util.List;

public interface WeatherForecastRepository {

    Single<WeatherForecastEntity> findById(String weatherForecastEntityId);
    Single<List<WeatherForecastEntity>> findByReferenceId(int referenceId);
    Single<WeatherForecastEntity> findByDayAndPlanetName(int day, String planetName);
    Single<String> insertWeatherForecast(WeatherForecastEntity weatherForecastEntity);
    Single<Integer> insertAll(List<WeatherForecastEntity> weatherForecastEntities);

}