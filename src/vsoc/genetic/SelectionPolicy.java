package vsoc.genetic;

import java.io.Serializable;
import java.util.List;

public interface SelectionPolicy extends Serializable {

    List createNextGeneration(List currentPopulation, CrossableFactory factory,
            double mutationRate);

    List createNewGeneration(CrossableFactory factory);

    void setPopulationSize(int size);

    int getPopulationSize();

}
