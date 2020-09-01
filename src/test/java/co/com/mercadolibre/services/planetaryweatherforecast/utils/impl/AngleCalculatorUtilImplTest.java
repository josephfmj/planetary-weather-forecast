package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.utils.AngleCalculatorUtil;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
@Property(name = "calculations.epsilon", value = "0.01")
public class AngleCalculatorUtilImplTest {

    @Inject
    private AngleCalculatorUtil angleCalculatorUtil;

    @Test
    public void retrieveAngleByDayAndRotationTypeZeroRotation(){

        var day = 15f;
        var angularVelocity = 1;
        var equivalentAngle= angleCalculatorUtil.retrieveAngleByDayAndRotationType(day,angularVelocity);
        Assertions.assertEquals(equivalentAngle,15f);
    }

    @Test
    public void retrieveAngleByDayAndRotationTypeManyRotation(){

        var day = 750f;
        var angularVelocity = 1;
        var equivalentAngle= angleCalculatorUtil.retrieveAngleByDayAndRotationType(day,angularVelocity);
        Assertions.assertEquals(equivalentAngle,30f);
    }

}