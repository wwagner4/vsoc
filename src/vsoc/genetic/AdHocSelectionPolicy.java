package vsoc.genetic;

import java.util.ArrayList;
import java.util.List;

import vsoc.util.RandomIndexSelector;

public class AdHocSelectionPolicy implements SelectionPolicy {

    private int populationSize = 21;
    
    public AdHocSelectionPolicy() {
        super();
    }

    public List createNextGeneration(List sorted,
            CrossableFactory factory, double m) {
        Crossable cr1, cr2;
        List resultPop = new ArrayList();
        cr1 = (Crossable) sorted.get(0);
        resultPop.add(cr1);
        cr1 = (Crossable) sorted.get(1);
        resultPop.add(cr1);
        cr1 = (Crossable) sorted.get(2);
        resultPop.add(cr1);
        cr1 = (Crossable) sorted.get(0);
        cr2 = (Crossable) sorted.get(1);
        resultPop.add(cr1.newChild(cr2, m));
        cr1 = (Crossable) sorted.get(0);
        cr2 = (Crossable) sorted.get(2);
        resultPop.add(cr1.newChild(cr2, m));
        cr1 = (Crossable) sorted.get(1);
        cr2 = (Crossable) sorted.get(2);
        resultPop.add(cr1.newChild(cr2, m));
        RandomIndexSelector i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = (Crossable) sorted.get(0);
            cr2 = (Crossable) sorted.get(i.next());
            resultPop.add(cr1.newChild(cr2, m));
        }
        i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = (Crossable) sorted.get(1);
            cr2 = (Crossable) sorted.get(i.next());
            resultPop.add(cr1.newChild(cr2, m));
        }
        i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = (Crossable) sorted.get(2);
            cr2 = (Crossable) sorted.get(i.next());
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
