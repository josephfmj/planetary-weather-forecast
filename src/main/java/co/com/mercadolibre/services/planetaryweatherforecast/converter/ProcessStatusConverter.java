package co.com.mercadolibre.services.planetaryweatherforecast.converter;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.ProcessStatusEntity;

public interface ProcessStatusConverter {

    ProcessStatusEntity toProcessStatusEntity(ProcessStatusDto processStatusDto);
    ProcessStatusDto toProcessStatusDto(ProcessStatusEntity processStatusEntity);

}