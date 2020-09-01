package co.com.mercadolibre.services.planetaryweatherforecast.constants;

public enum RotationType {

    CW("clockwise"),
    ACW("anticlockwise");

    private String rotationName;

    RotationType(String rotationName) {
        this.rotationName = rotationName;
    }

    public String getRotationName() {
        return rotationName;
    }
}
