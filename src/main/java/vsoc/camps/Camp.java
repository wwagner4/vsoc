package vsoc.camps;

import java.io.Serializable;
import java.util.Properties;

import vsoc.server.Server;
import vsoc.view.*;

public interface Camp extends Serializable, SimulationContainer {

    Server getServer();

    void setMaxGenerations(int i);

    boolean isFinished();

    void takeOneStep();
    
    default Simulation getSimulation() {
    	return getServer();
    }

	Properties getProperties();
    
}
