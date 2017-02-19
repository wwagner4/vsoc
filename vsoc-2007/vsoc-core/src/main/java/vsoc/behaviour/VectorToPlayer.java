package vsoc.behaviour;

import atan.model.Player;

public interface VectorToPlayer {

	void addCommandsFromOutputLayer(double[] out, Sensors sens, Player player);
	
}
