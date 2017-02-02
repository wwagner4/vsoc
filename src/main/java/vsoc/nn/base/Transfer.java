package vsoc.nn.base;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;

/**
 * Implements the transfer function for neurons. The values are calculated at
 * initialization time and stored in an array. If a value has to be calculated
 * it is taken from this array.
 */
public class Transfer implements Serializable {

    private static final long serialVersionUID = 0L;

    private Vector<Integer> vals = new Vector<>();

    private int maxIndex;

    private short maxValue;

    private int stretch;

    public Transfer(int stretch) {
        this.stretch = stretch;
        initTransfer(Params.maxValue, stretch);
    }

    void initTransfer(short maxValue, int stretch) {
        int i;
        Integer val;
        double dVal;

        this.maxValue = maxValue;
        initMaxIndex(stretch);
        for (i = 0; i < this.maxIndex; i++) {
            dVal = 1.0 / (1.0 + Math.exp(-i / (double) stretch)) * (maxValue);
            val = new Integer((int) (dVal));
            this.vals.addElement(val);
        }
    }

    void initMaxIndex(int stretch) {
        double y = 1 - (1.0 / (this.maxValue * 2.0));
        double x = -(Math.log(1.0 / y - 1.0) * (double) stretch);
        this.maxIndex = (int) x;
    }

    public int getStretch() {
        return this.stretch;
    }

    public short getValue(int in) {
        Integer val;

        if (in >= 0) {
            if (in >= this.maxIndex) {
                return (short) (this.maxValue - 1);
            }
            val = (Integer) this.vals.elementAt(in);
            return (short) val.intValue();
        }
        if (-in >= this.maxIndex) {
            return (short) 0;
        }
        val = (Integer) this.vals.elementAt(-in);
        return (short) (this.maxValue - (short) val.intValue());

    }

    void writeToStream(PrintWriter str) {
        short result;
        int val, from, to;

        from = (int) (-this.maxIndex * 1.1);
        to = (int) (this.maxIndex * 1.1);

        str.println("val;result");
        for (val = from; val <= to; val++) {
            result = getValue(val);
            str.println(val + ";" + result);
        }
    }
}
