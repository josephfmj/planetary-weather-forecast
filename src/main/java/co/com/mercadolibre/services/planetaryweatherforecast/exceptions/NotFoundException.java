package co.com.mercadolibre.services.planetaryweatherforecast.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}