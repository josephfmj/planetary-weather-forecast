package co.com.mercadolibre.services.planetaryweatherforecast.constants;

public enum QuadrantAngles {

    Q1(90,1,1),
    Q2(180,-1,1),
    Q3(270,-1,-1),
    Q4(360,1,-1);

    private int angle;
    //default sign is anticlockwise by convention
    private int xCoordinateSign;
    private int yCoordinateSign;

    QuadrantAngles(int angle, int xCoordinateSign, int yCoordinateSign){
        this.angle = angle;
        this.xCoordinateSign = xCoordinateSign;
        this.yCoordinateSign = yCoordinateSign;
    }

    public int retrieveAngle(){
        return this.angle;
    }

    public int retrieveXCoordinateSign() {
        return xCoordinateSign;
    }

    public int retrieveYCoordinateSign() {
        return yCoordinateSign;
    }
}
