package vsoc.camps.goalkeeper;

import java.util.Random;

import vsoc.behaviour.Behaviour;
import vsoc.behaviour.Sensors;
import atan.model.Player;

/**
 * Turns the player a random angle if the sensors do not see the flags around
 * the goal.
 */
public class GoalkeeperDefaultBehaviour implements Behaviour {

		private static final long serialVersionUID = 1L;
    
		private Behaviour child;

    private static Random random = new Random();

    public GoalkeeperDefaultBehaviour(Behaviour child) {
        super();
        this.child = child;
    }

    public boolean shouldBeApplied(Sensors sens) {
        return !canSeeOwnGoalEnvironment(sens);
    }

    private boolean canSeeOwnGoalEnvironment(Sensors sens) {
        if (sens.sawFlagPenaltyOwn() || sens.sawFlagGoalOwn()) {
            return true;
        } 
        return false;
    }

    public void apply(Sensors sens, Player player) {
        this.randomTurn(player);
    }

    public Behaviour getChild() {
        return this.child;
    }

    private void randomTurn(Player player) {
        if (random.nextDouble() > 0.5)
            player.turn(-100);
        else
            player.turn(100);
    }

}
