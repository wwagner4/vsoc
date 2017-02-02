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
        int i, size;
        Neuron neu;
        size = size();
        for (i = 0; i < size; i++) {
            neu = (Neuron) layerNodeAt(i);
            neu.setWeightsRandom(rw);
        }
    }

    void setWeightsRandom(RandomWgt rw) {
        Enumeration<Synapse> e;
        Synapse syn;
        for (e = synapses(); e.hasMoreElements();) {
            syn = e.nextElement();
            syn.setWeightRandom(rw);
        }
    }

    public boolean equals(Object o) {
        return equalsInWeights(o) && equalsInValues(o);
    }

    public boolean equalsInWeights(Object o) {
        Neuron neu, thisNeu;
        Enumeration<? extends LayerNode> neus; 
        Enumeration<? extends LayerNode> thisNeus;
        boolean equals = true;
        NeuronLayer nl;
        if (!(o instanceof NeuronLayer))
            return false;
        nl = (NeuronLayer) o;
        neus = nl.layerNodes();
        thisNeus = layerNodes();
        while (neus.hasMoreElements() && thisNeus.hasMoreElements()
                && equals) {
            neu = (Neuron) neus.nextElement();
            thisNeu = (Neuron) thisNeus.nextElement();
            if (!neu.equalsInWeights(thisNeu))
                equals = false;
        }
        if (equals == true) {
            if (thisNeus.hasMoreElements() || neus.hasMoreElements())
                return false;
            return true;
        }
        return false;
    }

    public Enumeration<Synapse> synapses() {
        return new EnumSynapsesOfNeuronLayer(this);
    }

    class EnumSynapsesOfNeuronLayer implements Enumeration<Synapse> {
        Enumeration<LayerNode> en; 
        Enumeration<Synapse> es;

        Synapse nextSyn = null;

        EnumSynapsesOfNeuronLayer(NeuronLayer nl) {
            Neuron neu;
            this.en = nl.layerNodes();
            if (this.en.hasMoreElements()) {
                neu = (Neuron) this.en.nextElement();
                this.es = neu.synapses();
                this.nextSyn = nextSyn();
            }
        }

        Synapse nextSyn() {
            Neuron neu;
            if (this.es.hasMoreElements()) {
                return this.es.nextElement();
            } else if (this.en.hasMoreElements()) {
                neu = (Neuron) this.en.nextElement();
                this.es = neu.synapses();
                return nextElement();
            } else
                return null;
        }

        public boolean hasMoreElements() {
            return this.nextSyn != null;
        }

        public Synapse nextElement() {
            Synapse o;
            o = this.nextSyn;
            this.nextSyn = nextSyn();
            return o;
        }
    }
}
