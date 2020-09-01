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

        var group = (Document) document.get("_id");

        if(Objects.nonNull(group.get("planetAlignmentType"))){
            this.planetAlignmentType = PlanetAlignmentType.valueOf(group.getString("planetAlignmentType"));
        }

        if(Objects.nonNull(group.get("weatherStatusInfo"))){
            var weatherInfo = (Document) group.get("weatherStatusInfo");
            WeatherIntensity weatherIntensity = WeatherIntensity.valueOf(weatherInfo.getString("weatherIntensity"));
            WeatherType weatherType = WeatherType.valueOf(weatherInfo.getString("weatherType"));
            this.weatherStatusInfo = new WeatherStatusInfo(weatherIntensity,weatherType);
        }

        if(Objects.nonNull(document.getInteger("totalDays"))){
            this.totalDays = document.getInteger("totalDays");
        }

        if(Objects.nonNull(document.getLong("year"))){
            this.year = document.getLong("year");
        }

    }
}