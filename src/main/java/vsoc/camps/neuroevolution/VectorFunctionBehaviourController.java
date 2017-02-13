package vsoc.camps.neuroevolution;

import java.io.Serializable;

import vsoc.behaviour.Behaviour;
import vsoc.behaviour.BehaviourController;
import vsoc.camps.VectorFunction;
import vsoc.nn.feedforward.AbstractFFNetConnector;
import vsoc.nn.feedforward.DefaultFFConnector;

/**
 * Atan control system that controls the robots by means of a neural network.
 */
public class VectorFunctionBehaviourController<V extends VectorFunction> extends BehaviourController implements
        Serializable {

    private static final long serialVersionUID = 0L;

    private VectorFunctionRetinaBehaviour<V> behaviour = null;

    public VectorFunctionBehaviourController() {
        super();
    }
    
    public VectorFunctionBehaviourController(Behaviour behaviour) {
        super(behaviour);
        this.behaviour = createBehaviour(behaviour);
    }

    @SuppressWarnings("unchecked")
	private VectorFunctionRetinaBehaviour<V> createBehaviour(Behaviour behav) {
        if (behav == null) {
            throw new IllegalStateException(
                    "The Behaviour of a NeuroControlSystem must contain exactly one net NetBehaviour.");
        } else if (behav instanceof VectorFunctionRetinaBehaviour) {
            return (VectorFunctionRetinaBehaviour<V>) behav;
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

    public AbstractFFNetConnector createNetConnector() {
        return new DefaultFFConnector();
    }

}
