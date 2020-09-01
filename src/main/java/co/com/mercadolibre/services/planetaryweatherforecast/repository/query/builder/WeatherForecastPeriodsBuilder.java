package co.com.mercadolibre.services.planetaryweatherforecast.repository.query.builder;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Projections;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.and;

public class WeatherForecastPeriodsBuilder {

    private List<Bson> aggregations;
    private List<Bson> andFilters;
    private List<String> projectionFieldNames;
    private List<BsonField> groupFiled;
    private Bson groupQuery;
    private Bson projection;
    private Bson matchFilter;
    private Bson sort;

    private WeatherForecastPeriodsBuilder(){
        this.aggregations = new ArrayList<>();
        this.andFilters = new ArrayList<>();
        this.projectionFieldNames = new ArrayList<>();
        this.groupFiled = new ArrayList<>();
    }

    public static WeatherForecastPeriodsBuilder newInstance(){
        return new WeatherForecastPeriodsBuilder();
    }

    public WeatherForecastPeriodsBuilder appendFilter(Bson filter){

        if(Objects.nonNull(filter)){
            this.andFilters.add(filter);
        }

        return this;
    }

    public WeatherForecastPeriodsBuilder appendProjectionFieldList(List<String> fields){
        this.projectionFieldNames=fields;
        return this;
    }

    public WeatherForecastPeriodsBuilder buildSimpleProjection(){

        if(!this.projectionFieldNames.isEmpty()) {
            this.projection = Aggregates.project(
                    Projections.fields(
                            Projections.include(this.projectionFieldNames)
                    )
            );

            this.aggregations.add(this.projection);
        }

        return this;
    }

    public WeatherForecastPeriodsBuilder appendGroup(BsonField group){
        this.groupFiled.add(group);
        return this;
    }

    public WeatherForecastPeriodsBuilder buildGroup(String id){

        if(!this.groupFiled.isEmpty()){
            this.groupQuery = Aggregates.group(id, this.groupFiled);
            this.aggregations.add(this.groupQuery);
        }

        return this;
    }

    public WeatherForecastPeriodsBuilder withSort(Bson sort){
        this.sort= sort;
        this.aggregations.add(this.sort);
        return this;
    }

    public WeatherForecastPeriodsBuilder buildMatchFilter(){

        if(!andFilters.isEmpty()) {
            this.matchFilter = match(and(this.andFilters));
            this.aggregations.add(this.matchFilter);
        }

        return this;
    }

    public List<Bson> retrieveAggregations(){

        return this.aggregations;
    }
}