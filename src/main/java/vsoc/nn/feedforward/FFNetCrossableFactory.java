package vsoc.nn.feedforward;

import java.util.Random;

import vsoc.genetic.CrossableFactory;
import vsoc.nn.Net;

public class FFNetCrossableFactory implements CrossableFactory<Net> {

	private static final long serialVersionUID = 1L;
	
    private static Random ran = new Random();

    private AbstractFFNetConnector connector = null;

    public FFNetCrossableFactory() {
        super();
    }

    public Net createNewCrossableWithRandomAttributes() {
        FFNet net = null;
        if (this.connector == null) {
            net = new FFNet(new DefaultFFConnector());
        } else {
            net = new FFNet(this.connector);
        }
        net.setWeightsRandom(ran.nextLong());
        return net;
    }

    public AbstractFFNetConnector getConnector() {
        return this.connector;
    }

    public void setConnector(AbstractFFNetConnector connector) {
        this.connector = connector;
    }

}
