package vsoc.nn.feedforward;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import vsoc.nn.base.Layer;
import vsoc.nn.base.Neuron;
import vsoc.nn.base.NeuronLayer;
import vsoc.nn.base.Synapse;
import vsoc.nn.base.TransferManager;
import vsoc.nn.base.Weighter;
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

    private Vector connMatrix = new Vector();

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
        Vector lfrom;
        int lCount = this.nodesPerLayer.size();

        this.ran = new Random(this.seed);
        this.connMatrix.insertElementAt(new Vector(), 0);
        for (lfromi = 1; lfromi < lCount; lfromi++) {
            lfrom = new Vector();
            initCMLayerFrom(lfrom, lfromi);
            this.connMatrix.insertElementAt(lfrom, lfromi);
        }
    }

    void initCMLayerFrom(Vector lfrom, int lfromi) {
        int nfromi;
        Vector nfrom;
        for (nfromi = 0; nfromi < this.nodesPerLayer.intAt(lfromi); nfromi++) {
            nfrom = new Vector();
            initCMNeuronFrom(nfrom, nfromi, lfromi);
            lfrom.insertElementAt(nfrom, nfromi);
        }
    }

    void initCMNeuronFrom(Vector nfrom, int nfromi, int lfromi) {
        int ltoi;
        Vector lto;
        for (ltoi = 0; ltoi < lfromi; ltoi++) {
            lto = new Vector();
            initCMLayerTo(lto, ltoi, nfromi, lfromi);
            nfrom.insertElementAt(lto, ltoi);
        }
    }

    void initCMLayerTo(Vector lto, int ltoi, int nfromi, int lfromi) {
        int ntoi;
        for (ntoi = 0; ntoi < this.nodesPerLayer.intAt(ltoi); ntoi++) {
            if (hasConnection(ltoi, lfromi)) {
                lto.addElement(new Integer(ntoi));
            }
        }
    }

    boolean hasConnection(int ltoi, int lfromi) {
        return this.ran.nextDouble() <= prob(ltoi, lfromi);
    }

    double prob(int ltoi, int lfromi) {
        double prob;
        IntVector connProbIntVector;
        // System.out.println ("++ prob ltoi="+ltoi+" lfromi="+lfromi);
        connProbIntVector = this.connProbMatrix.intVectorAt(lfromi);
        prob = ((double) connProbIntVector.intAt(ltoi)) / 100.0;
        // System.out.println ("prob="+prob);
        return prob;
    }

    public void initLayers(FFNet net) {
        int i, j, lCount;
        Layer l;
        NeuronLayer nl;

        l = new Layer(this.nodesPerLayer.intAt(0));
        net.addLayer(l);
        lCount = this.nodesPerLayer.size();
        for (i = 1; i < lCount; i++) {
            nl = new NeuronLayer();
            for (j = 0; j < this.nodesPerLayer.intAt(i); j++) {
                nl.addNeuron(getNewNeuron());
            }
            net.addLayer(nl);
        }
    }

    public void connectNet(FFNet net) {
        int lfromi, size;
        size = net.layerCount();
        for (lfromi = 1; lfromi < size; lfromi++) {
            connectLayer(net, lfromi);
        }
    }

    void connectLayer(FFNet net, int lfromi) {
        Layer lfrom;
        int nfromi, size;
        lfrom = net.layerAt(lfromi);
        size = lfrom.size();
        for (nfromi = 0; nfromi < size; nfromi++) {
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

    void connectNeuronToLayer(FFNet net, int lfromi, int nfromi, Neuron nfrom,
            int ltoi) {
        Layer lto;
        Vector vlfrom, vnfrom, vlto;
        int i, size;
        Integer indexTo;
        Synapse syn;

        lto = net.layerAt(ltoi);
        vlfrom = (Vector) this.connMatrix.elementAt(lfromi);
        vnfrom = (Vector) vlfrom.elementAt(nfromi);
        vlto = (Vector) vnfrom.elementAt(ltoi);
        size = vlto.size();
        for (i = 0; i < size; i++) {
            indexTo = (Integer) vlto.elementAt(i);
            syn = getNewSynapse();
            syn.connectLayerNode(lto.layerNodeAt(indexTo.intValue()));
            nfrom.addSynapse(syn);
        }
    }
}
