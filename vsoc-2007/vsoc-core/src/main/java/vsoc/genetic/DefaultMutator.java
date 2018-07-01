package vsoc.genetic;

import java.io.*;
import java.util.Random;

/**
* Generates a series of booleans. The probability of true is the mutation rate.
*/
public class DefaultMutator implements Mutator {

	Random ran;
	int mutationRate;

	/**
	* Generates a mutator that gives a series of booleans where
	* the probability of true is the mutation rate.
	*
	* @param mutRate	The mutation rate in per million. Values must be
	*			between 0 and 1000000.
	* @param seed		Initial Value for the random generator.
	*/
	public DefaultMutator (int mutRate, long seed){
		this.ran = new Random (seed);
		setMutationRate (mutRate);
	}

	/**
	* Undefined initialisation of the random generator.
	*/
	public DefaultMutator (int mutRate){
		this.ran = new Random ();
		setMutationRate (mutRate);
	}

	@Override
	public boolean isMutation () {
		return ((Math.abs(this.ran.nextInt()) % 1000000) < this.mutationRate);
	}

	public void setMutationRate (int mutRate)  {
		if (mutRate < 0) throw new Error("Mutation rate must be greater than 0");
		if (mutRate > 1000000) throw new Error("Mutation rate must be smaller than 1000000");
		this.mutationRate = mutRate;
	}

	public int getMutationRate () {
		return this.mutationRate;
	}

	public String toString () {
		StringWriter sw = new StringWriter ();

		try { write (sw);
		} catch (IOException e) {
			throw new Error (e.getMessage());
		}
		return sw.toString();
	}

	public void write (Writer w) throws IOException {
		w.write ("--- Mutator ---\n");
		w.write ("mutationRate = " + this.mutationRate + " per millon\n");
	}

}

