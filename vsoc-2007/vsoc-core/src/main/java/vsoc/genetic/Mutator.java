package vsoc.genetic;

public interface Mutator {

	/**
	* Returns the next value of the boolean sequence. Ca be used to determine if a
	* mutation has to take place or not.
	*/
	boolean isMutation();

}