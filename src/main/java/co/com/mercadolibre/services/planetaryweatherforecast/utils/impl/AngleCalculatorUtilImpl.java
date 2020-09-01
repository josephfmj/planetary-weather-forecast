package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.QuadrantAngles;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.AngleCalculatorUtil;

import javax.inject.Singleton;

@Singleton
public class AngleCalculatorUtilImpl implements AngleCalculatorUtil {

    @Override
    public float retrieveAngleByDayAndRotationType(float day, float angularVelocity){

        var currentAngle = day*angularVelocity;
        var angleInCircle = currentAngle;
        var maxAngle = calculateMaxAngleByRotation(angularVelocity);

        if(currentAngle >= QuadrantAngles.Q4.retrieveAngle()){
            angleInCircle = retrieveAngleInCircle(currentAngle, maxAngle);
        }

        return angleInCircle;
    }

    private static float calculateMaxAngleByRotation(float angularVelocity) {
        return (QuadrantAngles.Q4.retrieveAngle() / angularVelocity);
    }

    private static float retrieveAngleInCircle(float currentAngle, float maxAngle) {
        return currentAngle % maxAngle;
    }
}