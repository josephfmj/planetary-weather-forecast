package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.utils.DoubleComparator;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Singleton
public class DoubleComparatorImpl implements DoubleComparator {

    private double epsilon;

    public DoubleComparatorImpl(@Value("${calculations.epsilon}") double epsilon){
        this.epsilon = epsilon;
    }

    @Override
    public boolean nearlyEquals(double one, double two){

        var diff = Math.abs(one - two);
        if (diff <= this.epsilon){
            return true;
        }

        return false;

    }

    @Override
    public int compare(double one, double two){

        if(nearlyEquals(one,two)){
            return 0;
        }else if(one>two){
            return 1;
        }else {
            return -1;
        }
    }
}