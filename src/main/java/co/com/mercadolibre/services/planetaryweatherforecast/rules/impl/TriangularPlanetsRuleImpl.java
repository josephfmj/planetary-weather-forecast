package co.com.mercadolibre.services.planetaryweatherforecast.rules.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.*;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.rules.IRule;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.InCenterCoordinatesUtil;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.PointLocationInTriangleUtil;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

import static co.com.mercadolibre.services.planetaryweatherforecast.constants.PlanetAlignmentType.*;
import static co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherIntensity.MAX;
import static co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherIntensity.NORMAL;

@Singleton
@Named("triangularPlanetsRuleImpl")
public class TriangularPlanetsRuleImpl implements IRule<PlanetLocationInfoDto, RuleResultDto> {

    private IRule<PlanetLocationInfoDto,RuleResultDto> nextRule;
    private PointLocationInTriangleUtil pointLocationInTriangleUtil;
    private InCenterCoordinatesUtil inCenterCoordinatesUtil;

    public TriangularPlanetsRuleImpl(PointLocationInTriangleUtil pointLocationInTriangleUtil,
                                     InCenterCoordinatesUtil inCenterCoordinatesUtil){
        this.pointLocationInTriangleUtil = pointLocationInTriangleUtil;
        this.inCenterCoordinatesUtil = inCenterCoordinatesUtil;
    }

    @Override
    public RuleResultDto evaluate(List<PlanetLocationInfoDto> data) {

        var resultDto = new RuleResultDto();
        resultDto.setPlanetsLocation(data);
        resultDto.setMessage(ProcessMessages.SUCCESS);
        resultDto.setPlanetAlignmentType(TRIANGULAR);
        resultDto.setWeatherIntensity(NORMAL);
        resultDto.setWeatherType(WeatherType.NORMAL);

        //extract planets coordinates in Triangle object
        var planetsTriangularFormation = new Triangle(data.get(0).getCoordinates(),data.get(1).getCoordinates(),data.get(2).getCoordinates());
        var locationInTriangle = this.pointLocationInTriangleUtil.retrieveLocationOriginInside(planetsTriangularFormation);

        if(PointLocationInTriangle.INSIDE.equals(locationInTriangle)){

            var sunIsInCenter = this.inCenterCoordinatesUtil.isPointInCenter(planetsTriangularFormation);

            resultDto.setInCenter(this.inCenterCoordinatesUtil.retrieveInCenter(planetsTriangularFormation));
            resultDto.setWeatherType(WeatherType.RAIN);
            resultDto.setPlanetAlignmentType(sunIsInCenter ? TRIANGULAR_WITH_THE_SUN_IN_THE_CENTER : TRIANGULAR_WITH_THE_SUN_INSIDE);
            resultDto.setWeatherIntensity(sunIsInCenter ? MAX : NORMAL);
        }

        return resultDto;
    }

    @Override
    public void addNextRule(IRule nextRule) {
        this.nextRule = nextRule;
    }

}