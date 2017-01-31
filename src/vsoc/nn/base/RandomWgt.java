package vsoc.nn.base;

import java.util.*;
import java.io.*;

/**
 * Generates random weights for synapses. Used for the random initialisation of
 * ANNs.
 */
public class RandomWgt extends Random {

    private static long instCount = 0;

    public RandomWgt() {
        super(System.currentTimeMillis() + instCount++);
    }

    public RandomWgt(long seed) {
        super(seed);
    }

    public short nextValue() {
        int iran;
        iran = nextInt();
        return (short) (Math.abs(iran) % (Params.maxWgt * 2 - 1)
                - Params.maxWgt + 1);
    }

    void writeToStream(PrintStream str) {
        int i;
        for (i = 1; i <= 100; i++) {
            str.println(nextValue());
        }
    }
}
