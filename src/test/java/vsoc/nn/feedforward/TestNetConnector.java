package vsoc.nn.feedforward;

import vsoc.nn.base.TransferManager;
import vsoc.nn.base.Weighter;
import vsoc.nn.feedforward.AbstractFFNetConnector;
import vsoc.util.*;

public class TestNetConnector extends AbstractFFNetConnector {

	private static final long serialVersionUID = 1L;
	
    public TestNetConnector() {
        super();
    }

    public int seed() {
        return 2342;
    }

    public IntVector nodesPerLayer() {
        IntVector npl = new IntVector();
        npl.addElement(4);
        npl.addElement(4);
        npl.addElement(4);
        return npl;
    }

    public TransferManager transferManager() {
        return new TransferManager();
    }

    public Weighter weighter() {
        return new Weighter((float) 0.1, (float) 1.5);
    }

    public IntVector connProbMatrix() {
        IntVector cpm = new IntVector();
        IntVector cpl;
        cpl = new IntVector();
        cpm.addElement(cpl);
        cpl = new IntVector();
        cpl.addElement(100);
        cpm.addElement(cpl);
        cpl = new IntVector();
        cpl.addElement(0);
        cpl.addElement(100);
        cpm.addElement(cpl);
        cpl = new IntVector();
        cpl.addElement(0);
        cpl.addElement(0);
        cpl.addElement(100);
        cpm.addElement(cpl);
        return cpm;
    }
}