package co.com.mercadolibre.services.planetaryweatherforecast.dtos.error;

import io.micronaut.http.HttpStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDto {
    private String message;
    private HttpStatus status;
}