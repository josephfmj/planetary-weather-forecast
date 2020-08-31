package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PointLocationInTriangle;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.PointLocationInTriangleUtil;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
@Property(name = "calculations.epsilon", value = "0.01")
public class PointLocationInTriangleUtilImplImplTest {

    @Inject
    private PointLocationInTriangleUtil pointLocationInTriangleUtil;

    @Test
    public void testRetrieveLocation_Inside(){

        Point2D pointA = new Point2D(-2,2);
        Point2D pointB = new Point2D(2,2);
        Point2D pointC = new Point2D(0,-2);

        var triangle = new Triangle(pointA,pointB,pointC);

        var result = pointLocationInTriangleUtil.retrieveLocationOriginInside(triangle);
        Assertions.assertEquals(PointLocationInTriangle.INSIDE,result);
    }

    @Test
    public void testRetrieveLocation_OutSide(){

        Point2D pointA = new Point2D(2,3);
        Point2D pointB = new Point2D(4,2);
        Point2D pointC = new Point2D(2,0);

        var triangle = new Triangle(pointA,pointB,pointC);

        var result = pointLocationInTriangleUtil.retrieveLocationOriginInside(triangle);
        Assertions.assertEquals(PointLocationInTriangle.OUTSIDE,result);
    }

    @Test
    public void testRetrieveLocation_Vertex(){

        Point2D pointA = new Point2D(0,0);
        Point2D pointB = new Point2D(4,2);
        Point2D pointC = new Point2D(2,0);

        var triangle = new Triangle(pointA,pointB,pointC);

        var result = pointLocationInTriangleUtil.retrieveLocationOriginInside(triangle);
        Assertions.assertEquals(PointLocationInTriangle.VERTEX,result);
    }

    @Test
    public void testRetrieveLocation_Edge(){

        Point2D pointA = new Point2D(-2,2);
        Point2D pointB = new Point2D(2,2);
        Point2D pointC = new Point2D(2,-2);

        var triangle = new Triangle(pointA,pointB,pointC);

        var result = pointLocationInTriangleUtil.retrieveLocationOriginInside(triangle);
        Assertions.assertEquals(PointLocationInTriangle.EDGE,result);
    }
}