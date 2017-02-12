package vsoc.genetic;

public interface CrossoverSwitch {

	/**
	 * Crossover takes place between Parent A and Parent B.
	 * 
	 * True if the genetic values should be taken from Parent A. 
	 * Otherwise they should be taken from Parent B.  
	 */
	boolean takeA();

}