package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class InCenterCoordinatesUtil {

    public static Point2D retrieveInCenter(final Triangle triangle){
        var xCoordinate = retrieveXCoordinate(triangle);
        var yCoordinate = retrieveYCoordinate(triangle);
        return new Point2D(xCoordinate,yCoordinate);
    }

    public static boolean isPointInCenter(final Triangle triangle) {
        var inCenter = retrieveInCenter(triangle);
        return DoubleComparator.nearlyEquals(VectorUtil.ORIGIN.getXComponent(),inCenter.getXComponent()) &&
                DoubleComparator.nearlyEquals(VectorUtil.ORIGIN.getYComponent() , inCenter.getYComponent());
    }

    public static double retrieveXCoordinate(final Triangle triangle){

        Function<List<Double>, Double> xCoordinateCalculator =
                (edges) -> ((edges.get(0) * triangle.getPointA().getXComponent()) +
                        (edges.get(1) * triangle.getPointB().getXComponent()) +
                        (edges.get(2) * triangle.getPointC().getXComponent())) /
                        (edges.get(0) + edges.get(1) + edges.get(2));

        return retrieveCoordinate(triangle, xCoordinateCalculator);
    }

    public static double retrieveYCoordinate(final Triangle triangle){

        Function<List<Double>, Double> yCoordinateCalculator =
                (edges) -> ((edges.get(0) * triangle.getPointA().getYComponent()) +
                        (edges.get(1) * triangle.getPointB().getYComponent()) +
                        (edges.get(2) * triangle.getPointC().getYComponent())) /
                        (edges.get(0) + edges.get(1) + edges.get(2));

        return retrieveCoordinate(triangle, yCoordinateCalculator);
    }

    private static double retrieveCoordinate(Triangle triangle, Function<List<Double>, Double> calculator){

        final var aEdge = VectorUtil.calculateL2VectorNorm(triangle.getPointB(),triangle.getPointC());
        final var bEdge = VectorUtil.calculateL2VectorNorm(triangle.getPointA(),triangle.getPointC());
        final var cEdge = VectorUtil.calculateL2VectorNorm(triangle.getPointA(),triangle.getPointB());

        final var edges = Arrays.asList(aEdge,bEdge,cEdge);

        return calculator.apply(edges);
    }
}