package co.com.mercadolibre.services.planetaryweatherforecast.converter;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;

import java.util.List;

public interface RuleResultDtoConverter {

    List<WeatherForecastEntity> unWrapRuleResultDto(List<RuleResultDto> ruleResultDto);
}