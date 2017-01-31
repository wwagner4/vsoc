package vsoc.behaviour;

import java.io.Serializable;

import atan.model.Player;

public interface Behaviour extends Serializable {

    /**
     * Defines if the behaviour should be applyed according to the state of the
     * sensors.
     * 
     * @param sens
     *            The sensors.
     * @return True if the behaviour should be applied.
     */
    boolean shouldBeApplied(Sensors sens);

    /**
     * Applyes the information of the sensors to control the player.
     * 
     * @param sens
     *            The sensors.
     * @param player
     *            The player to be controlled.
     */
    void apply(Sensors sens, Player player);

    /**
     * Child behaviour that can be applied if this beahaviour should not be
     * applied.
     * 
     * @return The child behaviour.
     */
    Behaviour getChild();

}
