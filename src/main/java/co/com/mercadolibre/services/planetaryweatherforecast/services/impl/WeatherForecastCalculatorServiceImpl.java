package co.com.mercadolibre.services.planetaryweatherforecast.services.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.ProcessMessages;
import co.com.mercadolibre.services.planetaryweatherforecast.converter.RuleResultDtoConverter;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusSingleResult;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherForecastEntity;
import co.com.mercadolibre.services.planetaryweatherforecast.parallel.WeatherForecastCalculatorTask;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.WeatherForecastRepository;
import co.com.mercadolibre.services.planetaryweatherforecast.services.PlanetLocationService;
import co.com.mercadolibre.services.planetaryweatherforecast.services.ProcessStatusService;
import co.com.mercadolibre.services.planetaryweatherforecast.services.RuleChainService;
import co.com.mercadolibre.services.planetaryweatherforecast.services.WeatherForecastCalculatorService;
import io.micronaut.context.annotation.Value;
import io.micronaut.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static co.com.mercadolibre.services.planetaryweatherforecast.constants.ProcessMessages.*;

@Singleton
public class WeatherForecastCalculatorServiceImpl implements WeatherForecastCalculatorService {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherForecastCalculatorServiceImpl.class);
    private final String FIND_PROCESS_ERROR = "fFailed to retrieve process status: ";

    private final int threshold;
    private final int pool;
    private final RuleChainService ruleChainService;
    private final PlanetLocationService planetLocationService;
    private final WeatherForecastRepository weatherForecastRepository;
    private final ProcessStatusService processStatusService;
    private final RuleResultDtoConverter ruleResultDtoConverter;

    public WeatherForecastCalculatorServiceImpl(@Value("${forkjoin.threshold}") int threshold,
                                                @Value("${forkjoin.pool}") int pool,
                                                RuleChainService ruleChainService,
                                                PlanetLocationService planetLocationService,
                                                WeatherForecastRepository weatherForecastRepository,
                                                ProcessStatusService processStatusService,
                                                RuleResultDtoConverter ruleResultDtoConverter){
        this.threshold = threshold;
        this.pool = pool;
        this.ruleChainService = ruleChainService;
        this.planetLocationService = planetLocationService;
        this.weatherForecastRepository = weatherForecastRepository;
        this.processStatusService = processStatusService;
        this.ruleResultDtoConverter = ruleResultDtoConverter;
    }

    @Async
    @Override
    public void generateToNextYears(int years,String processId) {

        /* by convention and management the total days in year is represented by 360 degrees.
         That is one degree by day */
        if(years>0){
            this.startProcess(years,processId);
        } else {
            LOGGER.warn("Invalid year value");
        }

    }

    @Override
    public ProcessStatusSingleResult<String> initializeProcess(){
        var processStatusDto = new ProcessStatusDto();
        processStatusDto.setDescription(IN_PROCESS.getMessage());
        processStatusDto.setProcessStatus(IN_PROCESS.name());
        return this.processStatusService.insert(processStatusDto)
                .blockingGet();
    }

    @Override
    public ProcessStatusDto checkResult(String id) {
        return this.processStatusService.findById(id).blockingGet();
    }

    private List<RuleResultDto> processParallelTask(final List<Integer> totalDaysByYears){
        var task = new WeatherForecastCalculatorTask(totalDaysByYears,this.planetLocationService, this.ruleChainService);
        task.setThreshold(threshold);

        var forkJoinPool = new ForkJoinPool(pool);
        return forkJoinPool.invoke(task);
    }

    private void startProcess(int years,String processId){

        //select the max days by year (the most slowest planet to complete one rotation or year by degree)
        var maxDays= planetLocationService.retrieveMaxDaysByYearInPlanetList();
        var totalDaysByYears = IntStream.rangeClosed(1, (int)maxDays*years)
                .boxed().collect(Collectors.toList());
        var result = this.processParallelTask(totalDaysByYears);
        this.processResult(processId, result);
    }

    private void processResult(String processId, List<RuleResultDto> response){

        final var result= this.ruleResultDtoConverter.unWrapRuleResultDto(response);

//        this.processStatusService.findById(processId)
//                .doOnSuccess((process) -> this.saveWeatherForecastResult(result, process))
//                .doOnError((error)-> LOGGER.error(FIND_PROCESS_ERROR,error.getCause()));
        var status= this.processStatusService.findById(processId).blockingGet();
        status.setProcessStatus(SUCCESS.name());
        status.setDescription(SUCCESS.getMessage());
        this.weatherForecastRepository.insertAll(result).blockingGet();
        this.processStatusService.update(status).blockingGet();
    }

    private void saveWeatherForecastResult(List<WeatherForecastEntity> result, ProcessStatusDto processStatusDto){
        this.weatherForecastRepository.insertAll(result)
                .doOnSuccess((total) -> this.successToSaveWeatherForecast(total,processStatusDto))
                .doOnError((error) -> this.failedToSaveWeatherForecast(error, processStatusDto));
    }

    private void successToSaveWeatherForecast(Integer total, ProcessStatusDto processStatusDto){

        LOGGER.info("Inserted all weather forecast registers, total:", total);
        processStatusDto.setProcessStatus(SUCCESS.name());
        processStatusDto.setDescription(SUCCESS.getMessage());
        this.updateProcess(processStatusDto);
    }

    private void failedToSaveWeatherForecast(Throwable error, ProcessStatusDto processStatusDto) {
        LOGGER.error("Failed to insert weather forecast registers, error: ",error.getCause());
        this.updateProcessStatusEntity(processStatusDto,FAILED);
    }

    private void updateProcessStatusEntity(ProcessStatusDto processStatusDto, ProcessMessages processMessages) {
        processStatusDto.setProcessStatus(processMessages.name());
        processStatusDto.setDescription(processMessages.getMessage());

        this.updateProcess(processStatusDto);
    }

    private void updateProcess(ProcessStatusDto processStatusDto){
        this.processStatusService.update(processStatusDto)
                .doOnSuccess((total) -> LOGGER.info("Process status update successful. Total updated: ", total))
                .doOnError((error) -> LOGGER.error("Process status update failed ", error.getCause()));
    }
}