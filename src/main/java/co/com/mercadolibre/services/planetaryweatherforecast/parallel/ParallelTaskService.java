package co.com.mercadolibre.services.planetaryweatherforecast.parallel;

public interface ParallelTaskService<R,T> {

    R processParallelTask(T inputs);
}