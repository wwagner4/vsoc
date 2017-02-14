package vsoc.camps;

import java.io.Serializable;

import vsoc.behaviour.*;

/**
 * Atan control system that controls the robots by means of a neural network.
 */
public class VectorFunctionBehaviourController<V extends VectorFunction> extends BehaviourController implements
        Serializable {

    private static final long serialVersionUID = 0L;

    private VectorFunctionBehaviour<V> behaviour = null;

    public VectorFunctionBehaviourController() {
        super();
    }
    
    public VectorFunctionBehaviourController(Behaviour behaviour) {
        super(behaviour);
        this.behaviour = createBehaviour(behaviour);
    }

    @SuppressWarnings("unchecked")
	private VectorFunctionBehaviour<V> createBehaviour(Behaviour behav) {
        if (behav == null) {
            throw new IllegalStateException(
                    "The Behaviour of the ControlSystem must contain exactly one VectorFunction.");
        } else if (behav instanceof VectorFunctionBehaviour) {
            return (VectorFunctionBehaviour<V>) behav;
        } else {
            return createBehaviour(behav.getChild());
        }
    }

    public V getVectorFunction() {
        return this.behaviour.getVectorFunction();
    }

    public void setVectorFunction(V vectorFunction) {
        this.behaviour.setVectorFunction(vectorFunction);
    }

}
