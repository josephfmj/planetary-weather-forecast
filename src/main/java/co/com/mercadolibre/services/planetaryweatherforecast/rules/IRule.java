package co.com.mercadolibre.services.planetaryweatherforecast.rules;

import java.util.List;

public interface IRule<T,R> {

    R evaluate(List<T> data);
    void addNextRule(IRule nextRule);

}