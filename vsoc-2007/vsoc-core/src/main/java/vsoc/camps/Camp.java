package vsoc.camps;

import java.io.Serializable;

import atan.model.Controller;
import vsoc.server.*;
import vsoc.server.gui.*;

public interface Camp<M extends Member<?>> extends Serializable, SimulationContainer, Runnable {

	CtrlServer getServer();

	Controller getController(int num);

	default CtrlSimulation getSimulation() {
		return getServer();
	}

}
