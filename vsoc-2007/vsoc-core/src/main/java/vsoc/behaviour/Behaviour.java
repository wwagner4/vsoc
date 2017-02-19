package vsoc.behaviour;

import java.io.Serializable;
import java.util.Optional;

import atan.model.Player;

public interface Behaviour extends Serializable {

    /**
     * Defines if the behavior should be applied according to the state of the
     * sensors.
     * 
     * @param sens
     *            The sensors.
     * @return True if the behavior should be applied.
     */
    boolean shouldBeApplied(Sensors sens);

    /**
     * Applies the information of the sensors to control the player.
     * 
     * @param sens
     *            The sensors.
     * @param player
     *            The player to be controlled.
     */
    void apply(Sensors sens, Player player);

    /**
     * Child behavior that can be applied if this behavior should not be
     * applied.
     * 
     * @return The child behavior.
     */
    Optional<Behaviour> getChild();

}
