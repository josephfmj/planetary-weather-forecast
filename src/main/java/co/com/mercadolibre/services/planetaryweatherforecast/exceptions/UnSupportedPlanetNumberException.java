package co.com.mercadolibre.services.planetaryweatherforecast.exceptions;

public class UnSupportedPlanetNumberException extends RuntimeException{
    public UnSupportedPlanetNumberException(String message){
        super(message);
    }
}