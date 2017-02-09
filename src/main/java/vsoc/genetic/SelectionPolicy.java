package vsoc.genetic;

import java.io.Serializable;
import java.util.List;


public interface SelectionPolicy<T> extends Serializable {

    List<T> createNextGeneration(List<T> currentPopulation, Crosser<T> crosser, double mutationRate);

    List<T> createNewGeneration(Crosser<T> crosser);

    void setPopulationSize(int size);

    int getPopulationSize();

}
