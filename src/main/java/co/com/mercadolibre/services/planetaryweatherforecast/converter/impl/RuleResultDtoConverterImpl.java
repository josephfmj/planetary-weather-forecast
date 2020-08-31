package co.com.mercadolibre.services.planetaryweatherforecast.converter.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.converter.RuleResultDtoConverter;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherStatusInfo;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RuleResultDtoConverterImpl implements RuleResultDtoConverter {

    @Override
    public List<WeatherForecastEntity> unWrapRuleResultDto(List<RuleResultDto> ruleResultDto) {

        return ruleResultDto
                .stream()
                .map(data -> this.buildWeatherForecastEntity(data))
                .reduce(new ArrayList<>(), (partial, current) -> {
                    partial.addAll(current);
                    return partial;
                });
    }

    private List<WeatherForecastEntity> buildWeatherForecastEntity(RuleResultDto ruleResultDto){

        var weatherForecastEntityList = new ArrayList<WeatherForecastEntity>();
        ruleResultDto.getPlanetsLocation().forEach( data -> {

            final var weatherForecastEntity = new WeatherForecastEntity();
            weatherForecastEntity.setPlanetAlignmentType(ruleResultDto.getPlanetAlignmentType());
            weatherForecastEntity.setWeatherStatusInfo(this.buildWeatherStatusInfo(ruleResultDto));
            this.extractPlanetLocationInfo(weatherForecastEntity,data);
            weatherForecastEntityList.add(weatherForecastEntity);

        });

        return weatherForecastEntityList;
    }

    private void extractPlanetLocationInfo(final WeatherForecastEntity weatherForecastEntity, final PlanetLocationInfoDto planetLocationInfoDto){

        weatherForecastEntity.setCoordinates(planetLocationInfoDto.getCoordinates());
        weatherForecastEntity.setExactDay(planetLocationInfoDto.getExactDay());
        weatherForecastEntity.setOrbitalAngle(planetLocationInfoDto.getAngle());
        weatherForecastEntity.setPlanetName(planetLocationInfoDto.getPlanetName());
        weatherForecastEntity.setPlanetDay(planetLocationInfoDto.getPlanetDay());
        weatherForecastEntity.setYear(planetLocationInfoDto.getYear());
        weatherForecastEntity.setReferenceId(planetLocationInfoDto.getReferenceId());

    }

    private WeatherStatusInfo buildWeatherStatusInfo(RuleResultDto ruleResultDto) {

        final var weatherStatusInfo = new WeatherStatusInfo();
        weatherStatusInfo.setWeatherIntensity(ruleResultDto.getWeatherIntensity());
        weatherStatusInfo.setWeatherType(ruleResultDto.getWeatherType());

        return weatherStatusInfo;
    }
}