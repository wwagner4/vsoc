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
public class NetBehaviourController<N extends VectorFunction> extends BehaviourController implements
        Serializable {

    private static final long serialVersionUID = 0L;

    private NetBehaviour<N> netBehaviour = null;

    public NetBehaviourController() {
        super();
    }
    
    public NetBehaviourController(Behaviour behaviour) {
        super(behaviour);
        this.netBehaviour = getNetBehaviour(behaviour);
    }

    @SuppressWarnings("unchecked")
	private NetBehaviour<N> getNetBehaviour(Behaviour behav) {
        if (behav == null) {
            throw new IllegalStateException(
                    "The Behaviour of a NeuroControlSystem must contain exactly one net NetBehaviour.");
        } else if (behav instanceof NetBehaviour) {
            return (NetBehaviour<N>) behav;
        } else {
            return getNetBehaviour(behav.getChild());
        }
    }

    public N getNet() {
        return this.netBehaviour.getNet();
    }

    public void setNet(N net) {
        this.netBehaviour.setNet(net);
    }

    public AbstractFFNetConnector createNetConnector() {
        return new DefaultFFConnector();
    }

}
