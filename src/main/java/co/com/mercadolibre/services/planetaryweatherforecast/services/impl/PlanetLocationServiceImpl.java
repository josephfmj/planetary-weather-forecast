package co.com.mercadolibre.services.planetaryweatherforecast.services.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.config.PlanetInfoConfiguration;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.ProcessMessages;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.exceptions.UnSupportedPlanetNumberException;
import co.com.mercadolibre.services.planetaryweatherforecast.services.PlanetLocationService;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.AngleCalculatorUtil;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.CoordinatesCalculatorUtil;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class PlanetLocationServiceImpl implements PlanetLocationService {

    private final List<PlanetInfoConfiguration> planetInfoConfigurationList;
    private CoordinatesCalculatorUtil coordinatesCalculatorUtil;
    private AngleCalculatorUtil angleCalculatorUtil;

    @Inject
    public PlanetLocationServiceImpl(List<PlanetInfoConfiguration> planetInfoConfigurationList,
                                     CoordinatesCalculatorUtil coordinatesCalculatorUtil,
                                     AngleCalculatorUtil angleCalculatorUtil){
        this.planetInfoConfigurationList =planetInfoConfigurationList;
        this.coordinatesCalculatorUtil = coordinatesCalculatorUtil;
        this.angleCalculatorUtil = angleCalculatorUtil;
    }

    @PostConstruct
    public void validateSupportPlanetNumber(){
        if(this.planetInfoConfigurationList.size()>3 || planetInfoConfigurationList.size() < 3 || planetInfoConfigurationList.isEmpty())
            throw new UnSupportedPlanetNumberException(ProcessMessages.UNSUPPORTED_PLANET_NUMBER.getMessage());
    }
    @Override
    public List<PlanetLocationInfoDto> retrieveLocation(final int day) {
        final var planetLocationInfoDtoList = new ArrayList<PlanetLocationInfoDto>();
        planetInfoConfigurationList.forEach(planetInfo ->{
            planetLocationInfoDtoList.add(this.retrieveLocationInfo(planetInfo, day));
        });

        return planetLocationInfoDtoList;
    }

    @Override
    public float retrieveMaxDaysByYearInPlanetList() {
        return planetInfoConfigurationList.stream()
                .max(Comparator.comparing(PlanetInfoConfiguration::getMaxDaysInYear))
                .map( data -> data.getMaxDaysInYear())
                .get();
    }

    private PlanetLocationInfoDto retrieveLocationInfo(PlanetInfoConfiguration planetInfo, int day){

        final var currentAngle = this.angleCalculatorUtil.retrieveAngleByDayAndRotationType(day,planetInfo.getAngularVelocity());
        final var coordinates = new Point2D();
        coordinates.setXComponent(this.coordinatesCalculatorUtil.retrieveXCoordinate(currentAngle,planetInfo.getSolarDistance()));
        coordinates.setYComponent(this.coordinatesCalculatorUtil.retrieveYCoordinate(currentAngle,planetInfo.getSolarDistance(),planetInfo.getRotation()));

        final var planetLocationInfoDto = new PlanetLocationInfoDto();
        planetLocationInfoDto.setPlanetName(planetInfo.getName());
        planetLocationInfoDto.setAngle(currentAngle);
        planetLocationInfoDto.setCoordinates(coordinates);
        planetLocationInfoDto.setPlanetDay(day);
        // this is de day of slowest planet, to reference in database
        planetLocationInfoDto.setReferenceId(day);
        planetLocationInfoDto.setYear(this.retrieveYear(day, planetInfo.getMaxDaysInYear()));
        planetLocationInfoDto.setMaxAngle(planetInfo.getMaxDaysInYear());

        return planetLocationInfoDto;
    }

    private long retrieveYear(int day, float maxDaysInYear){
        return  ((long)(day/maxDaysInYear)) + 1;
    }
}