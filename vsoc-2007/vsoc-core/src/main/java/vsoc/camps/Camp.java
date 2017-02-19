package vsoc.camps;

import java.io.Serializable;

import atan.model.Controller;
import vsoc.server.Server;
import vsoc.server.gui.*;

public interface Camp<M extends Member<?>> extends Serializable, SimulationContainer, Runnable {

	Server getServer();

	Controller getController(int num);

	default Simulation getSimulation() {
		return getServer();
	}

}
