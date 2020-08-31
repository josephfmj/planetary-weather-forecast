package co.com.mercadolibre.services.planetaryweatherforecast.utils;

public interface DoubleComparator {

    boolean nearlyEquals(double one, double two);
    int compare(double one, double two);
}