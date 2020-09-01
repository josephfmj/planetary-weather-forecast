package co.com.mercadolibre.services.planetaryweatherforecast.services.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.converter.RuleResultDtoConverter;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusDto;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.process.ProcessStatusSingleResult;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.rules.RuleResultDto;
import co.com.mercadolibre.services.planetaryweatherforecast.parallel.ParallelTaskService;
import co.com.mercadolibre.services.planetaryweatherforecast.repository.WeatherForecastRepository;
import co.com.mercadolibre.services.planetaryweatherforecast.services.ProcessStatusService;
import co.com.mercadolibre.services.planetaryweatherforecast.services.WeatherForecastCalculatorService;
import io.micronaut.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;

import static co.com.mercadolibre.services.planetaryweatherforecast.constants.ProcessMessages.*;

@Singleton
public class WeatherForecastCalculatorServiceImpl implements WeatherForecastCalculatorService {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherForecastCalculatorServiceImpl.class);
    private final String FIND_PROCESS_ERROR = "fFailed to retrieve process status: ";

    private ParallelTaskService<List<RuleResultDto>,Integer> parallelTaskService;
    private final WeatherForecastRepository weatherForecastRepository;
    private final ProcessStatusService processStatusService;
    private final RuleResultDtoConverter ruleResultDtoConverter;

    public WeatherForecastCalculatorServiceImpl(WeatherForecastRepository weatherForecastRepository,
                                                ProcessStatusService processStatusService,
                                                RuleResultDtoConverter ruleResultDtoConverter,
                                                ParallelTaskService parallelTaskService){

        this.weatherForecastRepository = weatherForecastRepository;
        this.processStatusService = processStatusService;
        this.ruleResultDtoConverter = ruleResultDtoConverter;
        this.parallelTaskService = parallelTaskService;
    }

    @Async
    @Override
    public void generateToNextYears(int years,String processId) {

        /* by convention and management the total days in year is represented by 360 degrees.
         That is one degree by calculation */
        if(years>0){
            var result = this.parallelTaskService.processParallelTask(years);
            this.processResult(processId, result);
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

    private void processResult(String processId, List<RuleResultDto> response){

        final var result= this.ruleResultDtoConverter.unWrapRuleResultDto(response);
        try {
            var status= this.processStatusService.findById(processId).blockingGet();
            status.setProcessStatus(SUCCESS.name());
            status.setDescription(SUCCESS.getMessage());
            this.weatherForecastRepository.insertAll(result).blockingGet();
            this.processStatusService.update(status).blockingGet();
        }catch (Exception exception){
            LOGGER.error("Process status update failed ", exception.getCause());
        }
    }
}