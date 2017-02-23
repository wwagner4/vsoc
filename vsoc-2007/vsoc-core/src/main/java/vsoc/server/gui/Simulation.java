package vsoc.server.gui;

import java.awt.Graphics2D;

public interface Simulation {

	void paint(Graphics2D bg);

	void addListener(SimulationChangeListener fieldCanvas); 
	
	boolean isInformListeners();

	void setInformListeners(boolean informListeners);


	
}
