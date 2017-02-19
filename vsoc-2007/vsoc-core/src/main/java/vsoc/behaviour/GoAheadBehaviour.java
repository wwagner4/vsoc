package vsoc.behaviour;

import java.util.Optional;

import atan.model.Player;

/**
 * No matter what the sensors tell. The player is running straight ahead
 */
public class GoAheadBehaviour implements Behaviour {
    
	private static final long serialVersionUID = 1L;

		public GoAheadBehaviour() {
        super();
    }

    public boolean shouldBeApplied(Sensors sens) {
        return true;
    }

    public void apply(Sensors sens, Player player) {
        player.dash(50);
    }

    public Optional<Behaviour> getChild() {
        return Optional.empty();
    }

}
