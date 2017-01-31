package vsoc.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import vsoc.VsocInvalidConfigurationException;

public class RandomIndexSelector {

    private Set ints = new HashSet();

    private Iterator iter;

    private static Random ran = new Random();

    private int baseIndex;

    public RandomIndexSelector(int from, int to, int count) throws VsocInvalidConfigurationException {
        if (to < from)
            throw new Error("to '" + to + "' must always be bigger than from '"
                    + from + "' in RandomIndexSelector");
        if (count > (to - from))
            throw new VsocInvalidConfigurationException (
                    "count must always be bigger than (to-from) in RandomIndexSelector");
        this.baseIndex = from;
        initSet(to - from + 1, count);
        this.iter = this.ints.iterator();
    }

    public boolean hasNext() {
        return this.iter.hasNext();
    }

    public int next() {
        Integer val = (Integer) this.iter.next();
        return val.intValue();
    }

    private void initSet(int totalSize, int subsetSize) {
        while (this.ints.size() < subsetSize) {
            this.ints.add(randomInteger(totalSize));
        }
    }

    private Integer randomInteger(int totalSize) {
        return new Integer(ran.nextInt(totalSize) + this.baseIndex);
    }
}
