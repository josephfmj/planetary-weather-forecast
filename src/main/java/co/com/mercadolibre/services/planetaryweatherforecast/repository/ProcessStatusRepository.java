package co.com.mercadolibre.services.planetaryweatherforecast.repository;

import co.com.mercadolibre.services.planetaryweatherforecast.entities.ProcessStatusEntity;
import io.reactivex.Single;

public interface ProcessStatusRepository {

    Single<ProcessStatusEntity> findById(String id);
    Single<Long> update(ProcessStatusEntity processStatusEntity);
    Single<String> insert(ProcessStatusEntity processStatusEntity);
}