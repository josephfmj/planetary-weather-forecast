package co.com.mercadolibre.services.planetaryweatherforecast.repository;

import io.reactivex.Single;
import org.bson.Document;

import java.util.List;

public interface WeatherForecastPeriodsRepository {

    Single<List<Document>> totalByTypeAndPlanetNameAndYear(String weatherType, String planetName, int years);
    Single<List<Document>> totalByTypeAndPlanetNameAndYearAndMaxCondition(String weatherType, String planetName,int years);
}