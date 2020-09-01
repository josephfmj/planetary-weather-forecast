package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.DoubleComparator;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.VectorUtil;

import javax.inject.Singleton;

@Singleton
public class VectorUtilImpl implements VectorUtil {

    private DoubleComparator doubleComparator;

    public VectorUtilImpl(DoubleComparator doubleComparator){
        this.doubleComparator = doubleComparator;
    }

    @Override
    public boolean checkIfTwoPointAreAlignedWithOrigin(Point2D pointA, Point2D pointB){
        return this.checkIfPointsAreAligned(this.retrieveOrigin(),pointA,pointB);
    }
    @Override
    public boolean checkIfPointsAreAligned(Point2D pointA, Point2D pointB, Point2D pointC){

        var vectorAB = this.calculateVectorWithTwoPoints(pointA,pointB);
        var vectorAC = this.calculateVectorWithTwoPoints(pointA,pointC);
        var crossProduct = this.calculateTwoByTwoCrossProduct(vectorAB,vectorAC);

        return this.doubleComparator.nearlyEquals(0,crossProduct);
    }
    @Override
    public Point2D calculateVectorWithTwoPoints(Point2D pointA, Point2D pointB){

        return new Point2D(pointB.getXComponent() - pointA.getXComponent(),
                pointB.getYComponent() - pointA.getYComponent());
    }

    @Override
    public double calculateL2VectorNorm(Point2D pointA, Point2D pointB){

        var xCoordinatesDifference = pointB.getXComponent() - pointA.getXComponent();
        var yCoordinatesDifference = pointB.getYComponent() - pointA.getYComponent();

        return Math.sqrt(Math.pow(xCoordinatesDifference,2) + Math.pow(yCoordinatesDifference,2));
    }

    @Override
    public double calculateTwoByTwoCrossProduct(Point2D pointA, Point2D pointB) {
        return (pointA.getXComponent() * pointB.getYComponent()) - (pointA.getYComponent() * pointB.getXComponent());
    }
}