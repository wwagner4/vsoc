package vsoc.nn.feedforward;

import java.io.Serializable;
import java.util.Random;

import vsoc.nn.base.*;
import vsoc.util.IntVector;

/**
 * The netconnector allowes to define the structure of feed forward neuronal nets and
 * parameters for synapses and neurons.
 */

public abstract class AbstractFFNetConnector implements Serializable {

    private static final long serialVersionUID = 0L;

    private IntVector nodesPerLayer;

    private IntVector connProbMatrix;

    private int seed;

    private Random ran;

    private IntVector connMatrix = new IntVector();

    private Weighter wgt;

    private TransferManager trans;

    public AbstractFFNetConnector() {
        this.nodesPerLayer = nodesPerLayer();
        this.connProbMatrix = connProbMatrix();
        this.wgt = weighter();
        this.trans = transferManager();
        this.seed = seed();
        initConnMatrix();
    }

    public abstract IntVector nodesPerLayer();

    public abstract IntVector connProbMatrix();

    public abstract Weighter weighter();

    public abstract TransferManager transferManager();

    public abstract int seed();

    Neuron getNewNeuron() {
        return new Neuron(this.trans);
    }

    Synapse getNewSynapse() {
        return new Synapse(this.wgt);
    }

    void connect(FFNet n) {
        initLayers(n);
        connectNet(n);
    }

    void initConnMatrix() {
        int lfromi;
        IntVector lfrom;
        int lCount = this.nodesPerLayer.size();

        this.ran = new Random(this.seed);               
        this.connMatrix.insertElementAt(new IntVector(), 0);
        for (lfromi = 1; lfromi < lCount; lfromi++) {
            lfrom = new IntVector();
            initCMLayerFrom(lfrom, lfromi);
            this.connMatrix.insertElementAt(lfrom, lfromi);
        }
    }

    void initCMLayerFrom(IntVector lfrom, int lfromi) {
        int nfromi;
        IntVector nfrom;
        for (nfromi = 0; nfromi < this.nodesPerLayer.intAt(lfromi); nfromi++) {
            nfrom = new IntVector();
            initCMNeuronFrom(nfrom, nfromi, lfromi);
            lfrom.insertElementAt(nfrom, nfromi);
        }
    }

    void initCMNeuronFrom(IntVector nfrom, int nfromi, int lfromi) {
        int ltoi;
        IntVector lto;
        for (ltoi = 0; ltoi < lfromi; ltoi++) {
            lto = new IntVector();
            initCMLayerTo(lto, ltoi, nfromi, lfromi);
            nfrom.insertElementAt(lto, ltoi);
        }
    }

    void initCMLayerTo(IntVector lto, int ltoi, int nfromi, int lfromi) {
        int ntoi;
        for (ntoi = 0; ntoi < this.nodesPerLayer.intAt(ltoi); ntoi++) {
            if (hasConnection(ltoi, lfromi)) {
                lto.addElement(ntoi);
            }
        }
    }

    boolean hasConnection(int ltoi, int lfromi) {
        return this.ran.nextDouble() <= prob(ltoi, lfromi);
    }

    double prob(int ltoi, int lfromi) {
        double prob;
        IntVector connProbIntVector;
        connProbIntVector = this.connProbMatrix.intVectorAt(lfromi);
        prob = ((double) connProbIntVector.intAt(ltoi)) / 100.0;
        return prob;
    }

    public void initLayers(FFNet net) {
        Layer l = new Layer(this.nodesPerLayer.intAt(0));
        net.addLayer(l);
        int lCount = this.nodesPerLayer.size();
        for (int i = 1; i < lCount; i++) {
        	NeuronLayer nl = new NeuronLayer();
            for (int j = 0; j < this.nodesPerLayer.intAt(i); j++) {
                nl.addNeuron(getNewNeuron());
            }
            net.addLayer(nl);
        }
    }

    public void connectNet(FFNet net) {
        int size = net.layerCount();
        for (int lfromi = 1; lfromi < size; lfromi++) {
            connectLayer(net, lfromi);
        }
    }

    void connectLayer(FFNet net, int lfromi) {
        Layer lfrom;
        lfrom = net.layerAt(lfromi);
        int size = lfrom.size();
        for (int nfromi = 0; nfromi < size; nfromi++) {
            connectNeuron(net, lfromi, nfromi, lfrom);
        }
    }

    void connectNeuron(FFNet net, int lfromi, int nfromi, Layer lfrom) {
        Neuron nfrom;
        int ltoi;
        nfrom = (Neuron) lfrom.layerNodeAt(nfromi);
        for (ltoi = 0; ltoi < lfromi; ltoi++) {
            connectNeuronToLayer(net, lfromi, nfromi, nfrom, ltoi);
        }
    }

    void connectNeuronToLayer(FFNet net, int lfromi, int nfromi, Neuron nfrom, int ltoi) {
        Layer lto = net.layerAt(ltoi);
        IntVector vlfrom = (IntVector)this.connMatrix.elementAt(lfromi);
        IntVector vnfrom = (IntVector)vlfrom.elementAt(nfromi);
        IntVector vlto = (IntVector)vnfrom.elementAt(ltoi);
        int size = vlto.size();
        for (int i = 0; i < size; i++) {
            Integer indexTo = (Integer) vlto.elementAt(i);
            Synapse syn = getNewSynapse();
            syn.connectLayerNode(lto.layerNodeAt(indexTo.intValue()));
            nfrom.addSynapse(syn);
        }
    }
}
