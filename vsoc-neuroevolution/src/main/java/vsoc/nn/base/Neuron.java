package vsoc.nn.base;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A neuron. Holds an activation level and is connected with other neurons or
 * layer nodes via synaptic connections.
 */

public class Neuron extends LayerNode implements Serializable {

    private static final long serialVersionUID = 0L;

    private TransferManager transferManager = null;

    private List<Synapse> syns;

    private boolean isCalculated;

    private Transfer trans = null;

    public Neuron(TransferManager transManager) {
        this.transferManager = transManager;
        this.isCalculated = false;
        this.syns = new ArrayList<>();
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
        if (!isCalculated()) {
            int size = synapseCount();
            int sum = 0;
            for (int i = 0; i < size; i++) {
                Synapse syn = synapseAt(i);
                int wval = syn.getWeightedCalculatedValue();
                sum = sum + wval;
            }
            setValue(getTransfer().getValue(sum));
            setCalculated();
        }
    }

    public void addSynapse(Synapse syn) {
        this.syns.add(syn);
    }

    protected int synapseCount() {
        return this.syns.size();
    }

    public Synapse synapseAt(int i) {
        return (Synapse) this.syns.get(i);
    }

    public void setWeightsRandom(RandomWgt rw) {
        int size = synapseCount();
        for (int i = 0; i < size; i++) {
            Synapse syn = synapseAt(i);
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
        boolean is = false;
        int size = synapseCount();
        for (int i = 0; i < size; i++) {
            Synapse syn = synapseAt(i);
            if (syn.layerNode() == ln) {
                is = true;
                break;
            }
        }
        return is;
    }

    Iterator<Synapse> synapses() {
        return this.syns.iterator();
    }

	@SuppressWarnings("unchecked")
	void readObject(java.io.ObjectInputStream stream) {
        super.readObject(stream);
        try {
            this.syns = (List<Synapse>) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Error reading object. " + e.getMessage(), e);
        } 
    }

	@Override
    void writeObject(java.io.ObjectOutputStream stream) {
        super.writeObject(stream);
        try {
            stream.writeObject(this.syns);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean equalsInWeights(Object o) {
        boolean equals = true;
        if (!(o instanceof Neuron))
            return false;
        Neuron neu = (Neuron) o;
        Iterator<Synapse> syns1 = neu.synapses();
        Iterator<Synapse> thisSyns = synapses();
        while (syns1.hasNext() && thisSyns.hasNext() && equals) {
        	Synapse syn = (Synapse) syns1.next();
        	Synapse thisSyn = (Synapse) thisSyns.next();
            if (!syn.equals(thisSyn))
                equals = false;
        }
        if (equals) {
            if (thisSyns.hasNext() || syns1.hasNext())
                return false;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return equalsInValue(o) && equalsInWeights(o);
    }
    
    @Override
    public int hashCode() {
    	return 0;
    }

    /**
     * LayerNodes to which the current neuron node is connected.
     */

    public Iterator<LayerNode> connections() {
        List<LayerNode> lns = new ArrayList<>();
        Iterator<Synapse> enu = synapses();
        Synapse syn;
        while (enu.hasNext()) {
            syn = enu.next();
            lns.add(syn.getLayerNode());
        }
        return lns.iterator();
    }
}
