package vsoc.camps.neuroevolution;

import java.io.Serializable;

import vsoc.genetic.*;
import vsoc.nn.feedforward.*;

public class FFNetCrosser implements Crosser<FFNet>, Serializable {

	private static final long serialVersionUID = 1L;
	private AbstractFFNetConnector connector = new DefaultFFConnector();
	private CrossoverSwitch crossoverSwitch = new CrossoverSwitch(50, 20);
	
	public FFNetCrosser() {
		super();
	}

	@Override
	public FFNet newChild(FFNet c1, FFNet c2, double mutationRate) {
		return c1.newChild(c2, mutationRate, crossoverSwitch, connector);
	}

	@Override
	public FFNet create(long seed) {
		FFNet n = new FFNet();
		connector.initLayers(n);
		connector.connectNet(n);
		n.setParametersRandom(seed);
		return n;
	}
	
	@Override
	public double distance(FFNet c1, FFNet c2) {
		return c1.distance(c2);
	}

}
