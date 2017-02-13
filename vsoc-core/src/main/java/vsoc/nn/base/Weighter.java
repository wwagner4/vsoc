package vsoc.nn.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The weighter is used to preprocess actiavtion values of preceeding neurons.
 * Weighting is a multiplication of the incoming value with a value between min-
 * and max factor. What factor is taken depends on the weight of the synapse.
 * The weight of a synapse is a value out of the intervall [0 .. maxw].
 * <p>
 * <code>
 * <dd>f = maxf + ((maxf - minf) * (w / maxw))
 * <dd>f ... Factor
 * <dd>maxf ... maxFactor
 * <dd>minf ... minFactor
 * <dd>w ... weight
 * <dd>maxw ... maxWeight
 * <code/>
 * <p>The weighter is implemented as an array of values where result values can be calculated
 * by accessing these values. This should speed up the calculation of neuronal nets.
 */

public class Weighter implements Serializable {

    private static final long serialVersionUID = 0L;

    private List<List<Number>> vals = new ArrayList<>();

    public Weighter() {
        super();
    }

    public Weighter(float minFactor, float maxFactor) {
        initVals(Params.maxValue, Params.maxWgt, minFactor, maxFactor);
    }

    public Weighter(double minFactor, double maxFactor) {
        initVals(Params.maxValue, Params.maxWgt, (float) minFactor,
                (float) maxFactor);
    }

    private void initVals(short maxValue, short maxWeight, float minFactor,
            float maxFactor) {
        short val;
        short wgt;
        float step;
        List<Number> wgts;

        step = (maxFactor - minFactor) / maxWeight;
        for (val = 0; val < maxValue; val++) {
            wgts = new ArrayList<>();
            for (wgt = 0; wgt < maxWeight; wgt++) {
                wgts.add(result(val, wgt, minFactor, step));
            }
            this.vals.add(wgts);
        }
    }

    private Integer result(short val, short wgt, float minFactor, float step) {
        return (int) (val * (minFactor + wgt * step));
    }

    public int getWeightedValue(short val, short wgt) {
        if (val > 0) {
            if (wgt > 0) {
                return getWeightedValueForPositives(val, wgt);
            }
            return -getWeightedValueForPositives(val, (short) (-wgt));
        }
        if (wgt > 0) {
            return -getWeightedValueForPositives((short) (-val), wgt);
        }
        return getWeightedValueForPositives((short) (-val), (short) -wgt);
    }

    private int getWeightedValueForPositives(short val, short wgt) {
        List<Number> wgts = this.vals.get(val);
        Number i = wgts.get(wgt);
        return i.intValue();
    }

    public short maxValue() {
        return (short) this.vals.size();
    }

    public short maxWeight() {
        List<Number> wgts = this.vals.get(0);
        return (short) wgts.size();
    }
}
