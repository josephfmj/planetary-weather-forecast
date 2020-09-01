package co.com.mercadolibre.services.planetaryweatherforecast.services;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;

import java.util.List;

public interface PlanetLocationService {

    List<PlanetLocationInfoDto> retrieveLocation(final int day);
    float retrieveMaxDaysByYearInPlanetList();
}