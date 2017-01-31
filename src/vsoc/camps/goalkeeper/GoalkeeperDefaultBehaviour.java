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
        boolean re = false;
        if (sens.sawFlagPenaltyOwn()) {
            re = true;
        } else if (sens.sawFlagGoalOwn()) {
            re = true;
        }
        return re;
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
