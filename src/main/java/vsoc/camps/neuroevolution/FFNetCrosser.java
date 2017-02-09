package vsoc.camps.neuroevolution;

import java.util.Random;

import vsoc.genetic.Crosser;
import vsoc.nn.Net;
import vsoc.nn.feedforward.*;

public abstract class FFNetCrosser implements Crosser<FFNet> {

	private AbstractFFNetConnector connector;

	@Override
	public FFNet newChild(FFNet c1, FFNet c2, double mutationRate) {
		return c1.newChild(c2, mutationRate);
	}

	@Override
	public FFNet create(long seed) {
		Random r = new Random(seed);
		FFNet n = new FFNet();
		
		connector.connectNet(n);
		
		
		int pc = n.getParamCount();
		Double[] params = new Double[pc];
		for(int i=0; i< pc; i++) {
			params.
		}
		return n;
	}
	
	@Override
	public double distance(Net c1, Net c2) {
		throw new IllegalStateException("Not yet implemented");
	}

}
