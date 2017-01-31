package vsoc.nn.base;

import java.util.*;
import java.io.*;

/**
 * Manages a number of transfer functions. 
 * Allows the neurons to get the optimal transfer function for the number of their 
 * synapses.
 */
public class TransferManager implements Serializable {

    private static final long serialVersionUID = 0L;

    private Map values = new HashMap();

    public TransferManager() {
        super();
    }

    public Transfer getTransfer(int nSyn) {
        Integer key = new Integer(nSyn);
        if (this.values.containsKey(key)) {
            return (Transfer) this.values.get(key);
        }
        Transfer t = new Transfer(optimalStretch(nSyn));
        this.values.put(key, t);
        return t;
    }

    public static int optimalStretch(int nSyn) {
        if (nSyn <= 0)
            throw new java.lang.IllegalArgumentException("nSyn:" + nSyn);
        else if (nSyn <= 1)
            return 20;
        else if (nSyn <= 5)
            return linearInterpolation(nSyn, 1, 20, 5, 50);
        else if (nSyn <= 10)
            return linearInterpolation(nSyn, 5, 50, 10, 70);
        else if (nSyn <= 20)
            return linearInterpolation(nSyn, 10, 70, 20, 90);
        else if (nSyn <= 30)
            return linearInterpolation(nSyn, 20, 90, 30, 110);
        else if (nSyn <= 40)
            return linearInterpolation(nSyn, 30, 110, 40, 130);
        else if (nSyn <= 50)
            return linearInterpolation(nSyn, 40, 130, 50, 150);
        else if (nSyn <= 80)
            return linearInterpolation(nSyn, 50, 150, 80, 200);
        else if (nSyn <= 100)
            return linearInterpolation(nSyn, 80, 200, 100, 230);
        else if (nSyn <= 150)
            return linearInterpolation(nSyn, 100, 230, 150, 280);
        else if (nSyn <= 200)
            return linearInterpolation(nSyn, 150, 280, 200, 300);
        else if (nSyn <= 300)
            return linearInterpolation(nSyn, 200, 300, 300, 360);
        else if (nSyn <= 400)
            return linearInterpolation(nSyn, 300, 360, 400, 380);
        else
            return 380;
    }

    private static int linearInterpolation(int x, int x1, int y1, int x2, int y2) {
        float a = x2 * y1 - x1 * y2 + y2 * x - y1 * x;
        float b = x2 - x1;
        return (int) (a / b);
    }
}