package co.com.mercadolibre.services.planetaryweatherforecast.parallel;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.services.PlanetLocationService;
import co.com.mercadolibre.services.planetaryweatherforecast.services.RuleChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class WeatherForecastCalculatorTask extends RecursiveTask<List<RuleResultDto>> {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherForecastCalculatorTask.class);

    private static int THRESHOLD;
    private List<Integer> days;
    private PlanetLocationService coordinatesCalculatorService;
    private RuleChainService ruleChainService;

    public WeatherForecastCalculatorTask(List days, PlanetLocationService coordinatesCalculatorService, RuleChainService ruleChainService){
        this.days = days;
        this.coordinatesCalculatorService = coordinatesCalculatorService;
        this.ruleChainService = ruleChainService;
    }

    @Override
    protected List<RuleResultDto> compute() {

        if (this.days.size() < THRESHOLD) {
            LOGGER.debug("Compute directly Thread: " + Thread.currentThread().getName()+" size list: "+this.days.size());
            return process();

        }

        return ForkJoinTask.invokeAll(createSubTasks())
                .stream()
                .map(ForkJoinTask::join)
                .reduce(new ArrayList<>(),(partial, current) -> {
                    partial.addAll(current);
                    return partial;
                });
    }

    public void setThreshold(int threshold){
        THRESHOLD = threshold;
    }

    private Collection<WeatherForecastCalculatorTask> createSubTasks() {

        var middle = this.days.size()/2;
        var array1= new ArrayList<>(this.days.subList(0,middle));
        var array2= new ArrayList<>(this.days.subList(middle,this.days.size()));
        List<WeatherForecastCalculatorTask> subTasks = new ArrayList<>();
        subTasks.add(new WeatherForecastCalculatorTask(array1,this.coordinatesCalculatorService, this.ruleChainService));
        subTasks.add(new WeatherForecastCalculatorTask(array2,this.coordinatesCalculatorService, this.ruleChainService));

        return subTasks;
    }

    private List<RuleResultDto> process() {

        return this.days.stream()
                .map(day -> this.coordinatesCalculatorService.retrieveLocation(day))
                .map(coordinatesInfo -> this.ruleChainService.applyRules(coordinatesInfo))
                .collect(Collectors.toList());

    }
}