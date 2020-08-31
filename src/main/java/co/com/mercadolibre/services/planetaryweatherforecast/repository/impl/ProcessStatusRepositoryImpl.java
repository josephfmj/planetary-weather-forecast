package co.com.mercadolibre.services.planetaryweatherforecast.repository.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.config.MongoDBClientConfiguration;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastCreatedDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.ProcessStatusEntity;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.ProcessStatusRepository;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.context.annotation.Value;
import io.reactivex.Single;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Singleton;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class ProcessStatusRepositoryImpl implements ProcessStatusRepository {

    private final MongoDBClientConfiguration mongoDBClientConfiguration;
    private String processDataBase;

    public ProcessStatusRepositoryImpl(MongoDBClientConfiguration mongoDBClientConfiguration,
                                         @Value("${mongo-client.collections.process}") String processDataBase){
        this.mongoDBClientConfiguration = mongoDBClientConfiguration;
        this.processDataBase = processDataBase;
    }

    @Override
    public Single<ProcessStatusEntity> findById(String id) {
        var filter = eq("_id",new ObjectId(id));
        return Single.fromPublisher(this.retrieveCollection().find(filter).first());
    }

    @Override
    public Single<Long> update(ProcessStatusEntity processStatusEntity) {
        var filter = eq("_id",processStatusEntity.getId());
        return Single.fromPublisher(this.retrieveCollection().replaceOne(filter,processStatusEntity))
                .map(result -> result.getModifiedCount());
    }

    @Override
    public Single<String> insert(ProcessStatusEntity processStatusEntity) {
        return Single.fromPublisher(this.retrieveCollection().insertOne(processStatusEntity))
                .map(result -> result.getInsertedId().asObjectId().getValue().toString());
    }

    private MongoCollection<ProcessStatusEntity> retrieveCollection(){
        return this.mongoDBClientConfiguration
                .retrieveCollection(this.processDataBase,ProcessStatusEntity.class);
    }
}