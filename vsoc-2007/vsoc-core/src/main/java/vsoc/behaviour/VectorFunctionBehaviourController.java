package vsoc.behaviour;

import java.io.Serializable;

/**
 * Atan control system that controls the robots by means of a neural network.
 */
public class VectorFunctionBehaviourController<V extends VectorFunction> extends BehaviourController
    implements Serializable {

	private static final long serialVersionUID = 0L;

	private VectorFunctionBehaviour<V> behaviour;

	public VectorFunctionBehaviourController(Behaviour behaviour) {
		super(behaviour);
		this.behaviour = findVectorFunctionBehaviour(behaviour);
	}

	/**
	 * Search for the VectorFunctionBehaviour in the hierarchy of behaviours
	 * 
	 * @param behav
	 *          The actual behaviour
	 * @return the VectorFunctionBehaviour if there is one in the hierarchy
	 */
	@SuppressWarnings("unchecked")
	private VectorFunctionBehaviour<V> findVectorFunctionBehaviour(Behaviour behav) {
		if (behav instanceof VectorFunctionBehaviour) {
			return (VectorFunctionBehaviour<V>) behav;
		} else if (behav.getChild().isPresent()) {
			return findVectorFunctionBehaviour(behav.getChild().get());
		} else {
			throw new IllegalStateException(
			    "The Behaviour of the ControlSystem must contain at least one VectorFunctionBehaviour");
		}
	}

	public V getVectorFunction() {
		return this.behaviour.getVectorFunction();
	}

	public void setVectorFunction(V vectorFunction) {
		this.behaviour.setVectorFunction(vectorFunction);
	}

}
