package co.com.mercadolibre.services.planetaryweatherforecast.rules.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PlanetAlignmentType;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.ProcessMessages;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherIntensity;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherType;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.rules.IRule;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.VectorUtil;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;

@Singleton
@Named("inlinePlanetRuleImpl")
public class InlinePlanetsRuleImpl implements IRule<PlanetLocationInfoDto, RuleResultDto> {

    private IRule<PlanetLocationInfoDto,RuleResultDto> nextRule;

    @Override
    public RuleResultDto evaluate(List<PlanetLocationInfoDto> data) {

        //extract planet coordinates into Point2D object
        var planetA = data.get(0).getCoordinates();
        var planetB = data.get(1).getCoordinates();
        var planetC = data.get(2).getCoordinates();

        var resultDto = this.checkPlanetsAlignment(planetA,planetB,planetC);
        resultDto.setPlanetsLocation(data);

        if(Objects.isNull(resultDto.getPlanetAlignmentType()) && Objects.nonNull(this.nextRule)){
            resultDto = this.nextRule.evaluate(data);
        }else {
            this.checkSunAlignment(resultDto, planetB,planetC);
        }

        return resultDto;
    }

    @Override
    public void addNextRule(IRule nextRule) {
        this.nextRule = nextRule;
    }

    private RuleResultDto checkPlanetsAlignment(Point2D planetA, Point2D planetB, Point2D planetC){

        var resultDto = new RuleResultDto();

        var result = VectorUtil.checkIfPointsAreAligned(planetA,planetB,planetC);

        if(result){
            resultDto.setPlanetAlignmentType(PlanetAlignmentType.INLINE_WITHOUT_THE_SUN);
            resultDto.setWeatherIntensity(WeatherIntensity.NORMAL);
            resultDto.setWeatherType(WeatherType.OPTIMAL);
            resultDto.setMessage(ProcessMessages.SUCCESS);
        }

        return resultDto;
    }

    private void checkSunAlignment(final RuleResultDto resultDto, Point2D planetB, Point2D planetC){

        var result = VectorUtil.checkIfTwoPointAreAlignedWithOrigin(planetB,planetC);

        if(result){
            resultDto.setPlanetAlignmentType(PlanetAlignmentType.INLINE_WITH_THE_SUN);
            resultDto.setWeatherIntensity(WeatherIntensity.MAX);
            resultDto.setWeatherType(WeatherType.DROUGHT);
        }
    }

}