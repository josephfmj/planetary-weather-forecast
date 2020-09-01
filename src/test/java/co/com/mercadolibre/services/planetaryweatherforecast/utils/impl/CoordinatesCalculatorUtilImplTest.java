package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;


import co.com.mercadolibre.services.planetaryweatherforecast.constants.RotationType;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.CoordinatesCalculatorUtil;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

@MicronautTest
@Property(name = "calculations.epsilon", value = "0.01")
public class CoordinatesCalculatorUtilImplTest {

    @Inject
    private CoordinatesCalculatorUtil coordinatesCalculatorUtil;

    @Test
    public void testRetrieveCoordinates(){

        var xCoordinate = coordinatesCalculatorUtil.retrieveXCoordinate(15f,2000f);
        var yCoordinate = coordinatesCalculatorUtil.retrieveYCoordinate(15f,2000f, RotationType.CW);

        Assertions.assertEquals(xCoordinate,(2000f)*Math.cos(15f*(Math.PI/180)));
        Assertions.assertEquals(yCoordinate,-1*(2000f)*Math.sin(15f*(Math.PI/180)));

    }
}