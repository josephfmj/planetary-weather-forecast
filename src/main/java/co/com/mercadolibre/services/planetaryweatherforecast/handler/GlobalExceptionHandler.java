package co.com.mercadolibre.services.planetaryweatherforecast.handler;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.error.ErrorDto;
import co.com.mercadolibre.services.planetaryweatherforecast.exceptions.NotFoundException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {RuntimeException.class, ExceptionHandler.class})
public class GlobalExceptionHandler implements ExceptionHandler<RuntimeException, ErrorDto> {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Override
    public ErrorDto handle(HttpRequest request, RuntimeException exception) {
        return this.processException(exception);
    }

    private ErrorDto processException(RuntimeException exception) {

        LOGGER.error("error in application: ", exception.getCause());
        var errorDtoBuilder = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("error in application");

        if (exception.getCause() instanceof NotFoundException){
            return errorDtoBuilder.status(HttpStatus.NOT_FOUND)
                    .message("Not found")
                    .build();
        }

        return errorDtoBuilder.build();
    }
}