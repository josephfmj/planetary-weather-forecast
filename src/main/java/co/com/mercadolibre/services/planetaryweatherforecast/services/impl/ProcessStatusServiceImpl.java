package co.com.mercadolibre.services.planetaryweatherforecast.services.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.converter.ProcessStatusConverter;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusSingleResult;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.ProcessStatusRepository;
import co.com.mercadolibre.services.planetaryweatherforecast.services.ProcessStatusService;
import io.reactivex.Single;

import javax.inject.Singleton;

@Singleton
public class ProcessStatusServiceImpl implements ProcessStatusService {

    private ProcessStatusRepository processStatusRepository;
    private ProcessStatusConverter processStatusConverter;

    public ProcessStatusServiceImpl(ProcessStatusRepository processStatusRepository, ProcessStatusConverter processStatusConverter){
        this.processStatusRepository = processStatusRepository;
        this.processStatusConverter = processStatusConverter;
    }

    @Override
    public Single<ProcessStatusDto> findById(String id) {
        return this.processStatusRepository.findById(id)
                .map(process -> this.processStatusConverter.toProcessStatusDto(process));
    }

    @Override
    public Single<ProcessStatusSingleResult<Long>> update(ProcessStatusDto processStatusDto) {
        return this.processStatusRepository.update(this.processStatusConverter.toProcessStatusEntity(processStatusDto))
                .map(response -> new ProcessStatusSingleResult<>(response));
    }

    @Override
    public Single<ProcessStatusSingleResult<String>> insert(ProcessStatusDto processStatusDto) {
        return this.processStatusRepository.insert(this.processStatusConverter.toProcessStatusEntity(processStatusDto))
                .map(response -> new ProcessStatusSingleResult<>(response));
    }
}