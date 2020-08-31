package co.com.mercadolibre.services.planetaryweatherforecast.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanetRelatedInfo {

    private String planetName;
    private float equivalentDay;
}