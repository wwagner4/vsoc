package vsoc.camps.neuroevolution.goalgetter;

import java.util.*;

import atan.model.Player;
import vsoc.behaviour.*;

/**
 * Turns the player a random angle if the sensors see nothing. Prevents players
 * to run away from the field.
 */
public class GGDefaultBehaviour implements Behaviour {

		private static final long serialVersionUID = 1L;
    
		private Optional<Behaviour> child;

    private static Random random = new Random();

    public GGDefaultBehaviour(Behaviour child) {
        super();
        this.child = Optional.of(child);
    }

    public boolean shouldBeApplied(Sensors sens) {
        return !sens.sawAnything() || sens.isBeforeKickOff();
    }

    public void apply(Sensors sens, Player player) {
        this.randomTurn(player);
    }

    public Optional<Behaviour> getChild() {
        return this.child;
    }

    private void randomTurn(Player player) {
        if (random.nextDouble() > 0.5)
            player.turn(-100);
        else
            player.turn(100);
    }

}
