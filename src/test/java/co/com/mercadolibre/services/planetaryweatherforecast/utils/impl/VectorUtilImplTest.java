package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.VectorUtil;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
@Property(name = "calculations.epsilon", value = "0.01")
public class VectorUtilImplTest {

    @Inject
    private VectorUtil vectorUtil;

    @Test
    public void testCheckIfPointsAreAlignedWithOrigin(){

        var pointA = new Point2D(1,1);
        var pointB = new Point2D(2,2);
        var pointC = new Point2D(3,3);

        var areAligned = vectorUtil.checkIfPointsAreAligned(pointA,pointB,pointC);
        var areAlignedWithOrigin = vectorUtil.checkIfTwoPointAreAlignedWithOrigin(pointB, pointC);
        Assertions.assertTrue(areAligned);
        Assertions.assertTrue(areAlignedWithOrigin);
    }

    @Test
    public void testCheckIfPointsAreAlignedWithoutOriginHorizontally(){

        var pointA = new Point2D(1,1);
        var pointB = new Point2D(2,1);
        var pointC = new Point2D(3,1);

        var areAligned = vectorUtil.checkIfPointsAreAligned(pointA,pointB,pointC);
        var areAlignedWithOrigin = vectorUtil.checkIfTwoPointAreAlignedWithOrigin(pointB, pointC);
        Assertions.assertTrue(areAligned);
        Assertions.assertFalse(areAlignedWithOrigin);
    }

    @Test
    public void testCheckIfPointsAreAlignedWithoutOriginVertically(){

        var pointA = new Point2D(1,1);
        var pointB = new Point2D(2,1);
        var pointC = new Point2D(3,1);

        var areAligned = vectorUtil.checkIfPointsAreAligned(pointA,pointB,pointC);
        var areAlignedWithOrigin = vectorUtil.checkIfTwoPointAreAlignedWithOrigin(pointB, pointC);
        Assertions.assertTrue(areAligned);
        Assertions.assertFalse(areAlignedWithOrigin);
    }
}