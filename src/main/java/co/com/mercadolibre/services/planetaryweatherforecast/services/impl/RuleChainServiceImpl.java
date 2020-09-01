package co.com.mercadolibre.services.planetaryweatherforecast.services.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.PlanetLocationInfoDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.rules.IRule;
import co.com.mercadolibre.services.planetaryweatherforecast.services.RuleChainService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class RuleChainServiceImpl implements RuleChainService {

    private final IRule<PlanetLocationInfoDto,RuleResultDto> inlinePlanetRuleImpl;
    private final IRule<PlanetLocationInfoDto,RuleResultDto> triangularPlanetsRuleImpl;

    @Inject
    public RuleChainServiceImpl(@Named("inlinePlanetRuleImpl") IRule inlinePlanetRuleImpl,
                                @Named("triangularPlanetsRuleImpl") IRule triangularPlanetsRuleImpl) {

        this.inlinePlanetRuleImpl = inlinePlanetRuleImpl;
        this.triangularPlanetsRuleImpl = triangularPlanetsRuleImpl;

    }

    @PostConstruct
    public void setUpRules(){
        this.inlinePlanetRuleImpl.addNextRule(this.triangularPlanetsRuleImpl);
    }

    @Override
    public RuleResultDto applyRules(List<PlanetLocationInfoDto> data) {
        return inlinePlanetRuleImpl.evaluate(data);
    }
}