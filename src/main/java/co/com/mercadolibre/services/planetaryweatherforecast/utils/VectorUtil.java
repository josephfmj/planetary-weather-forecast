package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;

public interface VectorUtil {

    default Point2D retrieveOrigin(){ return new Point2D(0,0);}
    boolean checkIfTwoPointAreAlignedWithOrigin(Point2D pointA, Point2D pointB);
    boolean checkIfPointsAreAligned(Point2D pointA, Point2D pointB, Point2D pointC);
    Point2D calculateVectorWithTwoPoints(Point2D pointA, Point2D pointB);
    double calculateL2VectorNorm(Point2D pointA, Point2D pointB);
    double calculateTwoByTwoCrossProduct(Point2D pointA, Point2D pointB);
}