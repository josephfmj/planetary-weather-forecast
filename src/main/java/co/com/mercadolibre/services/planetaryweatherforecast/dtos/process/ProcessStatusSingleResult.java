package co.com.mercadolibre.services.planetaryweatherforecast.dtos.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessStatusSingleResult<T> {

    private T result;
}