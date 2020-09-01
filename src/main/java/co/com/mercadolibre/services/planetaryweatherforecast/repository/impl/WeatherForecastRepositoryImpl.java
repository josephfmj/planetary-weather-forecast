package co.com.mercadolibre.services.planetaryweatherforecast.repository.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.config.MongoDBClientConfiguration;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.WeatherForecastRepository;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.context.annotation.Value;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.bson.types.ObjectId;

import javax.inject.Singleton;

import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Singleton
public class WeatherForecastRepositoryImpl implements WeatherForecastRepository {

    private final MongoDBClientConfiguration mongoDBClientConfiguration;
    private String forecastDataBase;

    public WeatherForecastRepositoryImpl(MongoDBClientConfiguration mongoDBClientConfiguration,
                                         @Value("${mongo-client.collections.forecast}") String forecastDataBase){
        this.mongoDBClientConfiguration = mongoDBClientConfiguration;
        this.forecastDataBase = forecastDataBase;
    }

    @Override
    public Single<WeatherForecastEntity> findById(String weatherForecastEntityId) {
        var filter = eq("_id",new ObjectId(weatherForecastEntityId));
        return Single.fromPublisher(this.retrieveCollection().find(filter).first());
    }

    @Override
    public Single<List<WeatherForecastEntity>> findByReferenceId(int referenceId) {
        var filter = eq("referenceId",referenceId);
        return Flowable.fromPublisher(this.retrieveCollection().find(filter)).toList();
    }

    @Override
    public Single<WeatherForecastEntity> findByDayAndPlanetName(int day, String planetName) {
        var filter = and(eq("planetDay",day),eq("planetName",planetName));
        return Single.fromPublisher(this.retrieveCollection().find(filter).first());
    }

    @Override
    public Single<String> insertWeatherForecast(WeatherForecastEntity weatherForecastEntity) {
        return Single
                .fromPublisher(this.retrieveCollection().insertOne(weatherForecastEntity))
                .map(result -> result.getInsertedId().asObjectId().getValue().toString());
    }

    @Override
    public Single<Integer>  insertAll(List<WeatherForecastEntity> weatherForecastEntities) {
        return Single.fromPublisher(this.retrieveCollection().insertMany(weatherForecastEntities))
                .map(data -> data.getInsertedIds().size());
    }

    private MongoCollection<WeatherForecastEntity> retrieveCollection(){
        return this.mongoDBClientConfiguration
                .retrieveCollection(this.forecastDataBase,WeatherForecastEntity.class);
    }
}