package vsoc.genetic;

import java.io.Serializable;
import java.util.List;

import vsoc.nn.Net;

public interface SelectionPolicy<T extends Crossable> extends Serializable {

    List<T> createNextGeneration(List<Net> currentPopulation, CrossableFactory factory,
            double mutationRate);

    List<T> createNewGeneration(CrossableFactory factory);

    void setPopulationSize(int size);

    int getPopulationSize();

}
