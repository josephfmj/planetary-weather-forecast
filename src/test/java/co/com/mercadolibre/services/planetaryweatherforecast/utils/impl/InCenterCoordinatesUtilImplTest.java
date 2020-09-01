package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.InCenterCoordinatesUtil;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
@Property(name = "calculations.epsilon", value = "0.1")
public class InCenterCoordinatesUtilImplTest {

    @Inject
    private InCenterCoordinatesUtil inCenterCoordinatesUtil;

    @Test
    public void testPointInCenterInOrigin(){

        var pointA = new Point2D(9,4);
        var pointB = new Point2D(-7,10);
        var pointC = new Point2D(-7,-38);

        var triangle = new Triangle(pointA, pointB, pointC);

        var inCenterInOrigin = inCenterCoordinatesUtil.isPointInCenter(triangle);
        Assertions.assertTrue(inCenterInOrigin);
    }
}