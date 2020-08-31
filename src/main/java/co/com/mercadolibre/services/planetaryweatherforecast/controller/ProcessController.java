package co.com.mercadolibre.services.planetaryweatherforecast.controller;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusSingleResult;
import co.com.mercadolibre.services.planetaryweatherforecast.services.WeatherForecastCalculatorService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;

@Controller("/process/api/v1")
public class ProcessController {

    private WeatherForecastCalculatorService weatherForecastCalculatorService;

    public ProcessController(WeatherForecastCalculatorService weatherForecastCalculatorService){
        this.weatherForecastCalculatorService = weatherForecastCalculatorService;
    }

    @Post("/{years}")
    @Operation(tags = {"process"})
    public ProcessStatusSingleResult<String> calculateWeatherForecastByYears(int years){

        var processId = this.weatherForecastCalculatorService.initializeProcess();
        this.weatherForecastCalculatorService.generateToNextYears(years, processId.getResult());

        return processId;
    }

    @Get("/{processId}")
    @Operation(tags = {"process"})
    public ProcessStatusDto checkResult(String processId){
        return this.weatherForecastCalculatorService.checkResult(processId);
    }
}