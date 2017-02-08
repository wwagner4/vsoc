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

    protected List<Layer> ls = new ArrayList<>();

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
        return layerAt(0);
    }
    
    public void setInputValue(int index, short val)   {
        getInputLayer().setValueAt(index, val);
    }

    public short getOutputValue(int index)   {
        return getOutputLayer().getValueAt(index);
    }

    public Net newChild(Net otherParent, double mutationRate) {
        Mutator mut = new Mutator((int) (mutationRate * 1000000)); 
        FFNet otherNet = (FFNet) otherParent;
        FFNet childNet = new FFNet(this.netConnector);
        childNet.setWeightsCrossover(this, otherNet, getCrossoverSwitsh(), mut);
        return childNet;
    }

    void setWeightsCrossover(FFNet netA, FFNet netB, CrossoverSwitch cs, Mutator mut) {
        RandomWgt rw = new RandomWgt();
        Iterator<Synapse> enumA = netA.synapses();
        Iterator<Synapse> enumB = netB.synapses();
        Iterator<Synapse> enumChild = this.synapses();
        while (enumA.hasNext()) {
        	Synapse synA = enumA.next();
        	Synapse synB = enumB.next();
        	Synapse synChild = enumChild.next();
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

    public void setParametersRandom(long seed) {
        RandomWgt rw = new RandomWgt(seed);
        setWeightsRandom(rw);
    }

    private void setWeightsRandom(RandomWgt rw) {
        Synapse syn;
        for (Iterator<Synapse> e = synapses(); e.hasNext();) {
            syn = (Synapse) e.next();
            syn.setWeightRandom(rw);
        }
    }

    public void setWeightsCrossover(FFNet father, FFNet mother, Random r) {
        int count = 0;
        int interval = 20;
        boolean fromFather;
        if (Math.abs(r.nextInt() % 1000) > 500)
            fromFather = true;
        else
            fromFather = false;
        int offset = Math.abs(r.nextInt() % 20);
        Iterator<Synapse> e = synapses();
        Iterator<Synapse> ef = father.synapses();
        Iterator<Synapse> em = mother.synapses();
        while (e.hasNext()) {
        	Synapse syn = e.next();
        	Synapse synf = ef.next();
        	Synapse synm = em.next();
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
        this.ls.add(l);
    }

    public Layer layerAt(int i) {
        return (Layer) this.ls.get(i);
    }

    int layerCount() {
        return this.ls.size();
    }

    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            toStream(sw);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return sw.toString();
    }

    void toStream(Writer w) throws IOException {
        int size = this.layerCount();
        w.write("--- Net BEGIN ---\n");
        for (int i = 0; i < size; i++) {
            Layer l;
            l = this.layerAt(i);
            l.toStream(w);
            w.write("\n");
        }
        w.write("--- Net END ---\n");
    }

    List<Integer> compareWeights(FFNet net1) {
        List<Integer> result = new ArrayList<>();
        Iterator<Synapse> e = synapses();
        Iterator<Synapse> e1 = net1.synapses();
        while (e.hasNext()) {
            Synapse syn = e.next();
            Synapse syn1 = e1.next();
            short w = syn.getWeight();
            short w1 = syn1.getWeight();
            if (w == w1)
                result.add(0);
            else
                result.add(1);
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
        boolean equals = true;
        if (!(o instanceof Net))
            return false;
        FFNet net = (FFNet) o;
        Iterator<Layer> ls1 = net.layers();
        Iterator<Layer> thisLs = layers();
        while (ls1.hasNext() && thisLs.hasNext() && equals) {
            Layer l = ls1.next();
            Layer thisL = thisLs.next();
            if (!l.equalsInValues(thisL))
                equals = false;
        }
        if (equals) {
            if (thisLs.hasNext() || ls1.hasNext())
                return false;
            return true;
        }
        return false;
    }

    public boolean equalsInStructure(Object o) {
        if (!(o instanceof Net))
            return false;
        FFNet net = (FFNet) o;
        Iterator<Layer> lsa = layers();
        Iterator<Layer> lsb = net.layers();
        while (lsa.hasNext() && lsb.hasNext()) {
        	Layer la = lsa.next();
        	Layer lb = lsb.next();
            Iterator<LayerNode> lnsa = la.layerNodes();
            Iterator<LayerNode> lnsb = lb.layerNodes();
            while (lnsa.hasNext() && lnsb.hasNext()) {
            	LayerNode lna = lnsa.next();
            	LayerNode lnb = lnsb.next();
                Iterator<Synapse> synsa = synapses();
                Iterator<Synapse>  synsb = net.synapses();
                while (synsa.hasNext() && synsb.hasNext()) {
                	Synapse syna = synsa.next();
                	Synapse  synb = synsb.next();
                    if ((syna.layerNode() == lna)
                            && (synb.layerNode() != lnb))
                        return false;
                    if ((synb.layerNode() == lnb)
                            && (syna.layerNode() != lna))
                        return false;
                }
                if (synsa.hasNext() || synsb.hasNext())
                    return false;
            }
            if (lnsa.hasNext() || lnsb.hasNext())
                return false;
        }
        if (lsa.hasNext() || lsb.hasNext())
            return false;
        return true;
    }

    public boolean equalsInWeights(Object o) {
        boolean equals = true;
        FFNet net;
        if (!(o instanceof Net))
            return false;
        net = (FFNet) o;
        Iterator<Layer> ls1 = net.neuronLayers();
        Iterator<Layer> thisLs = neuronLayers();
        while (ls1.hasNext() && thisLs.hasNext() && equals) {
        	NeuronLayer l = (NeuronLayer) ls1.next();
        	NeuronLayer thisL = (NeuronLayer) thisLs.next();
            if (!l.equalsInWeights(thisL))
                equals = false;
        }
        if (equals) {
            if (thisLs.hasNext() || ls1.hasNext())
                return false;
            return true;
        }
        return false;
    }

	public Iterator<Synapse> synapses() {
        return new EnumSynapsesOfNet(this);
    }

    Iterator<Layer> layers() {
        return ls.iterator();
    }

    protected Iterator<Layer> neuronLayers() {
        return new NeuronLayersOfNet(this);
    }

    public double distance(Net net) {
        FFNet ffnet = (FFNet) net;
        int synCount = 0;
        int distSum = 0;
        Iterator<Synapse> enum1 = this.synapses();
        Iterator<Synapse> enum2 = ffnet.synapses();
        while (enum1.hasNext()) {
            Synapse syn1 = enum1.next();
            Synapse syn2 = enum2.next();
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
    
    class EnumSynapsesOfNet implements Iterator<Synapse> {
        Iterator<Synapse> enl;

        int size;
        int index;
        List<Layer> enumls;

        Synapse nextSyn = null;

        EnumSynapsesOfNet(FFNet net) {
            this.size = net.layerCount();
            if (this.size >= 2) {
                this.index = 1;
                this.enumls = net.ls;
                NeuronLayer nl = (NeuronLayer) this.enumls.get(this.index);
                this.index++;
                this.enl = nl.synapses();
                this.nextSyn = nextSyn();
            }
        }

        private Synapse nextSyn() {
            NeuronLayer nl;
            Synapse re = null;
            if (this.enl.hasNext()) {
                re = this.enl.next();
            } else if (this.index < this.size) {
                nl = (NeuronLayer) this.enumls.get(this.index);
                this.index++;
                this.enl = nl.synapses();
                re = nextSyn();
            }
            return re;
        }

        @Override
        public boolean hasNext() {
            return this.nextSyn != null;
        }

        @Override
        public Synapse next() {
        	Synapse o = this.nextSyn;
        	if (o == null) {
        		throw new NoSuchElementException();
        	}
            this.nextSyn = nextSyn();
            return o;
        }
    }

    // Iterator over all Neuron layers of a NN
    class NeuronLayersOfNet implements Iterator<Layer> {
    	
        private int index;
        private int size;
        private List<Layer> layers;

        NeuronLayersOfNet(FFNet net) {
            this.size = net.layerCount();
            this.index = 1;
            this.layers = net.ls;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.size;
        }

        @Override
        public Layer next() {
        	Layer layer = this.layers.get(this.index);
        	if (layer == null) {
        		throw new NoSuchElementException();
        	}
            this.index++;
            return layer;
        }
    }

    public void reset() {
        // Nothing to be done by a FFNet.
    }

}
