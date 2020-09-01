package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.DoubleComparator;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.InCenterCoordinatesUtil;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.VectorUtil;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Singleton
public class InCenterCoordinatesUtilImpl implements InCenterCoordinatesUtil {

    private VectorUtil vectorUtil;
    private DoubleComparator doubleComparator;

    public InCenterCoordinatesUtilImpl(VectorUtil vectorUtil, DoubleComparator doubleComparator){
        this.vectorUtil = vectorUtil;
        this.doubleComparator = doubleComparator;
    }

    @Override
    public Point2D retrieveInCenter(final Triangle triangle){
        var xCoordinate = retrieveXCoordinate(triangle);
        var yCoordinate = retrieveYCoordinate(triangle);
        return new Point2D(xCoordinate,yCoordinate);
    }

    @Override
    public boolean isPointInCenter(final Triangle triangle) {
        var inCenter = retrieveInCenter(triangle);
        return this.doubleComparator.nearlyEquals(this.vectorUtil.retrieveOrigin().getXComponent(),inCenter.getXComponent()) &&
                this.doubleComparator.nearlyEquals(this.vectorUtil.retrieveOrigin().getYComponent() , inCenter.getYComponent());
    }

    @Override
    public double retrieveXCoordinate(final Triangle triangle){

        Function<List<Double>, Double> xCoordinateCalculator =
                (edges) -> ((edges.get(0) * triangle.getPointA().getXComponent()) +
                        (edges.get(1) * triangle.getPointB().getXComponent()) +
                        (edges.get(2) * triangle.getPointC().getXComponent())) /
                        (edges.get(0) + edges.get(1) + edges.get(2));

        return retrieveCoordinate(triangle, xCoordinateCalculator);
    }

    @Override
    public double retrieveYCoordinate(final Triangle triangle){

        Function<List<Double>, Double> yCoordinateCalculator =
                (edges) -> ((edges.get(0) * triangle.getPointA().getYComponent()) +
                        (edges.get(1) * triangle.getPointB().getYComponent()) +
                        (edges.get(2) * triangle.getPointC().getYComponent())) /
                        (edges.get(0) + edges.get(1) + edges.get(2));

        return retrieveCoordinate(triangle, yCoordinateCalculator);
    }

    private double retrieveCoordinate(Triangle triangle, Function<List<Double>, Double> calculator){

        final var aEdge = this.vectorUtil.calculateL2VectorNorm(triangle.getPointB(),triangle.getPointC());
        final var bEdge = this.vectorUtil.calculateL2VectorNorm(triangle.getPointA(),triangle.getPointC());
        final var cEdge = this.vectorUtil.calculateL2VectorNorm(triangle.getPointA(),triangle.getPointB());

        final var edges = Arrays.asList(aEdge,bEdge,cEdge);

        return calculator.apply(edges);
    }
}