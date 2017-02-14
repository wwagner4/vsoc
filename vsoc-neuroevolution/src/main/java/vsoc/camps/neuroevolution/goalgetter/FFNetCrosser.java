package vsoc.camps.neuroevolution.goalgetter;

import java.io.Serializable;
import java.util.Iterator;

import vsoc.genetic.*;
import vsoc.nn.base.*;
import vsoc.nn.feedforward.*;

public class FFNetCrosser implements Crosser<FFNet>, Serializable {

	private static final long serialVersionUID = 1L;
	private AbstractFFNetConnector connector = new DefaultFFConnector();
	private CrossoverSwitch crossoverSwitch = new DefaultCrossoverSwitch(50, 20);

	public FFNetCrosser() {
		super();
	}

	@Override
	public FFNet newChild(FFNet c1, FFNet c2, double mutationRate) {
		return newChild(c1, c2, mutationRate, crossoverSwitch, connector);
	}

	public void setCrossoverSwitch(CrossoverSwitch crossoverSwitch) {
		this.crossoverSwitch = crossoverSwitch;
	}

	public FFNet newChild(FFNet parent, FFNet otherParent, double mutationRate, CrossoverSwitch crossoverSwitch,
			AbstractFFNetConnector connector) {
		Mutator mut = new DefaultMutator((int) (mutationRate * 1000000));
		FFNet childNet = new FFNet();
		connector.initLayers(childNet);
		connector.connectNet(childNet);
		setWeightsCrossover(childNet, parent, otherParent, crossoverSwitch, mut);
		return childNet;
	}

	private void setWeightsCrossover(FFNet child, FFNet netA, FFNet netB, CrossoverSwitch cs, Mutator mut) {
		RandomWgt rw = new RandomWgt();
		Iterator<Synapse> enumA = netA.synapses();
		Iterator<Synapse> enumB = netB.synapses();
		Iterator<Synapse> enumChild = child.synapses();
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
