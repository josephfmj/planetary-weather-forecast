package co.com.mercadolibre.services.planetaryweatherforecast.dtos.weather;

import co.com.mercadolibre.services.planetaryweatherforecast.constants.PlanetAlignmentType;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherIntensity;
import co.com.mercadolibre.services.planetaryweatherforecast.constants.WeatherType;
import co.com.mercadolibre.services.planetaryweatherforecast.entities.WeatherStatusInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeatherForecastPeriodDto {

    private Long year;
    private int totalDays;
    private PlanetAlignmentType planetAlignmentType;
    private WeatherStatusInfo weatherStatusInfo;

    public WeatherForecastPeriodDto(Document document){

        if(Objects.nonNull(document.get("planetAlignmentType"))){
            this.planetAlignmentType = PlanetAlignmentType.valueOf(document.getString("planetAlignmentType"));
        }

        if(Objects.nonNull(document.get("weatherStatusInfo"))){
            var intensity = (Document) document.get("weatherStatusInfo");
            var type = (Document) document.get("weatherStatusInfo");
            WeatherIntensity weatherIntensity = WeatherIntensity.valueOf(intensity.getString("weatherIntensity"));
            WeatherType weatherType = WeatherType.valueOf(type.getString("weatherType"));
            this.weatherStatusInfo = new WeatherStatusInfo(weatherIntensity,weatherType);
        }

        if(Objects.nonNull(document.getString("totalDays"))){
            this.totalDays = Integer.parseInt(document.getString("totalDays"));
        }

        if(Objects.nonNull(document.getLong("year"))){
            this.year = document.getLong("year");
        }

    }
}