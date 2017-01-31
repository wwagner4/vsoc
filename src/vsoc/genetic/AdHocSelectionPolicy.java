package vsoc.genetic;

import java.util.ArrayList;
import java.util.List;

import vsoc.nn.Net;
import vsoc.util.RandomIndexSelector;

public class AdHocSelectionPolicy implements SelectionPolicy<Net> {

    private int populationSize = 21;
    
    public AdHocSelectionPolicy() {
        super();
    }

    public List<Net> createNextGeneration(List<Net> sorted,
            CrossableFactory factory, double m) {
    	Net cr1, cr2;
        List<Net> resultPop = new ArrayList<>();
        cr1 = sorted.get(0);
        resultPop.add(cr1);
        cr1 = sorted.get(1);
        resultPop.add(cr1);
        cr1 = sorted.get(2);
        resultPop.add(cr1);
        cr1 = sorted.get(0);
        cr2 = sorted.get(1);
        resultPop.add(cr1.newChild(cr2, m));
        cr1 = sorted.get(0);
        cr2 = sorted.get(2);
        resultPop.add(cr1.newChild(cr2, m));
        cr1 = sorted.get(1);
        cr2 = sorted.get(2);
        resultPop.add(cr1.newChild(cr2, m));
        RandomIndexSelector i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = sorted.get(0);
            cr2 = sorted.get(i.next());
            resultPop.add(cr1.newChild(cr2, m));
        }
        i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = sorted.get(1);
            cr2 = sorted.get(i.next());
            resultPop.add(cr1.newChild(cr2, m));
        }
        i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = sorted.get(2);
            cr2 = sorted.get(i.next());
            resultPop.add(cr1.newChild(cr2, m));
        }
        return resultPop;
    }

    public List createNewGeneration(CrossableFactory factory) {
        List pop = new ArrayList();
        for (int i = 0; i < this.populationSize; i++) {
            Crossable m = factory.createNewCrossableWithRandomAttributes();
            pop.add(m);
        }
        return pop;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setPopulationSize(String populationSize) {
        this.populationSize = Integer.parseInt(populationSize);
    }

}
