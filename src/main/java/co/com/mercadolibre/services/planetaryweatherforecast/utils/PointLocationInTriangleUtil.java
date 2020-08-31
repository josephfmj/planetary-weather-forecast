package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PointLocationInTriangle;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;

public interface PointLocationInTriangleUtil {

        PointLocationInTriangle retrieveLocation(Triangle triangle, Point2D point2D);
        PointLocationInTriangle retrieveLocationOriginInside(Triangle triangle);
}