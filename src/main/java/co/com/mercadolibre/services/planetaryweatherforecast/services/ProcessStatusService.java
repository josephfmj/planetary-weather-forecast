package co.com.mercadolibre.services.planetaryweatherforecast.services;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusSingleResult;
import io.reactivex.Single;

public interface ProcessStatusService {

    Single<ProcessStatusDto> findById(String id);
    Single<ProcessStatusSingleResult<Long>> update(ProcessStatusDto processStatusDto);
    Single<ProcessStatusSingleResult<String>> insert(ProcessStatusDto processStatusDto);
}