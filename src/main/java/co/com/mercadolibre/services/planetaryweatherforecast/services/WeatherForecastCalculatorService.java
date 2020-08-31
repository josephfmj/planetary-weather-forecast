package co.com.mercadolibre.services.planetaryweatherforecast.services;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusSingleResult;

public interface WeatherForecastCalculatorService {

    void generateToNextYears(int years, String processId);
    ProcessStatusSingleResult<String> initializeProcess();
    ProcessStatusDto checkResult(String id);
}