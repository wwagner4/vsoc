package vsoc.view;

import java.awt.Graphics2D;

public class NullServer implements Simulation {

	@Override
	public void addListener(SimulationChangeListener fieldCanvas) {
		// Nothing to do
	}

	@Override
	public void paint(Graphics2D bg) {
		// Nothing to do
	}

	@Override
	public void setSteps(int steps) {
		// Nothing to do
	}

	@Override
	public void setDelay(int delay) {
		// Nothing to do
	}

}