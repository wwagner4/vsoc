package vsoc.camps.neuroevolution;

import vsoc.genetic.*;
import vsoc.nn.feedforward.*;

public class FFNetCrosser implements Crosser<FFNet> {

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
		connector.connectNet(n);
		n.setParametersRandom(seed);
		return n;
	}
	
	@Override
	public double distance(FFNet c1, FFNet c2) {
		throw new IllegalStateException("Not yet implemented");
	}

}
