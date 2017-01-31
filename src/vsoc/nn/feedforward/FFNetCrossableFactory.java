package vsoc.nn.feedforward;

import java.util.Random;

import vsoc.genetic.Crossable;
import vsoc.genetic.CrossableFactory;

public class FFNetCrossableFactory implements CrossableFactory {

    private static Random ran = new Random();

    private AbstractFFNetConnector connector = null;

    public FFNetCrossableFactory() {
        super();
    }

    public Crossable createNewCrossableWithRandomAttributes() {
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
