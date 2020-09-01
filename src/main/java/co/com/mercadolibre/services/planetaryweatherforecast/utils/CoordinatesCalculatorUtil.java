package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.RotationType;

public interface CoordinatesCalculatorUtil {

    double retrieveXCoordinate(final float angle, final float distance);
    double retrieveYCoordinate(final float angle, final float distance, final RotationType rotationType);
}