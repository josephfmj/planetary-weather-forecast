package co.com.mercadolibre.services.planetaryweatherforecast.converter.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PlanetAlignmentType;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherIntensity;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherType;
import co.com.mercadolibre.services.planetaryweatherforecast.converter.WeatherForecastDtoConverter;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherStatusInfo;
import org.bson.types.ObjectId;

import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public class WeatherForecastDtoConverterImpl implements WeatherForecastDtoConverter {

    @Override
    public WeatherForecastDto toWeatherStatusDto(WeatherForecastEntity weatherForecastEntity) {

        final WeatherForecastDto responseWeatherForecastDto = new WeatherForecastDto();

        responseWeatherForecastDto.setPlanetAlignmentType(weatherForecastEntity.getPlanetAlignmentType().name());
        responseWeatherForecastDto.setCoordinates(weatherForecastEntity.getCoordinates());
        responseWeatherForecastDto.setId(weatherForecastEntity.getId().toString());
        responseWeatherForecastDto.setOrbitalAngle(weatherForecastEntity.getOrbitalAngle());
        responseWeatherForecastDto.setYear(weatherForecastEntity.getYear());
        responseWeatherForecastDto.setPlanetName(weatherForecastEntity.getPlanetName());
        responseWeatherForecastDto.setPlanetDay(weatherForecastEntity.getPlanetDay());
        responseWeatherForecastDto.setReferenceId(weatherForecastEntity.getReferenceId());
        responseWeatherForecastDto.setWeatherStatus(this.toWeatherStatusDto(weatherForecastEntity.getWeatherStatusInfo()));

        return responseWeatherForecastDto;
    }

    @Override
    public WeatherForecastEntity toWeatherForecastEntity(WeatherForecastDto responseWeatherForecastDto) {

        final WeatherForecastEntity weatherForecastEntity = new WeatherForecastEntity();

        weatherForecastEntity.setPlanetAlignmentType(PlanetAlignmentType.valueOf(responseWeatherForecastDto.getPlanetAlignmentType()));
        weatherForecastEntity.setCoordinates(responseWeatherForecastDto.getCoordinates());
        weatherForecastEntity.setOrbitalAngle(responseWeatherForecastDto.getOrbitalAngle());
        weatherForecastEntity.setYear(responseWeatherForecastDto.getYear());
        weatherForecastEntity.setPlanetName(responseWeatherForecastDto.getPlanetName());
        weatherForecastEntity.setPlanetDay(responseWeatherForecastDto.getPlanetDay());
        weatherForecastEntity.setReferenceId(responseWeatherForecastDto.getReferenceId());
        weatherForecastEntity.setWeatherStatusInfo(this.toWeatherStatusInfo(responseWeatherForecastDto.getWeatherStatus()));
        if(Objects.nonNull(responseWeatherForecastDto.getId()))
            weatherForecastEntity.setId(new ObjectId(responseWeatherForecastDto.getId()));

        return weatherForecastEntity;
    }

    private WeatherStatusDto toWeatherStatusDto(WeatherStatusInfo weatherStatusInfo){

        final WeatherStatusDto weatherStatusDto = new WeatherStatusDto();
        weatherStatusDto.setWeatherIntensity(weatherStatusInfo.getWeatherIntensity().name());
        weatherStatusDto.setWeatherType(weatherStatusInfo.getWeatherType().name());

        return weatherStatusDto;
    }

    private WeatherStatusInfo toWeatherStatusInfo(WeatherStatusDto weatherStatusDto){

        final WeatherStatusInfo weatherStatusInfo = new WeatherStatusInfo();
        weatherStatusInfo.setWeatherIntensity(WeatherIntensity.valueOf(weatherStatusDto.getWeatherIntensity()));
        weatherStatusInfo.setWeatherType(WeatherType.valueOf(weatherStatusDto.getWeatherType()));
        return weatherStatusInfo;
    }

}