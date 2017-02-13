package vsoc.camps;

import java.io.Serializable;
import java.util.Properties;

import vsoc.server.Server;
import vsoc.server.gui.*;

public interface Camp<M extends Member<?>> extends Serializable, SimulationContainer {

    Server getServer();

    void setMaxGenerations(int i);

    boolean isFinished();

    void takeOneStep();
    
	Properties getProperties();

	M getMember(int num);
    
    default Simulation getSimulation() {
    	return getServer();
    }

}
