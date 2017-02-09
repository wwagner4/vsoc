package vsoc.genetic;

import java.io.Serializable;
import java.util.List;


public interface SelectionPolicy<T> extends Serializable {

    List<T> createNextGeneration(List<T> currentPopulation, Crosser<T> crosser, CrossableFactory<T> factory,
            double mutationRate);

    List<T> createNewGeneration(CrossableFactory<T> factory);

    void setPopulationSize(int size);

    int getPopulationSize();

}
