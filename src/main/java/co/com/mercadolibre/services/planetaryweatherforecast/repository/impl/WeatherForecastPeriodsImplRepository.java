package co.com.mercadolibre.services.planetaryweatherforecast.repository.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.config.MongoDBClientConfiguration;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.WeatherForecastPeriodsRepository;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.query.builder.WeatherForecastPeriodsBuilder;

import com.mongodb.client.model.*;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.context.annotation.Value;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

@Singleton
public class WeatherForecastPeriodsImplRepository implements WeatherForecastPeriodsRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherForecastPeriodsImplRepository.class);

    private final MongoDBClientConfiguration mongoDBClientConfiguration;
    private String forecastDataBase;

    public WeatherForecastPeriodsImplRepository(MongoDBClientConfiguration mongoDBClientConfiguration,
                                         @Value("${mongo-client.collections.forecast}") String forecastDataBase){
        this.mongoDBClientConfiguration = mongoDBClientConfiguration;
        this.forecastDataBase = forecastDataBase;
    }

    @Override
    public Single<List<Document>> totalByTypeAndPlanetNameAndYear(String weatherType, String planetName, int years) {

        var aggregations = createAggregationBuilder(false,weatherType,planetName, years);
        AggregatePublisher<Document> publisher = this.retrieveCollection()
                .aggregate(aggregations).allowDiskUse(true);

        return Flowable.fromPublisher(publisher).toList();

    }

    @Override
    public Single<List<Document>> totalByTypeAndPlanetNameAndYearAndMaxCondition(String weatherType, String planetName, int years) {

        var aggregations = createAggregationBuilder(true,weatherType,planetName, years);

        AggregatePublisher<Document> publisher = this.retrieveCollection()
                .aggregate(aggregations).allowDiskUse(true);

        return Flowable.fromPublisher(publisher).toList();
    }


    private MongoCollection<Document> retrieveCollection(){
        return this.mongoDBClientConfiguration
                .retrieveCollection(this.forecastDataBase,Document.class);
    }

    private List<Bson> createAggregationBuilder(boolean isMax, String weatherType, String planetName, int year){

        var id = new LinkedHashMap<String, Object>();
        id.putIfAbsent("weatherStatusInfo","$weatherStatusInfo");
        id.putIfAbsent("planetAlignmentType","$planetAlignmentType");
        id.putIfAbsent("year","$year");
        var maxFilter = isMax?eq("weatherStatusInfo.weatherIntensity","MAX"):null;

        return WeatherForecastPeriodsBuilder
                .newInstance()
                .appendFilter(eq("planetName",planetName))
                .appendFilter(eq("weatherStatusInfo.weatherType",weatherType))
                .appendFilter(lte("year",year))
                .appendFilter(maxFilter)
                .buildMatchFilter()
                .appendGroup(new BsonField("_id",new Document(id)))
                .appendGroup(Accumulators.sum("totalDays", 1))
                .buildGroup(null)
                .appendProjectionFieldList(Arrays.asList("planetAlignmentType","weatherStatusInfo","year","totalDays"))
                .buildSimpleProjection()
                .withSort(sort(Sorts.descending("totalDays")))
                .retrieveAggregations();
    }

}