package co.com.mercadolibre.services.planetaryweatherforecast.utils;


import co.com.mercadolibre.services.planetaryweatherforecast.constants.RotationType;
import org.junit.jupiter.api.Test;

public class CoordinatesCalculatorUtilTest {

    @Test
    public void testRetrieveXCoordinate_Planet_Betasoide(){
        var xcoordinate = CoordinatesCalculatorUtil.retrieveXCoordinate(15f,2000f);
        var ycoordinate = CoordinatesCalculatorUtil.retrieveYCoordinate(15f,2000f, RotationType.CW);
        var testy = (2000f)*Math.sin(15f);
        var testx = (2000f)*Math.cos(15f);

        String hola = "hola";
    }
}