package co.com.mercadolibre.services.planetaryweatherforecast.parallel.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.parallel.ParallelTaskService;
import co.com.mercadolibre.services.planetaryweatherforecast.parallel.WeatherForecastCalculatorTask;
import co.com.mercadolibre.services.planetaryweatherforecast.services.PlanetLocationService;
import co.com.mercadolibre.services.planetaryweatherforecast.services.RuleChainService;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class WeatherForecastParallelTaskServiceImpl implements ParallelTaskService<List<RuleResultDto>, Integer> {

    private final int threshold;
    private final int pool;
    private final PlanetLocationService planetLocationService;
    private final RuleChainService ruleChainService;

    public WeatherForecastParallelTaskServiceImpl(@Value("${forkjoin.threshold}") int threshold,
                                                  @Value("${forkjoin.pool}") int pool,
                                                  RuleChainService ruleChainService,
                                                  PlanetLocationService planetLocationService){
        this.threshold = threshold;
        this.pool = pool;
        this.ruleChainService = ruleChainService;
        this.planetLocationService = planetLocationService;
    }

    @Override
    public List<RuleResultDto> processParallelTask(Integer years) {

        //select the max days by year (the most slowest planet to complete one rotation or year by degree)
        var maxDays= planetLocationService.retrieveMaxDaysByYearInPlanetList();
        var totalDaysByYears = IntStream.rangeClosed(1, (int)maxDays*years)
                .boxed().collect(Collectors.toList());

        var task = new WeatherForecastCalculatorTask(totalDaysByYears,this.planetLocationService, this.ruleChainService);
        task.setThreshold(threshold);

        var forkJoinPool = new ForkJoinPool(pool);
        return forkJoinPool.invoke(task);
    }
}