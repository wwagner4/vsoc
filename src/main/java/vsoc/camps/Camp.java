package vsoc.camps;

import java.io.Serializable;

import vsoc.reports.Reportable;
import vsoc.server.Server;
import vsoc.view.*;

public interface Camp extends Serializable, SimulationContainer, Reportable {

    Server getServer();

    void setMaxGenerations(int i);

    boolean isFinished();

    void takeOneStep();
    
    default Simulation getSimulation() {
    	return getServer();
    }
    
}
