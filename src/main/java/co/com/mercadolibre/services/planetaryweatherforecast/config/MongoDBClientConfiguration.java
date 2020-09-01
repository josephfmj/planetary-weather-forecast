package co.com.mercadolibre.services.planetaryweatherforecast.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
public class MongoDBClientConfiguration {

    private MongoClient mongoClient;
    private String dataBase;

    public MongoDBClientConfiguration(MongoClient mongoClient, @Value("${mongo-client.database}") String dataBase){
        this.mongoClient = mongoClient;
        this.dataBase = dataBase;
    }

    public <T> MongoCollection<T> retrieveCollection(final String collection, Class<T> tClass){
        return mongoClient
                .getDatabase(this.dataBase)
                .getCollection(collection,tClass);
    }
}