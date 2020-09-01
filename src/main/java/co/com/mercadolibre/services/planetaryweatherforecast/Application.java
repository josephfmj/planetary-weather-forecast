package co.com.mercadolibre.services.planetaryweatherforecast;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Planetary Weather Forecast",
                version = "1.0",
                description = "Exposes information about Weather Forecast",
                license = @License(name = "Apache 2.0", url = "http://mercadolibre.com.co")
        ),
        tags = {@Tag(name = "forecast")}
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}
