package co.com.mercadolibre.services.planetaryweatherforecast.controller;

import co.com.mercadolibre.services.planetaryweatherforecast.config.PlanetInfoConfiguration;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@Controller("/config/api/v1")
public class PlanetInfoConfigurationController {

    private List<PlanetInfoConfiguration> planetInfoConfigurationList;

    public PlanetInfoConfigurationController(List<PlanetInfoConfiguration> planetInfoConfigurationList){
        this.planetInfoConfigurationList =planetInfoConfigurationList;
    }

    @Get("/planets-info")
    @Operation(tags = {"configuration"})
    public Single<List<PlanetInfoConfiguration>> retrievePlanetInfoConfiguration(){

        return Single.just(planetInfoConfigurationList);
    }
}