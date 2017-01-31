package vsoc.genetic;

import java.util.*;
import java.io.*;

/**
 * A Crossover switch which generates a boolean that switches from true to false
 * after random intervals. The interval length is normal distributed.
 */
public class CrossoverSwitch {

    private Random ran;

    private int mean;

    private int var;

    private int nextSwitchAt;

    private int count;

    private boolean takeA = true;

    /**
     * @param mean
     *            The mean length of an interval.
     * @param var
     *            The variance of the length of the interval.
     * @param seed
     *            Initial Value for the random generator.
     */
    public CrossoverSwitch(int mean, int var, long seed) {
        this.ran = new Random(seed);
        this.mean = mean;
        this.var = var;
        this.count = 0;
        this.nextSwitchAt = setNextSwitchAt(mean, var, this.ran);
    }

    /**
     * Undefined initialisation of the random generator.
     */
    public CrossoverSwitch(int mean, int var) {
        this.ran = new Random();
        this.mean = mean;
        this.var = var;
        this.count = 0;
        this.nextSwitchAt = setNextSwitchAt(mean, var, this.ran);
    }

    private static int setNextSwitchAt(int mean, int var, Random ran) {
        return mean + (int) (ran.nextGaussian() * var);
    }

    private int setNextSwitchAt() {
        return setNextSwitchAt(this.mean, this.var, this.ran);
    }

    /**
     * Returns the next value of the boolean sequence. Can be used to determine
     * if a mutation has to take place or not.
     */
    public boolean takeA() {
        if (this.count >= this.nextSwitchAt) {
            this.count = 0;
            this.nextSwitchAt = setNextSwitchAt();
            this.takeA = !this.takeA;
        }
        this.count++;
        return this.takeA;
    }

    public String toString() {
        StringWriter sw = new StringWriter();

        try {
            write(sw);
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        return sw.toString();
    }

    public void write(Writer w) throws IOException {
        w.write("--- CrossoverSwitch ---\n");
        w.write("mean = " + this.mean + "\n");
        w.write("var = " + this.var + "\n");
    }

}
