package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;

public interface InCenterCoordinatesUtil {

    Point2D retrieveInCenter(final Triangle triangle);
    boolean isPointInCenter(final Triangle triangle);
    double retrieveXCoordinate(final Triangle triangle);
    double retrieveYCoordinate(final Triangle triangle);
}