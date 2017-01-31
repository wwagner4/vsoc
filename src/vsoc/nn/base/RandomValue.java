package vsoc.nn.base;

import java.util.*;
import java.io.*;

/**
 * Generates random activation values for neurons. Used for the random
 * initialisation of ANNs.
 */
public class RandomValue extends Random {

    public RandomValue() {
        super();
    }

    public RandomValue(long seed) {
        setSeed(seed);
    }

    public short nextValue() {
        int iran;
        iran = nextInt();
        return (short) (Math.abs(iran) % (Params.maxValue));
    }

    void writeToStream(PrintStream str) {
        int i;
        for (i = 1; i <= 100; i++) {
            str.println(nextValue());
        }
    }

}
