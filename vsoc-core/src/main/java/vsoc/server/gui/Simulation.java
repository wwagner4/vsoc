package vsoc.server.gui;

import java.awt.Graphics2D;

public interface Simulation {

	void paint(Graphics2D bg);

	void setSteps(int steps);

	void setDelay(int delay);
	
	void addListener(SimulationChangeListener fieldCanvas); 
	
}
