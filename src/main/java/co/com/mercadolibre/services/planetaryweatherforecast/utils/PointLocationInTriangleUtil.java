package co.com.mercadolibre.services.planetaryweatherforecast.utils;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PointLocationInTriangle;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Triangle;

import java.util.function.Function;
import java.util.function.Supplier;

public class PointLocationInTriangleUtil {

    public static PointLocationInTriangle retrieveLocation(Triangle triangle){

        var vectorAB = VectorUtil.calculateVectorWithTwoPoints(triangle.getPointA(),triangle.getPointB());
        var vectorBC = VectorUtil.calculateVectorWithTwoPoints(triangle.getPointB(),triangle.getPointC());
        var vectorCA = VectorUtil.calculateVectorWithTwoPoints(triangle.getPointC(),triangle.getPointA());
        var vectorAP = VectorUtil.calculateVectorWithTwoPoints(triangle.getPointA(),VectorUtil.ORIGIN);
        var vectorBP = VectorUtil.calculateVectorWithTwoPoints(triangle.getPointB(),VectorUtil.ORIGIN);
        var vectorCP = VectorUtil.calculateVectorWithTwoPoints(triangle.getPointC(),VectorUtil.ORIGIN);

        var crossProductAB_AP = CrossProductCalculatorUtil.calculateTwoByTwoCrossProduct(vectorAB,vectorAP);
        var crossProductBC_BP = CrossProductCalculatorUtil.calculateTwoByTwoCrossProduct(vectorBC,vectorBP);
        var crossProductCA_CP = CrossProductCalculatorUtil.calculateTwoByTwoCrossProduct(vectorCA,vectorCP);

        return evaluatePointLocation(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP)
                .apply(evalAnotherLocationSupplier(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP));

    }

    private static Function<Supplier<PointLocationInTriangle>,PointLocationInTriangle> evaluatePointLocation(final double crossProductAB_AP, final double crossProductBC_BP, final double crossProductCA_CP){

        return (supplier) -> {

            var twoAreZero = checkIfTwoPointsAreZero(crossProductAB_AP, crossProductBC_BP, crossProductCA_CP);
            if(twoAreZero){
                return PointLocationInTriangle.VERTEX;
            }

            return supplier.get();
        };
    }

    private static Supplier<PointLocationInTriangle> evalAnotherLocationSupplier(final double crossProductAB_AP, final double crossProductBC_BP, final double crossProductCA_CP){

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

    private static boolean checkIfTwoPointsAreZero(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP){

        var evalAB_AP = DoubleComparator.nearlyEquals(0,crossProductAB_AP);
        var evalBC_BP = DoubleComparator.nearlyEquals(0,crossProductBC_BP);
        var evalCA_CP = DoubleComparator.nearlyEquals( 0,crossProductCA_CP);

        return (evalAB_AP && evalBC_BP) || (evalBC_BP && evalCA_CP)|| (evalAB_AP && evalCA_CP);
    }

    private static boolean checkIfAnyPointIsZero(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        return DoubleComparator.nearlyEquals( 0,(crossProductAB_AP * crossProductBC_BP * crossProductCA_CP));
    }

    private static boolean checkIfAllHaveSameSign(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        var evalAB_AP = DoubleComparator.compare(crossProductAB_AP,0) == 1;
        var evalBC_BP = DoubleComparator.compare(crossProductBC_BP,0) == 1;
        var evalCA_CP = DoubleComparator.compare(crossProductCA_CP,0) == 1;

        var negateEvalAB_AP = DoubleComparator.compare(crossProductAB_AP,0) == -1;
        var negateEvalBC_BP = DoubleComparator.compare(crossProductBC_BP,0) == -1;
        var negateEvalCA_CP = DoubleComparator.compare(crossProductCA_CP,0) == -1;

        return (evalAB_AP && evalBC_BP & evalCA_CP) || (negateEvalAB_AP && negateEvalBC_BP && negateEvalCA_CP);
    }

    private static boolean checkIfTwoHavePositiveSign(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        var evalAB_AP = DoubleComparator.compare(crossProductAB_AP,0) == 1;
        var evalBC_BP = DoubleComparator.compare(crossProductBC_BP,0) == 1;
        var evalCA_CP = DoubleComparator.compare(crossProductCA_CP,0) == 1;

        return (evalAB_AP && evalBC_BP) || (evalBC_BP && evalCA_CP) || (evalAB_AP && evalCA_CP);
    }

    private static boolean checkIfTwoHaveNegativeSign(double crossProductAB_AP, double crossProductBC_BP, double crossProductCA_CP) {

        var evalAB_AP = DoubleComparator.compare(crossProductAB_AP,0) == -1;
        var evalBC_BP = DoubleComparator.compare(crossProductBC_BP,0) == -1;
        var evalCA_CP = DoubleComparator.compare(crossProductCA_CP,0) == -1;

        return (evalAB_AP && evalBC_BP) || (evalBC_BP && evalCA_CP) || (evalAB_AP && evalCA_CP);
    }
}