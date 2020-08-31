package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;

public class CrossProductCalculatorUtil {

    public static double calculateTwoByTwoCrossProduct(Point2D vector_one, Point2D vector_two){

        return (vector_one.getXComponent() * vector_two.getYComponent()) - (vector_one.getYComponent() * vector_two.getXComponent());
    }
}