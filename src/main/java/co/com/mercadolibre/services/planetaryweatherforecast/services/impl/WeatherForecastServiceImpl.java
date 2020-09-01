package co.com.mercadolibre.services.planetaryweatherforecast.services.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.converter.WeatherForecastDtoConverter;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastCreatedDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather.WeatherForecastPeriodDto;
import co.com.mercadolibre.services.planetaryweatherforecast.exceptions.GenericWeatherForecastException;
import co.com.mercadolibre.services.planetaryweatherforecast.exceptions.NotFoundException;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.WeatherForecastPeriodsRepository;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.WeatherForecastRepository;
import co.com.mercadolibre.services.planetaryweatherforecast.services.WeatherForecastService;
import io.reactivex.Single;
import org.bson.Document;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class WeatherForecastServiceImpl implements WeatherForecastService {

    @Inject
    private WeatherForecastRepository weatherForecastRepository;
    @Inject
    private WeatherForecastDtoConverter weatherForecastDtoConverter;
    @Inject
    private WeatherForecastPeriodsRepository weatherForecastPeriodsRepository;

    @Override
    public Single<WeatherForecastDto> findById(String weatherForecastEntityId) {
        return weatherForecastRepository.findById(weatherForecastEntityId)
                .map(data -> this.weatherForecastDtoConverter.toWeatherStatusDto(data))
                .onErrorReturn(error -> {throw new NotFoundException("not found result with Id: "+weatherForecastEntityId);});
    }

    @Override
    public Single<List<WeatherForecastDto>> findByReferenceId(int referenceId){

        return weatherForecastRepository.findByReferenceId(referenceId)
                .map(data -> data.stream().map(item -> this.weatherForecastDtoConverter.toWeatherStatusDto(item)).collect(Collectors.toList()))
                .onErrorReturn(error -> {throw new NotFoundException("not found result for referenceId: "+referenceId);});
    }

    @Override
    public Single<WeatherForecastDto> findByDayAndPlanetName(int day, String planetName) {
        return weatherForecastRepository.findByDayAndPlanetName(day,planetName)
                .map(data -> this.weatherForecastDtoConverter.toWeatherStatusDto(data))
                .onErrorReturn(error -> {throw new NotFoundException("not found result for day: " +day+", and planet name: "+ planetName);});
    }

    @Override
    public Single<List<WeatherForecastPeriodDto>> totalByWeatherTypeAndPlanetName(String weatherType, String planetName, int year) {
        return weatherForecastPeriodsRepository.totalByTypeAndPlanetNameAndYear(weatherType,planetName,year)
                .map(data -> this.processForecastPeriods(data))
                .onErrorReturn(error -> {throw new GenericWeatherForecastException(error);});
    }

    @Override
    public Single<List<WeatherForecastPeriodDto>> totalByTypeAndPlanetNameAndYearAndMaxCondition(String weatherType, String planetName, int year) {
        return weatherForecastPeriodsRepository.totalByTypeAndPlanetNameAndYearAndMaxCondition(weatherType,planetName,year)
                .map(data -> this.processForecastPeriods(data))
                .onErrorReturn(error -> {throw new GenericWeatherForecastException(error);});
    }

    @Override
    public Single<WeatherForecastCreatedDto> insertWeatherForecast(WeatherForecastDto weatherForecastDto) {
        return weatherForecastRepository
                .insertWeatherForecast(this.weatherForecastDtoConverter.toWeatherForecastEntity(weatherForecastDto))
                .map( resultId -> new WeatherForecastCreatedDto(resultId))
                .onErrorReturn(error -> {throw new GenericWeatherForecastException(error);});
    }


    private List<WeatherForecastPeriodDto> processForecastPeriods(List<Document> documents){
        return documents.stream()
                .map(document -> new WeatherForecastPeriodDto(document))
                .collect(Collectors.toList());
    }
}