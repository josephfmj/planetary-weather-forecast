package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;

public class VectorUtil {

    public static final Point2D ORIGIN = new Point2D(0,0);

    public static boolean checkIfTwoPointAreAlignedWithOrigin(Point2D pointA, Point2D pointB){
        return checkIfPointsAreAligned(ORIGIN,pointA,pointB);
    }

    public static boolean checkIfPointsAreAligned(Point2D pointA, Point2D pointB, Point2D pointC){

        var vectorAB = VectorUtil.calculateVectorWithTwoPoints(pointA,pointB);
        var vectorAC = VectorUtil.calculateVectorWithTwoPoints(pointA,pointC);
        var crossProduct = CrossProductCalculatorUtil.calculateTwoByTwoCrossProduct(vectorAB,vectorAC);

        return DoubleComparator.nearlyEquals(0,crossProduct);
    }
    
    public static Point2D calculateVectorWithTwoPoints(Point2D pointA, Point2D pointB){

        return new Point2D(pointB.getXComponent() - pointA.getXComponent(),
                pointB.getYComponent() - pointA.getYComponent());
    }

    public static double calculateL2VectorNorm(Point2D pointA, Point2D pointB){

        var xCoordinatesDifference = pointB.getXComponent() - pointA.getXComponent();
        var yCoordinatesDifference = pointB.getYComponent() - pointA.getYComponent();

        return Math.sqrt(Math.pow(xCoordinatesDifference,2) + Math.pow(yCoordinatesDifference,2));
    }
}