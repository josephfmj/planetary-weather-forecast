package co.com.mercadolibre.services.planetaryweatherforecast.dtos.math;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Triangle {

    private Point2D pointA;
    private Point2D pointB;
    private Point2D pointC;

}