package co.com.mercadolibre.services.planetaryweatherforecast.utils;

public class DoubleComparator {

    private static final double EPSILON = 0.0000001d;

    public static boolean nearlyEquals(double one, double two){

        var diff = Math.abs(one - two);
        if (diff <=  EPSILON){
            return true;
        }

        return false;

    }

    public static int compare(double one, double two){

        if(nearlyEquals(one,two)){
            return 0;
        }else if(one>two){
            return 1;
        }else {
            return -1;
        }
    }
}