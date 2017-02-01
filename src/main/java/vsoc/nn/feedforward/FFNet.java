package vsoc.nn.feedforward;

import java.io.*;
import java.util.*;

import vsoc.genetic.*;
import vsoc.nn.Net;
import vsoc.nn.base.*;

/**
 * Is an artificial neural net. A net contains out of an ordered list of layers.
 * The first layer is called the input-layer and the last layer is the output
 * layer. The layers are lists of layer-nodes. A layer-node is a neuron if it is
 * not in the input layer. Connections between neurons and layer-nodes can be
 * defined via synapses. The structure (its connections) of the net is defined
 * by the net-connector that must be used as an argument to the constructor.
 * Diverse parameters for synapses and neurons can also be defined with the
 * net-Connector. see@ Neuron see@ Layer
 */

public class FFNet implements Net {
    
    private static final long serialVersionUID = 0L;

    protected Vector<Layer> ls = new Vector<>();

    private AbstractFFNetConnector netConnector = null;

    private transient CrossoverSwitch crossoverSwitsh = null;

    public FFNet() {
        super();
    }

    public FFNet(AbstractFFNetConnector nc) {
        this();
        this.connect(nc);
    }

    void connect(AbstractFFNetConnector nc) {
        this.netConnector = nc;
        nc.connect(this);
    }

    public NeuronLayer getOutputLayer() {
        return (NeuronLayer) (layerAt(layerCount() - 1));
    }

    public Layer getInputLayer() {
        return (layerAt(0));
    }
    
    public void setInputValue(int index, short val)   {
        getInputLayer().setValueAt(index, val);
    }

    public short getOutputValue(int index)   {
        return getOutputLayer().getValueAt(index);
    }

    public Net newChild(Net otherParent, double mutationRate) {
        Mutator mut = new Mutator((int) (mutationRate * 1000000)); 
        FFNet otherNet, childNet;
        otherNet = (FFNet) otherParent;
        childNet = new FFNet(this.netConnector);
        childNet.setWeightsCrossover(this, otherNet, getCrossoverSwitsh(), mut);
        return childNet;
    }

    void setWeightsCrossover(FFNet netA, FFNet netB, CrossoverSwitch cs, Mutator mut) {
    	// TODO Remove Enumerations
        Enumeration<Synapse> enumA, enumB, enumChild;
        Synapse synA, synB, synChild;
        RandomWgt rw = new RandomWgt();
        enumA = netA.synapses();
        enumB = netB.synapses();
        enumChild = this.synapses();
        while (enumA.hasMoreElements()) {
            synA = enumA.nextElement();
            synB = enumB.nextElement();
            synChild = enumChild.nextElement();
            if (mut.isMutation())
                synChild.setWeightRandom(rw);
            else if (cs.takeA())
                synChild.setWeight(synA.getWeight());
            else
                synChild.setWeight(synB.getWeight());
        }
    }

    public void setInputLayerValuesRandom(RandomValue rv) {
        Layer il = getInputLayer();
        il.setValuesRandom(rv);
    }

    public void setWeightsRandom(long seed) {
        RandomWgt rw = new RandomWgt(seed);
        setWeightsRandom(rw);
    }

    private void setWeightsRandom(RandomWgt rw) {
        Synapse syn;
        for (Enumeration<Synapse> e = synapses(); e.hasMoreElements();) {
            syn = (Synapse) e.nextElement();
            syn.setWeightRandom(rw);
        }
    }

    public void setWeightsCrossover(FFNet father, FFNet mother, Random r) {
        Synapse syn, synf, synm;
        Enumeration<Synapse> e, ef, em;
        int count, offset, interval;
        boolean fromFather;
        count = 0;
        interval = 20;
        if (Math.abs(r.nextInt() % 1000) > 500)
            fromFather = true;
        else
            fromFather = false;
        offset = Math.abs(r.nextInt() % 20);
        e = synapses();
        ef = father.synapses();
        em = mother.synapses();
        while (e.hasMoreElements()) {
            syn = e.nextElement();
            synf = ef.nextElement();
            synm = em.nextElement();
            if (fromFather) {
                syn.setWeight(synf.getWeight());
                if ((count + offset) % interval == 0)
                    fromFather = false;
            } else {
                syn.setWeight(synm.getWeight());
                if ((count + offset) % interval == 0)
                    fromFather = true;
            }
            count++;
        }
    }

    void addLayer(Layer l) {
        this.ls.addElement(l);
    }

    public Layer layerAt(int i) {
        return (Layer) this.ls.elementAt(i);
    }

    int layerCount() {
        return this.ls.size();
    }

    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            toStream(sw);
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        return sw.toString();
    }

    void toStream(Writer w) throws IOException {
        int i, size;
        size = this.layerCount();
        w.write("--- Net BEGIN ---\n");
        for (i = 0; i < size; i++) {
            Layer l;
            l = this.layerAt(i);
            l.toStream(w);
            w.write("\n");
        }
        w.write("--- Net END ---\n");
    }

    Vector<Integer> compareWeights(FFNet net1) {
        Synapse syn, syn1;
        Enumeration<Synapse> e, e1;
        short w, w1;
        Vector<Integer> result = new Vector<>();
        e = synapses();
        e1 = net1.synapses();
        while (e.hasMoreElements()) {
            syn = e.nextElement();
            syn1 = e1.nextElement();
            w = syn.getWeight();
            w1 = syn1.getWeight();
            if (w == w1)
                result.addElement(new Integer(0));
            else
                result.addElement(new Integer(1));
        }
        return result;
    }

    public void resetCalculated() {
        for (int i = 1; i < layerCount(); i++) {
            layerAt(i).resetCalculated();
        }
    }

    public void calculate() {
        resetCalculated();
        getOutputLayer().calculate();
    }

    public boolean equalsInValues(Object o) {
        Layer l, thisL;
        boolean equals = true;
        FFNet net;
        if (!(o instanceof Net))
            return false;
        net = (FFNet) o;
        Enumeration<Layer> ls = net.layers();
        Enumeration<Layer> thisLs = layers();
        while (ls.hasMoreElements() && thisLs.hasMoreElements() && equals) {
            l = ls.nextElement();
            thisL = thisLs.nextElement();
            if (!l.equalsInValues(thisL))
                equals = false;
        }
        if (equals) {
            if (thisLs.hasMoreElements() || ls.hasMoreElements())
                return false;
            return true;
        }
        return false;
    }

    public boolean equalsInStructure(Object o) {
        Enumeration<Layer> lsa, lsb;
        Enumeration<LayerNode> lnsa, lnsb;
        Enumeration<Synapse> synsa, synsb;
        FFNet net;
        LayerNode lna, lnb;
        Layer la, lb;
        Synapse syna, synb;
        if (!(o instanceof Net))
            return false;
        net = (FFNet) o;
        lsa = layers();
        lsb = net.layers();
        while (lsa.hasMoreElements() && lsb.hasMoreElements()) {
            la = lsa.nextElement();
            lb = lsb.nextElement();
            lnsa = la.layerNodes();
            lnsb = lb.layerNodes();
            while (lnsa.hasMoreElements() && lnsb.hasMoreElements()) {
                lna = lnsa.nextElement();
                lnb = lnsb.nextElement();
                synsa = synapses();
                synsb = net.synapses();
                while (synsa.hasMoreElements() && synsb.hasMoreElements()) {
                    syna = (Synapse) synsa.nextElement();
                    synb = (Synapse) synsb.nextElement();
                    if ((syna.layerNode() == lna)
                            && (synb.layerNode() != lnb))
                        return false;
                    if ((synb.layerNode() == lnb)
                            && (syna.layerNode() != lna))
                        return false;
                }
                if (synsa.hasMoreElements() || synsb.hasMoreElements())
                    return false;
            }
            if (lnsa.hasMoreElements() || lnsb.hasMoreElements())
                return false;
        }
        if (lsa.hasMoreElements() || lsb.hasMoreElements())
            return false;
        return true;
    }

    public boolean equalsInWeights(Object o) {
        NeuronLayer l, thisL;
        Enumeration<Layer> ls, thisLs;
        boolean equals = true;
        FFNet net;
        if (!(o instanceof Net))
            return false;
        net = (FFNet) o;
        ls = net.neuronLayers();
        thisLs = neuronLayers();
        while (ls.hasMoreElements() && thisLs.hasMoreElements() && equals) {
            l = (NeuronLayer) ls.nextElement();
            thisL = (NeuronLayer) thisLs.nextElement();
            if (!l.equalsInWeights(thisL))
                equals = false;
        }
        if (equals == true) {
            if (thisLs.hasMoreElements() || ls.hasMoreElements())
                return false;
            return true;
        }
        return false;
    }

	public Enumeration<Synapse> synapses() {
        return new EnumSynapsesOfNet(this);
    }

    Enumeration<Layer> layers() {
        return new EnumLayersOfNet(this);
    }

    protected Enumeration<Layer> neuronLayers() {
        return new EnumNeuronLayersOfNet(this);
    }

    public double distance(Net net) {
        FFNet ffnet = (FFNet) net;
        int synCount = 0;
        int distSum = 0;
        Enumeration<Synapse> enum1 = this.synapses();
        Enumeration<Synapse> enum2 = ffnet.synapses();
        while (enum1.hasMoreElements()) {
            Synapse syn1 = enum1.nextElement();
            Synapse syn2 = enum2.nextElement();
            distSum += Math.abs(syn1.getWeight() - syn2.getWeight());
            synCount++;
        }
        return (double) distSum / synCount;
    }

    public void setCrossoverSwitsh(CrossoverSwitch crossoverSwitsh) {
        this.crossoverSwitsh = crossoverSwitsh;
    }

    private CrossoverSwitch getCrossoverSwitsh() {
        if (this.crossoverSwitsh == null) {
            this.crossoverSwitsh = new CrossoverSwitch(50, 20);
        }
        return this.crossoverSwitsh;
    }
    
    class EnumSynapsesOfNet implements Enumeration<Synapse> {
        Enumeration<Synapse> enl;

        int index, size;

        Vector<Layer> enumls;

        Synapse nextSyn = null;

        EnumSynapsesOfNet(FFNet net) {
            this.size = net.layerCount();
            if (this.size >= 2) {
                this.index = 1;
                this.enumls = net.ls;
                NeuronLayer nl = (NeuronLayer) this.enumls.elementAt(this.index);
                this.index++;
                this.enl = nl.synapses();
                this.nextSyn = nextSyn();
            }
        }

        private Synapse nextSyn() {
            NeuronLayer nl;
            Synapse re = null;
            if (this.enl.hasMoreElements()) {
                re = this.enl.nextElement();
            } else if (this.index < this.size) {
                nl = (NeuronLayer) this.enumls.elementAt(this.index);
                this.index++;
                this.enl = nl.synapses();
                re = nextSyn();
            }
            return re;
        }

        public boolean hasMoreElements() {
            return this.nextSyn != null;
        }

        public Synapse nextElement() {
        	Synapse o = this.nextSyn;
            this.nextSyn = nextSyn();
            return o;
        }
    }

    class EnumLayersOfNet implements Enumeration<Layer> {
        
    	private int index, size;

        private Vector<Layer> els;

        public EnumLayersOfNet(FFNet net) {
            this.size = net.layerCount();
            this.index = 0;
            this.els = net.ls;
        }

        public boolean hasMoreElements() {
            return this.index < this.size;
        }

        public Layer nextElement() {
        	Layer o = this.els.elementAt(this.index);
            this.index++;
            return o;
        }
    }

    // TODO Can be replaced by net.ls
    class EnumNeuronLayersOfNet implements Enumeration<Layer> {
    	
        private int index, size;

        private Vector<Layer> els1;

        EnumNeuronLayersOfNet(FFNet net) {
            this.size = net.layerCount();
            this.index = 1;
            this.els1 = net.ls;
        }

        public boolean hasMoreElements() {
            return this.index < this.size;
        }

        public Layer nextElement() {
        	Layer o = this.els1.elementAt(this.index);
            this.index++;
            return o;
        }
    }

    public void reset() {
        // Nothing to be done by a FFNet.
    }

}
