package co.com.mercadolibre.services.planetaryweatherforecast.services;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;

import java.util.List;

public interface RuleChainService {

    RuleResultDto applyRules(List<PlanetLocationInfoDto> data);

}