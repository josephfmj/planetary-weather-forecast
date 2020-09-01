package co.com.mercadolibre.services.planetaryweatherforecast.constants;

public enum ProcessMessages {

    IN_PROCESS("the process has started"),
    NO_MORE_RULES("there are no more associated rules"),
    UNSUPPORTED_PLANET_NUMBER("number of planets not supported"),
    FAILED("an error has occurred in the process"),
    SUCCESS("success process");

    private String message;

    ProcessMessages(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
