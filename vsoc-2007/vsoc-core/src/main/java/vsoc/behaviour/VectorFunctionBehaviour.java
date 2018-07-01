package vsoc.behaviour;

import java.util.Optional;

import atan.model.Player;

public class VectorFunctionBehaviour<V extends VectorFunction> implements Behaviour {

	private static final long serialVersionUID = 1L;

	private V vectorFunction;

	private SensorsToVector sensorsToVector;

	private VectorToPlayer vectorToPlayer;

	public VectorFunctionBehaviour(V vectorFunction, SensorsToVector sensorsToVector, VectorToPlayer vectorToPlayer) {
		super();
		this.vectorFunction = vectorFunction;
		this.sensorsToVector = sensorsToVector;
		this.vectorToPlayer = vectorToPlayer;
	}

	public boolean shouldBeApplied(Sensors sens) {
		return true;
	}

	public void apply(Sensors sens, Player player) {
		double[] in = this.sensorsToVector.apply(sens);
		double[] out = this.vectorFunction.apply(in);
		vectorToPlayer.addCommandsFromOutputLayer(out, sens, player);
	}

	public Optional<Behaviour> getChild() {
		return Optional.empty();
	}

	public V getVectorFunction() {
		return this.vectorFunction;
	}

	public void setVectorFunction(V vectorFunction) {
		this.vectorFunction = vectorFunction;
	}
}
