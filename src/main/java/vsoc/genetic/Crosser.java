package vsoc.genetic;

public interface Crosser<T> {
	
    /**
    * Creates a new instance of a new crossable object by applying
    * crossover and mutation on two parent objects.
    */
    T newChild(T c1, T c2, double mutationRate);

    T create(long seed);
    
    double distance(T c1, T c2);
    

}
