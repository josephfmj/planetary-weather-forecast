package co.com.mercadolibre.services.planetaryweatherforecast.controller;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastCreatedDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastDto;
import co.com.mercadolibre.services.planetaryweatherforecast.services.WeatherForecastService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;

import javax.inject.Inject;
import java.util.List;

@Controller("/forecast/api/v1")
public class RetrieveForecastController {

    @Inject
    private WeatherForecastService weatherForecastService;

    @Get("/{id}")
    @Operation(tags = {"forecast"})
    public Single<WeatherForecastDto> findById(String id){
        return this.weatherForecastService.findById(id);
    }

    @Get("/{planetName}/{day}")
    @Operation(tags = {"forecast"})
    public Single<WeatherForecastDto> findByDayAndPlanetName(int day, String planetName){
        return this.weatherForecastService.findByDayAndPlanetName(day,planetName);
    }

    @Get("/reference/{id}")
    @Operation(tags = {"forecast"})
    public Single<List<WeatherForecastDto>> findByReferenceId(int id){
        return this.weatherForecastService.findByReferenceId(id);
    }

    @Post("/save")
    @Operation(tags = {"forecast"})
    public Single<WeatherForecastCreatedDto> save(@Body WeatherForecastDto weatherForecastDto){
        return weatherForecastService.insertWeatherForecast(weatherForecastDto);
    }
}