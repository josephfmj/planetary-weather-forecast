package co.com.mercadolibre.services.planetaryweatherforecast.entities;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PlanetAlignmentType;
import co.com.mercadolibre.services.planetaryweatherforecast.dtos.math.Point2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeatherForecastEntity {

    @BsonId
    private ObjectId id;
    private String planetName;
    private int planetDay;
    private long referenceId;
    private long year;
    private float orbitalAngle;
    private Point2D coordinates;
    private PlanetAlignmentType planetAlignmentType;
    private WeatherStatusInfo weatherStatusInfo;


}