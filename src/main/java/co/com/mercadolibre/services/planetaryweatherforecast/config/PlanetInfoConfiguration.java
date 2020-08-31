package co.com.mercadolibre.services.planetaryweatherforecast.config;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.QuadrantAngles;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.RotationType;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import lombok.Data;

import javax.annotation.PostConstruct;

@EachProperty("forecast.planets")
@Data
public class PlanetInfoConfiguration {

    private String name;
    private float angularVelocity;
    private float solarDistance;
    private RotationType rotation;
    private float maxDaysInYear;

    public PlanetInfoConfiguration(@Parameter String name){
        this.name = name;
    }

    @PostConstruct
    public void init(){
        this.maxDaysInYear = QuadrantAngles.Q4.retrieveAngle() / angularVelocity;
    }

}