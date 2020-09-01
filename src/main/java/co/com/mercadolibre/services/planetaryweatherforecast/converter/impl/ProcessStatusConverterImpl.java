package co.com.mercadolibre.services.planetaryweatherforecast.converter.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.converter.ProcessStatusConverter;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.ProcessStatusEntity;
import org.bson.types.ObjectId;

import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class ProcessStatusConverterImpl implements ProcessStatusConverter {

    @Override
    public ProcessStatusEntity toProcessStatusEntity(ProcessStatusDto processStatusDto) {

        final var processStatusEntity = new ProcessStatusEntity();

        processStatusEntity.setProcessStatus(processStatusDto.getProcessStatus());
        processStatusEntity.setDescription(processStatusDto.getDescription());
        if(Objects.nonNull(processStatusDto.getId()))
            processStatusEntity.setId(new ObjectId(processStatusDto.getId()));

        return processStatusEntity;
    }

    @Override
    public ProcessStatusDto toProcessStatusDto(ProcessStatusEntity processStatusEntity) {

        final var processStatusDto = new ProcessStatusDto();

        processStatusDto.setProcessStatus(processStatusEntity.getProcessStatus());
        processStatusDto.setDescription(processStatusEntity.getDescription());
        processStatusDto.setId(processStatusEntity.getId().toString());

        return processStatusDto;
    }
}