package co.com.mercadolibre.services.planetaryweatherforecast.utils.impl;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PointLocationInTriangle;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.DoubleComparator;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.PointLocationInTriangleUtil;
import co.com.mercadolibre.services.planetaryweatherforecast.utils.VectorUtil;

import javax.inject.Singleton;
import java.util.function.Function;
import java.util.function.Supplier;

@Singleton
public class PointLocationInTriangleUtilImpl implements PointLocationInTriangleUtil {

    private Point2D origin;
    private VectorUtil vectorUtil;
    private DoubleComparator doubleComparator;

    public PointLocationInTriangleUtilImpl(VectorUtil vectorUtil, DoubleComparator doubleComparator){
        this.vectorUtil = vectorUtil;
        this.doubleComparator = doubleComparator;
        this.origin = this.vectorUtil.retrieveOrigin();
    }

    @Override
    public PointLocationInTriangle retrieveLocation(Triangle triangle, Point2D point2D){

        var vectorAB = this.vectorUtil.calculateVectorWithTwoPoints(triangle.getPointA(),triangle.getPointB());
        var vectorBC = this.vectorUtil.calculateVectorWithTwoPoints(triangle.getPointB(),triangle.getPointC());
        var vectorCA = this.vectorUtil.calculateVectorWithTwoPoints(triangle.getPointC(),triangle.getPointA());
        var vectorAP = this.vectorUtil.calculateVectorWithTwoPoints(triangle.getPointA(), point2D );
        var vectorBP = this.vectorUtil.calculateVectorWithTwoPoints(triangle.getPointB(), point2D);
        var vectorCP = this.vectorUtil.calculateVectorWithTwoPoints(triangle.getPointC(), point2D);

        var crossProductAB_AP = this.vectorUtil.calculateTwoByTwoCrossProduct(vectorAB,vectorAP);
        var crossProductBC_BP = this.vectorUtil.calculateTwoByTwoCrossProduct(vectorBC,vectorBP);
        var crossProductCA_CP = this.vectorUtil.calculateTwoByTwoCrossProduct(vectorCA,vectorCP);

        return evaluatePointLocation(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP)
                .apply(evalAnotherLocationSupplier(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP));

    }

    @Override
    public PointLocationInTriangle retrieveLocationOriginInside(Triangle triangle) {
        return this.retrieveLocation(triangle,this.origin);
    }

    private Function<Supplier<PointLocationInTriangle>,PointLocationInTriangle> evaluatePointLocation(final double crossProductAB_AP, final double crossProductBC_BP, final double crossProductCA_CP){

        return (supplier) -> {

            var twoAreZero = checkIfTwoPointsAreZero(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP);
            if(twoAreZero){
                return PointLocationInTriangle.VERTEX;
            }

            return supplier.get();
        };
    }

    private Supplier<PointLocationInTriangle> evalAnotherLocationSupplier(final double crossProductAB_AP, final double crossProductBC_BP, final double crossProductCA_CP){

        return () -> {

            var anyIsZero = checkIfAnyPointIsZero(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP);
            var twoArePositiveSign = checkIfTwoHavePositiveSign(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP);
            var twoAreNegativeSign = checkIfTwoHaveNegativeSign(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP);
            var sameSign = checkIfAllHaveSameSign(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP);

            if(anyIsZero && (twoAreNegativeSign || twoArePositiveSign)){
                return PointLocationInTriangle.EDGE;
            } else if(sameSign){
                return PointLocationInTriangle.INSIDE;
            }

            return PointLocationInTriangle.OUTSIDE;
        };
    }

    private boolean checkIfTwoPointsAreZero(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP){

        var evalAB_AP = this.doubleComparator.nearlyEquals(0,crossProductAB_AP);
        var evalBC_BP = this.doubleComparator.nearlyEquals(0,crossProductBC_BP);
        var evalCA_CP = this.doubleComparator.nearlyEquals( 0,crossProductCA_CP);

        return (evalAB_AP && evalBC_BP) || (evalBC_BP && evalCA_CP)|| (evalAB_AP && evalCA_CP);
    }

    private boolean checkIfAnyPointIsZero(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        return this.doubleComparator.nearlyEquals( 0,(crossProductAB_AP * crossProductBC_BP * crossProductCA_CP));
    }

    private boolean checkIfAllHaveSameSign(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        var evalAB_AP = this.doubleComparator.compare(crossProductAB_AP,0) == 1;
        var evalBC_BP = this.doubleComparator.compare(crossProductBC_BP,0) == 1;
        var evalCA_CP = this.doubleComparator.compare(crossProductCA_CP,0) == 1;

        var negateEvalAB_AP = this.doubleComparator.compare(crossProductAB_AP,0) == -1;
        var negateEvalBC_BP = this.doubleComparator.compare(crossProductBC_BP,0) == -1;
        var negateEvalCA_CP = this.doubleComparator.compare(crossProductCA_CP,0) == -1;

        return (evalAB_AP && evalBC_BP & evalCA_CP) || (negateEvalAB_AP && negateEvalBC_BP && negateEvalCA_CP);
    }

    private boolean checkIfTwoHavePositiveSign(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        var evalAB_AP = this.doubleComparator.compare(crossProductAB_AP,0) == 1;
        var evalBC_BP = this.doubleComparator.compare(crossProductBC_BP,0) == 1;
        var evalCA_CP = this.doubleComparator.compare(crossProductCA_CP,0) == 1;

        return (evalAB_AP && evalBC_BP) || (evalBC_BP && evalCA_CP) || (evalAB_AP && evalCA_CP);
    }

    private boolean checkIfTwoHaveNegativeSign(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        var evalAB_AP = this.doubleComparator.compare(crossProductAB_AP,0) == -1;
        var evalBC_BP = this.doubleComparator.compare(crossProductBC_BP,0) == -1;
        var evalCA_CP = this.doubleComparator.compare(crossProductCA_CP,0) == -1;

        return (evalAB_AP && evalBC_BP) || (evalBC_BP && evalCA_CP) || (evalAB_AP && evalCA_CP);
    }
}