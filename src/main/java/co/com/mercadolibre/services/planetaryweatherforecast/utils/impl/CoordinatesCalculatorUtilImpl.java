package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.QuadrantAngles;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.RotationType;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.CoordinatesCalculatorUtil;

import javax.inject.Singleton;
import java.util.function.BiFunction;

import static co.com.mercadolibre.services.planetaryweatherforecast.constants.QuadrantAngles.*;

@Singleton
public class CoordinatesCalculatorUtilImpl implements CoordinatesCalculatorUtil {

    @Override
    public double retrieveXCoordinate(final float angle, final float distance){

        BiFunction<Double,QuadrantAngles,Double> xCoordinateFunction = (currentAngle, quadrant) -> quadrant.retrieveXCoordinateSign() * (distance * Math.cos(currentAngle*(Math.PI/180)));
        return calculateCoordinateInQuadrant(angle, xCoordinateFunction);
    }

    @Override
    public double retrieveYCoordinate(final float angle, final float distance, final RotationType rotationType){

        var ySignDirection = 1;
        BiFunction<Double,QuadrantAngles,Double> yCoordinateFunction = (currentAngle, quadrant) -> quadrant.retrieveYCoordinateSign() * (distance * Math.sin(currentAngle*(Math.PI/180)));

        //it is reversed if the direction is clockwise
        if(RotationType.CW.equals(rotationType)){
            ySignDirection = -1;
        }

        return ySignDirection * calculateCoordinateInQuadrant(angle, yCoordinateFunction);
    }

    private static double calculateCoordinateInQuadrant(final double angle, BiFunction<Double,QuadrantAngles,Double> coordinateCalculator) {

        var quadrant = Q1;
        var equivalentAngle = angle;

        if(angle > Q1.retrieveAngle() && angle <= Q2.retrieveAngle()) {
            equivalentAngle = Q2.retrieveAngle() - angle;
            quadrant = Q2;

        } else if(angle > Q2.retrieveAngle() && angle <= Q3.retrieveAngle()) {
            equivalentAngle = angle - Q2.retrieveAngle();
            quadrant = Q3;

        } else if(angle > Q3.retrieveAngle() && angle <= Q4.retrieveAngle()) {
            equivalentAngle = Q4.retrieveAngle() - angle;
            quadrant = Q3;
        }

        return coordinateCalculator.apply(equivalentAngle,quadrant);
    }
}