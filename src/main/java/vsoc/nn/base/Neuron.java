package vsoc.nn.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * A neuron. Holds an activation level and is connected with other neurons or
 * layer nodes via synaptic connections.
 */

public class Neuron extends LayerNode implements Serializable {

    private static final long serialVersionUID = 0L;

    private TransferManager transferManager = null;

    private Vector<Synapse> syns;

    private boolean isCalculated;

    private Transfer trans = null;

    public Neuron(TransferManager transManager) {
        this.transferManager = transManager;
        this.isCalculated = false;
        this.syns = new Vector<>();
        setValue((short) 0);
    }

    protected Transfer getTransfer() {
        if (this.trans == null) {
            this.trans = this.transferManager.getTransfer(this.syns.size());
        }
        return this.trans;
    }

    void setCalculated() {
        this.isCalculated = true;
    }

    void resetCalculated() {
        this.isCalculated = false;
    }

    boolean isCalculated() {
        return this.isCalculated;
    }

    public void recalculate() {
        resetCalculated();
        calculate();
    }

    void calculate() {
        int i, size, wval, sum;
        Synapse syn;

        if (!isCalculated()) {
            size = synapseCount();
            sum = 0;
            for (i = 0; i < size; i++) {
                syn = synapseAt(i);
                wval = syn.getWeightedCalculatedValue();
                sum = sum + wval;
            }
            setValue(getTransfer().getValue(sum));
            setCalculated();
        }
    }

    public void addSynapse(Synapse syn) {
        this.syns.addElement(syn);
    }

    protected int synapseCount() {
        return this.syns.size();
    }

    public Synapse synapseAt(int i) {
        return (Synapse) this.syns.elementAt(i);
    }

    public void setWeightsRandom(RandomWgt rw) {
        int i, size;
        Synapse syn;

        size = synapseCount();
        for (i = 0; i < size; i++) {
            syn = synapseAt(i);
            syn.setWeightRandom(rw);
        }
    }

    public String toString() {
        String str;
        int size = this.synapseCount();
        Synapse syn;
        int i;

        str = super.toString();
        if (this.trans == null) {
            str += "*** No Transfer ***";
        } else {
            str += "-" + this.trans.getStretch() + "-";
        }
        str += "{";
        for (i = 0; i < size; i++) {
            syn = this.synapseAt(i);
            str = str + syn.toString();
        }
        str = str + "}";
        return str;
    }

    boolean isConnectedToLayerNode(LayerNode ln) {
        int i, size;
        boolean is = false;
        Synapse syn;

        size = synapseCount();
        for (i = 0; i < size; i++) {
            syn = synapseAt(i);
            if (syn.layerNode() == ln) {
                is = true;
                break;
            }
        }
        return is;
    }

    Enumeration<Synapse> synapses() {
        return this.syns.elements();
    }

	@SuppressWarnings("unchecked")
	void readObject(java.io.ObjectInputStream stream) {
        super.readObject(stream);
        try {
            this.syns = (Vector<Synapse>) stream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void writeObject(java.io.ObjectOutputStream stream) {
        super.writeObject(stream);
        try {
            stream.writeObject(this.syns);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equalsInWeights(Object o) {
        Synapse syn, thisSyn;
        Enumeration<Synapse> syns, thisSyns;
        boolean equals = true;
        Neuron neu;

        if (!(o instanceof Neuron))
            return false;
        neu = (Neuron) o;
        syns = neu.synapses();
        thisSyns = synapses();
        while (syns.hasMoreElements() && thisSyns.hasMoreElements() && equals) {
            syn = (Synapse) syns.nextElement();
            thisSyn = (Synapse) thisSyns.nextElement();
            if (!syn.equals(thisSyn))
                equals = false;
        }
        if (equals == true) {
            if (thisSyns.hasMoreElements() || syns.hasMoreElements())
                return false;
            return true;
        }
        return false;
    }

    public boolean equals(Object o) {
        return (equalsInValue(o) && equalsInWeights(o));
    }

    /**
     * LayerNodes to which the current neuron node is connected.
     */

    public Enumeration<LayerNode> connections() {
        Vector<LayerNode> lns = new Vector<>();
        Enumeration<Synapse> enu = synapses();
        Synapse syn;
        while (enu.hasMoreElements()) {
            syn = enu.nextElement();
            lns.addElement(syn.getLayerNode());
        }
        return lns.elements();
    }
}
