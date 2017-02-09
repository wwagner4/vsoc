package vsoc.nn.feedforward;import java.io.*;import java.util.Iterator;import java.util.Random;import junit.framework.Test;import junit.framework.TestCase;import junit.framework.TestSuite;import vsoc.*;import vsoc.genetic.CrossoverSwitch;import vsoc.nn.base.Layer;import vsoc.nn.base.LayerNode;import vsoc.nn.base.Neuron;import vsoc.nn.base.NeuronLayer;import vsoc.nn.base.RandomValue;import vsoc.nn.base.RandomWgt;import vsoc.nn.base.Synapse;import vsoc.nn.base.TransferManager;import vsoc.nn.base.Weighter;import vsoc.nn.feedforward.AbstractFFNetConnector;import vsoc.nn.feedforward.FFNet;/** * Tests */public class FFNetTest extends TestCase {    static AbstractFFNetConnector nc = new TestNetConnector();    static Weighter wgt = new Weighter((float) 0.1, (float) 1.5);    static TransferManager trans = new TransferManager();    LayerNode ln1, ln2, ln3;    Neuron neu1, neu2, neu3;    NeuronLayer nl1, nl2, nl3;    FFNet n1, n2, n3, n4;    RandomValue rv;    RandomWgt rw;    public FFNetTest(String s) {        super(s);    }    public static Test suite() {        TestSuite s = new TestSuite();        s.addTest(new FFNetTest("testCrossoverA"));        s.addTest(new FFNetTest("testEqualsLayerNode"));        s.addTest(new FFNetTest("testEqualsNeuron"));        s.addTest(new FFNetTest("testEqualsNeuronLayer"));        s.addTest(new FFNetTest("testEqualsNet"));        s.addTest(new FFNetTest("testCalculate"));        s.addTest(new FFNetTest("testSerialize00"));        s.addTest(new FFNetTest("testSerialize01"));        s.addTest(new FFNetTest("testSerialize02"));        s.addTest(new FFNetTest("testSerialize03"));        s.addTest(new FFNetTest("testSerialize04"));        s.addTest(new FFNetTest("testSerialize05"));        // s.addTest (new NetTest ("testValues"));        return s;    }    protected void setUp() {        this.rv = new RandomValue(293847);        this.rw = new RandomWgt(23847);        this.ln1 = new LayerNode((short) 2);        this.ln2 = new LayerNode((short) 2);        this.ln3 = new LayerNode((short) 4);        this.neu1 = new Neuron(trans);        this.neu1.setValue((short) 3);        generateSynapsesForNeuron(this.neu1);        this.neu2 = new Neuron(trans);        this.neu2.setValue((short) 3);        generateSynapsesForNeuron(this.neu2);        this.neu3 = new Neuron(trans);        this.neu3.setValue((short) 3);        generateSynapsesForNeuron(this.neu3);        this.nl1 = new NeuronLayer();        this.nl1.addNeuron(new Neuron(trans));        this.nl1.addNeuron(new Neuron(trans));        this.nl1.addNeuron(new Neuron(trans));        this.nl1.addNeuron(new Neuron(trans));        this.nl1.addNeuron(new Neuron(trans));        generateSynapsesForNeuronLayer(this.nl1);        this.nl2 = new NeuronLayer();        this.nl2.addNeuron(new Neuron(trans));        this.nl2.addNeuron(new Neuron(trans));        this.nl2.addNeuron(new Neuron(trans));        this.nl2.addNeuron(new Neuron(trans));        this.nl2.addNeuron(new Neuron(trans));        generateSynapsesForNeuronLayer(this.nl2);        this.nl3 = new NeuronLayer();        this.nl3.addNeuron(new Neuron(trans));        this.nl3.addNeuron(new Neuron(trans));        this.nl3.addNeuron(new Neuron(trans));        this.nl3.addNeuron(new Neuron(trans));        this.nl3.addNeuron(new Neuron(trans));        generateSynapsesForNeuronLayer(this.nl3);        nc = new TestNetConnector();        this.n1 = new FFNet(nc);        this.n1.setParametersRandom(-1);        this.n2 = new FFNet(nc);        this.n2.setParametersRandom(-1);    }    void generateSynapsesForNeuron(Neuron neu) {        Synapse syn;        Weighter w = new Weighter((float) 0.1, (float) 1.5);        syn = new Synapse(w);        syn.setWeight((short) -1);        neu.addSynapse(syn);        syn = new Synapse(w);        syn.setWeight((short) 3);        neu.addSynapse(syn);        syn = new Synapse(w);        syn.setWeight((short) 1);        neu.addSynapse(syn);        syn = new Synapse(w);        syn.setWeight((short) 4);        neu.addSynapse(syn);    }    void generateSynapsesForNeuronLayer(NeuronLayer nl) {        Iterator<LayerNode> neus;        Neuron neu;        neus = nl.layerNodes();        while (neus.hasNext()) {            neu = (Neuron) neus.next();            generateSynapsesForNeuron(neu);        }    }    /**     * Writes an object to a file and reads it afterwards. Can be used to verify     * if a specific object is serializable. The name of this file is always     * Serializable.object.     *      * @param sIn     *            The object.     * @return The object that was read from the file.     * @exception ClassNotFoundException     *                If the correponding class cannot be found at read time.     */    public Serializable writeAndRead(Serializable sIn) throws IOException,            ClassNotFoundException {        FileOutputStream fos;        FileInputStream fis;        ObjectOutputStream oos;        ObjectInputStream pi;        File file = TestUtil.tmp("Serializable.object");        Serializable sOut;        fos = new FileOutputStream(file);        oos = new ObjectOutputStream(fos);        oos.writeObject(sIn);        oos.flush();        fos.close();        fis = new FileInputStream(file);        pi = new ObjectInputStream(fis);        sOut = (Serializable) pi.readObject();        fis.close();        return sOut;    }    public void testCrossoverA() throws Exception {        Iterator<Synapse> enum1, enum2, enum3;        Synapse s1, s2, s3;        int from1Count, from2Count, fromNoneCount;        this.n1 = new FFNet(nc);        this.n1.setCrossoverSwitsh(new CrossoverSwitch(5, 3));        this.n2 = new FFNet(nc);        Random ran = new Random();        this.n1.setParametersRandom(ran.nextLong());        this.n2.setParametersRandom(ran.nextLong());        this.n3 = (FFNet) this.n1.newChild(this.n2, 0.3);        enum1 = this.n1.synapses();        enum2 = this.n2.synapses();        enum3 = this.n3.synapses();        from1Count = 0;        from2Count = 0;        fromNoneCount = 0;        while (enum1.hasNext()) {            s1 = (Synapse) enum1.next();            s2 = (Synapse) enum2.next();            s3 = (Synapse) enum3.next();            if (s1.getWeight() == s3.getWeight())                from1Count++;            else if (s2.getWeight() == s3.getWeight())                from2Count++;            else                fromNoneCount++;        }        assertTrue(from1Count > 0);        assertTrue(from2Count > 0);        assertTrue(fromNoneCount > 0);    }    public void testEqualsLayerNode() {        assertTrue(this.ln1.equals(this.ln1));        assertTrue(this.ln1.equals(this.ln2));        assertTrue(this.ln2.equals(this.ln1));        assertTrue(!this.ln1.equals(this.ln3));        assertTrue(!this.ln2.equals(this.ln3));        assertTrue(this.ln1.equalsInValue(this.ln1));        assertTrue(this.ln1.equalsInValue(this.ln2));        assertTrue(this.ln2.equalsInValue(this.ln1));        assertTrue(!this.ln1.equalsInValue(this.ln3));        assertTrue(!this.ln2.equalsInValue(this.ln3));    }    public void testEqualsNeuron() {        Synapse syn;        assertTrue(this.neu1.equals(this.neu2));        assertTrue(this.neu1.equals(this.neu3));        assertTrue(this.neu1.equals(this.neu1));        assertTrue(this.neu3.equals(this.neu2));        assertTrue(this.neu1.equalsInValue(this.neu2));        assertTrue(this.neu1.equalsInValue(this.neu3));        assertTrue(this.neu1.equalsInValue(this.neu1));        assertTrue(this.neu3.equalsInValue(this.neu2));        assertTrue(this.neu1.equalsInWeights(this.neu2));        assertTrue(this.neu1.equalsInWeights(this.neu3));        assertTrue(this.neu1.equalsInWeights(this.neu1));        assertTrue(this.neu3.equalsInWeights(this.neu2));        this.neu2.setValue((short) 4);        assertTrue(!this.neu1.equals(this.neu2));        assertTrue(this.neu1.equals(this.neu3));        assertTrue(this.neu1.equals(this.neu1));        assertTrue(!this.neu3.equals(this.neu2));        assertTrue(!this.neu1.equalsInValue(this.neu2));        assertTrue(this.neu1.equalsInValue(this.neu3));        assertTrue(this.neu1.equalsInValue(this.neu1));        assertTrue(!this.neu3.equalsInValue(this.neu2));        assertTrue(this.neu1.equalsInWeights(this.neu2));        assertTrue(this.neu1.equalsInWeights(this.neu3));        assertTrue(this.neu1.equalsInWeights(this.neu1));        assertTrue(this.neu3.equalsInWeights(this.neu2));        syn = this.neu2.synapseAt(1);        syn.setWeight((short) -10);        assertTrue(!this.neu1.equals(this.neu2));        assertTrue(this.neu1.equals(this.neu3));        assertTrue(this.neu1.equals(this.neu1));        assertTrue(!this.neu3.equals(this.neu2));        assertTrue(!this.neu1.equalsInValue(this.neu2));        assertTrue(this.neu1.equalsInValue(this.neu3));        assertTrue(this.neu1.equalsInValue(this.neu1));        assertTrue(!this.neu3.equalsInValue(this.neu2));        assertTrue(!this.neu1.equalsInWeights(this.neu2));        assertTrue(this.neu1.equalsInWeights(this.neu3));        assertTrue(this.neu1.equalsInWeights(this.neu1));        assertTrue(!this.neu3.equalsInWeights(this.neu2));    }    public void testEqualsNeuronLayer() {        Synapse syn;        Neuron neu;        assertTrue(this.nl1.equals(this.nl2));        assertTrue(this.nl1.equals(this.nl3));        assertTrue(this.nl1.equals(this.nl1));        assertTrue(this.nl3.equals(this.nl2));        assertTrue(this.nl1.equalsInValues(this.nl2));        assertTrue(this.nl1.equalsInValues(this.nl3));        assertTrue(this.nl1.equalsInValues(this.nl1));        assertTrue(this.nl3.equalsInValues(this.nl2));        assertTrue(this.nl1.equalsInWeights(this.nl2));        assertTrue(this.nl1.equalsInWeights(this.nl3));        assertTrue(this.nl1.equalsInWeights(this.nl1));        assertTrue(this.nl3.equalsInWeights(this.nl2));        neu = this.nl2.neuronAt(1);        neu.setValue((short) 4);        assertTrue(!this.nl1.equals(this.nl2));        assertTrue(this.nl1.equals(this.nl3));        assertTrue(this.nl1.equals(this.nl1));        assertTrue(!this.nl3.equals(this.nl2));        assertTrue(!this.nl1.equalsInValues(this.nl2));        assertTrue(this.nl1.equalsInValues(this.nl3));        assertTrue(this.nl1.equalsInValues(this.nl1));        assertTrue(!this.nl3.equalsInValues(this.nl2));        assertTrue(this.nl1.equalsInWeights(this.nl2));        assertTrue(this.nl1.equalsInWeights(this.nl3));        assertTrue(this.nl1.equalsInWeights(this.nl1));        assertTrue(this.nl3.equalsInWeights(this.nl2));        neu = this.nl2.neuronAt(1);        syn = neu.synapseAt(1);        syn.setWeight((short) -10);        assertTrue(!this.nl1.equals(this.nl2));        assertTrue(this.nl1.equals(this.nl3));        assertTrue(this.nl1.equals(this.nl1));        assertTrue(!this.nl3.equals(this.nl2));        assertTrue(!this.nl1.equalsInValues(this.nl2));        assertTrue(this.nl1.equalsInValues(this.nl3));        assertTrue(this.nl1.equalsInValues(this.nl1));        assertTrue(!this.nl3.equalsInValues(this.nl2));        assertTrue(!this.nl1.equalsInWeights(this.nl2));        assertTrue(this.nl1.equalsInWeights(this.nl3));        assertTrue(this.nl1.equalsInWeights(this.nl1));        assertTrue(!this.nl3.equalsInWeights(this.nl2));    }    public void testEqualsNet() {        Iterator<Synapse> syns;        Synapse syn = new Synapse(wgt);        Neuron neu;        NeuronLayer nl;        int i;        assertTrue(this.n1.equalsInValues(this.n1));        assertTrue(this.n1.equalsInWeights(this.n1));        assertTrue(this.n2.equalsInValues(this.n1));        assertTrue(this.n2.equalsInWeights(this.n1));        nl = (NeuronLayer) this.n2.layerAt(2);        neu = (Neuron) nl.layerNodeAt(1);        neu.setValueRandom(this.rv);        assertTrue(!this.n1.equals(this.n2));        assertTrue(!this.n1.equalsInValues(this.n2));        assertTrue(this.n1.equalsInWeights(this.n2));        syns = this.n2.synapses();        for (i = 0; i < 4; i++) {            syn = (Synapse) syns.next();        }        syn.setWeightRandom(this.rw);        assertTrue(!this.n1.equals(this.n2));        assertTrue(!this.n1.equalsInValues(this.n2));        assertTrue(!this.n1.equalsInWeights(this.n2));    }    public void testCalculate() {        Iterator<Synapse> syns;        Synapse syn = new Synapse(wgt);        Layer il1, ol1, il2, ol2;        int i;        il1 = this.n1.getInputLayer();        ol1 = this.n1.getOutputLayer();        il2 = this.n2.getInputLayer();        ol2 = this.n2.getOutputLayer();        assertTrue(il1.equals(il2));        il1.setValuesRandom(new RandomValue(7236));        il2.setValuesRandom(new RandomValue(7236));        assertTrue(il1.equals(il2));        assertTrue(ol1.equals(ol2));        this.n1.calculate();        assertTrue(!ol1.equals(ol2));        this.n2.calculate();        assertTrue(ol1.equals(ol2));        il2.setValuesRandom(new RandomValue(736));        this.n2.calculate();        assertTrue(!ol1.equals(ol2));        il2.setValuesRandom(new RandomValue(7236));        this.n2.calculate();        assertTrue(ol1.equals(ol2));        syns = this.n2.synapses();        for (i = 0; i < 4; i++) {            syn = (Synapse) syns.next();        }        syn.setWeightRandom(this.rw);        this.n2.resetCalculated();        il2.setValuesRandom(new RandomValue(7236));        Layer ol;        ol = this.n2.getOutputLayer();        ol.calculate();        assertTrue(!ol1.equals(ol2));    }    public void testSerialize00() throws IOException, ClassNotFoundException {        FFNet n11;        FileOutputStream ostream;        FileInputStream istream;        ObjectOutputStream po;        ObjectInputStream pi;        File file = TestUtil.tmp("Net00.object");        this.n1.setParametersRandom(234);        n11 = new FFNet(nc);        n11.setParametersRandom(82736);        assertTrue(!n11.equalsInWeights(this.n1));        ostream = new FileOutputStream(file);        po = new ObjectOutputStream(ostream);        po.writeObject(this.n1);        po.flush();        ostream.close();        istream = new FileInputStream(file);        pi = new ObjectInputStream(istream);        n11 = (FFNet) pi.readObject();        istream.close();        assertTrue(n11.equalsInWeights(this.n1));    }    public void testSerialize01() throws IOException, ClassNotFoundException {        FileOutputStream ostream;        FileInputStream istream;        ObjectOutputStream po;        ObjectInputStream pi;        File file = TestUtil.tmp("Net01.object");        FFNet ns1, ns2;        ns1 = new FFNet(nc);        ns1.setParametersRandom(034);        ostream = new FileOutputStream(file);        po = new ObjectOutputStream(ostream);        po.writeObject(ns1);        po.flush();        ostream.close();        istream = new FileInputStream(file);        pi = new ObjectInputStream(istream);        ns2 = (FFNet) pi.readObject();        istream.close();        assertTrue(ns1.equalsInValues(ns2));    }    public void testSerialize02() throws IOException, ClassNotFoundException {        FileOutputStream ostream;        FileInputStream istream;        ObjectOutputStream po;        ObjectInputStream pi;        File file = TestUtil.tmp("Net02.object");        FFNet ns1, ns2;        ns1 = new FFNet(nc);        ns1.setParametersRandom(034);        ostream = new FileOutputStream(file);        po = new ObjectOutputStream(ostream);        po.writeObject(ns1);        po.flush();        ostream.close();        istream = new FileInputStream(file);        pi = new ObjectInputStream(istream);        ns2 = (FFNet) pi.readObject();        istream.close();        assertTrue(ns1.equalsInWeights(ns2));    }    public void testSerialize03() throws IOException, ClassNotFoundException {        FFNet ns1, ns2;        ns1 = new FFNet(nc);        ns1.setParametersRandom(034);        ns2 = (FFNet) writeAndRead(ns1);        assertTrue(ns1.equalsInWeights(ns2));    }    public void testSerialize04() throws IOException, ClassNotFoundException {        FFNet ns1, ns2;        ns1 = new FFNet(nc);        ns1.setParametersRandom(034);        ns2 = (FFNet) writeAndRead(ns1);        assertTrue(ns2.equalsInWeights(ns1));    }    public void testSerialize05() throws IOException, ClassNotFoundException {        FFNet ns1, ns2;        ns1 = new FFNet(nc);        ns1.setParametersRandom(034);        ns1.getInputLayer().setValuesRandom(new RandomValue(7236));        Layer ol;        ol = ns1.getOutputLayer();        ol.calculate();        ns2 = (FFNet) writeAndRead(ns1);        assertTrue(ns2.equalsInStructure(ns1));    }    void runGeneral() {        FFNet net = new FFNet(nc);        net.setParametersRandom(System.currentTimeMillis());        net.setInputLayerValuesRandom(new RandomValue());        Layer ol;        ol = net.getOutputLayer();        ol.calculate();    }    void runCrossover() {        int i;        FFNet net, netFather, netMother;        Random r = new Random();        net = new FFNet(nc);        net.setParametersRandom(System.currentTimeMillis());        netFather = new FFNet(nc);        netFather.setParametersRandom(System.currentTimeMillis());        netMother = new FFNet(nc);        netMother.setParametersRandom(System.currentTimeMillis());        net = new FFNet(nc);        net.setWeightsCrossover(netMother, netFather, r);        for (i = 1; i <= 30; i++) {            netFather.setParametersRandom(System.currentTimeMillis());            netMother.setParametersRandom(System.currentTimeMillis());            net.setWeightsCrossover(netMother, netFather, r);        }    }}