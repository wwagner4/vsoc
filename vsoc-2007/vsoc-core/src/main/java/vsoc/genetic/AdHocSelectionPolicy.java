package vsoc.genetic;

import java.util.*;

import vsoc.util.RandomIndexSelector;

public class AdHocSelectionPolicy<T> implements SelectionPolicy<T> {

		private static final long serialVersionUID = 1L;
    
		private int populationSize = 21;
		
		private Random ran = new Random();
    
    public AdHocSelectionPolicy() {
        super();
    }

    public List<T> createNextGeneration(List<T> sorted, Crosser<T> crosser, double m) {
    	T cr1;
    	T cr2;
        List<T> resultPop = new ArrayList<>();
        cr1 = sorted.get(0);
        resultPop.add(cr1);
        cr1 = sorted.get(1);
        resultPop.add(cr1);
        cr1 = sorted.get(2);
        resultPop.add(cr1);
        cr1 = sorted.get(0);
        cr2 = sorted.get(1);
        resultPop.add(crosser.newChild(cr1, cr2, m));
        cr1 = sorted.get(0);
        cr2 = sorted.get(2);
        resultPop.add(crosser.newChild(cr1, cr2, m));
        cr1 = sorted.get(1);
        cr2 = sorted.get(2);
        resultPop.add(crosser.newChild(cr1, cr2, m));
        RandomIndexSelector i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = sorted.get(0);
            cr2 = sorted.get(i.next());
            resultPop.add(crosser.newChild(cr1, cr2, m));
        }
        i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = sorted.get(1);
            cr2 = sorted.get(i.next());
            resultPop.add(crosser.newChild(cr1, cr2, m));
        }
        i = new RandomIndexSelector(3, 17, 5);
        while (i.hasNext()) {
            cr1 = sorted.get(2);
            cr2 = sorted.get(i.next());
            resultPop.add(crosser.newChild(cr1, cr2, m));
        }
        return resultPop;
    }

	@Override
	public List<T> createNewGeneration(Crosser<T> crosser) {
        List<T> pop = new ArrayList<>();
        for (int i = 0; i < this.populationSize; i++) {
            T mm = crosser.create(ran.nextLong());
            pop.add(mm);
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
