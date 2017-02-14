package vsoc.nn.base;

import java.util.*;
import java.io.*;

/**
 * Is a neuron with no synapses. This means it can be used in the input-layer of
 * a neuronal net.
 */

public class LayerNode implements Serializable {

    private static final long serialVersionUID = 0L;

    short value;

    public LayerNode(short val) {
        setValue(val);
    }

    public LayerNode() {
        setValue((short) 0);
    }

    public void setValue(short value) {
        if (value > Params.maxValue) {
            this.value = Params.maxValue;
        } else if (value < 0) {
            this.value = (short) 0;
        } else {
            this.value = value;
        }
    }

    public void setValueRandom(RandomValue r) {
        setValue(r.nextValue());
    }

    public short getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        if (isCalculated()) {
            return "<C" + this.value + ">";
        }
        return "<U" + this.value + ">";
    }

    String valueToString() {
        return Short.toString(value);
    }

    boolean isConnectedToLayerNode(LayerNode ln) {
        return false;
    }

    boolean isCalculated() {
        return true;
    }

    void resetCalculated() {
        // nothing to be done
    }

    void setCalculated() {
        // nothing to be done
    }

    void calculate() {
        // nothing to be done
    }

    void readObject(java.io.ObjectInputStream stream) {
        // nothing to be done
    }

    void writeObject(java.io.ObjectOutputStream stream) {
        // nothing to be done
    }

    @Override
    public boolean equals(Object o) {
        return equalsInValue(o);
    }
    
    @Override
    public int hashCode() {
    	return 0;
    }

    public boolean equalsInValue(Object o) {
        LayerNode ln;

        if (!(o instanceof LayerNode))
            return false;
        ln = (LayerNode) o;
        if (ln.getValue() == getValue())
            return true;
        return false;
    }

    /**
     * LayerNodes to witch the current layer node is connected. Makes only sense
     * in the subclass Neuron of layernode.
     */

    Iterator<LayerNode> connections() {
        return new ArrayList<LayerNode>().iterator();
    }
}
