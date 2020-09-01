package co.com.mercadolibre.services.planetaryweatherforecast.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessStatusEntity {

    @BsonId
    private ObjectId id;
    private String processStatus;
    private String description;
}