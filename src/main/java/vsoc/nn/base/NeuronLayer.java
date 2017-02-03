package vsoc.nn.base;

import java.util.*;
import java.io.*;

/**
 * A layer of a ANN containing a collection of neurons.
 * 
 * @see Neuron
 */

public class NeuronLayer extends Layer implements Serializable {

    
    private static final long serialVersionUID = 0L;

    public void addNeuron(Neuron neu) {
        addLayerNode(neu);
    }

    public Neuron neuronAt(int i) {
        return (Neuron) layerNodeAt(i);
    }

    void setWeightsRandomOld(RandomWgt rw) {
        int size = size();
        for (int i = 0; i < size; i++) {
            Neuron neu = (Neuron) layerNodeAt(i);
            neu.setWeightsRandom(rw);
        }
    }

    void setWeightsRandom(RandomWgt rw) {
        for (Iterator<Synapse> e = synapses(); e.hasNext();) {
            Synapse syn = e.next();
            syn.setWeightRandom(rw);
        }
    }

    @Override
    public boolean equals(Object o) {
        return equalsInWeights(o) && equalsInValues(o);
    }
    
    @Override
    public int hashCode() {
    	return 0;
    }

    public boolean equalsInWeights(Object o) {
        boolean equals = true;
        NeuronLayer nl;
        if (!(o instanceof NeuronLayer))
            return false;
        nl = (NeuronLayer) o;
        Iterator<? extends LayerNode> neus = nl.layerNodes();
        Iterator<? extends LayerNode> thisNeus = layerNodes();
        while (neus.hasNext() && thisNeus.hasNext()
                && equals) {
            Neuron neu = (Neuron) neus.next();
            Neuron thisNeu = (Neuron) thisNeus.next();
            if (!neu.equalsInWeights(thisNeu))
                equals = false;
        }
        if (equals) {
            if (thisNeus.hasNext() || neus.hasNext())
                return false;
            return true;
        }
        return false;
    }

    public Iterator<Synapse> synapses() {
        return new EnumSynapsesOfNeuronLayer(this);
    }

    class EnumSynapsesOfNeuronLayer implements Iterator<Synapse> {
        private Iterator<LayerNode> en; 
        private Iterator<Synapse> es;
        private Synapse next = null;

        EnumSynapsesOfNeuronLayer(NeuronLayer nl) {
            Neuron neu;
            this.en = nl.layerNodes();
            if (this.en.hasNext()) {
                neu = (Neuron) this.en.next();
                this.es = neu.synapses();
                this.next = nextSyn();
            }
        }

        Synapse nextSyn() {
            Neuron neu;
            if (this.es.hasNext()) {
                return this.es.next();
            } else if (this.en.hasNext()) {
                neu = (Neuron) this.en.next();
                this.es = neu.synapses();
                return next();
            } else
                return null;
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public Synapse next() {
            Synapse o = this.next;
            if (o == null) {
            	throw new NoSuchElementException();
            }
            this.next = nextSyn();
            return o;
        }
    }
}
