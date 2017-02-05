package vsoc.behaviour;

import java.util.Random;

import atan.model.Player;

/**
 * Turns the player a random angle if the sensors see nothing. Prevents players
 * to run away from the field.
 */
public class DefaultBehaviour implements Behaviour {

		private static final long serialVersionUID = 1L;
    
		private Behaviour child;

    private static Random random = new Random();

    public DefaultBehaviour(Behaviour child) {
        super();
        this.child = child;
    }

    public boolean shouldBeApplied(Sensors sens) {
        return !sens.sawAnything() || sens.isBeforeKickOff();
    }

    public void apply(Sensors sens, Player player) {
        if (sens.isBeforeKickOff()) {
            moveToStartPosition(player);
        }
        this.randomTurn(player);
    }

    private void moveToStartPosition(Player player) {
        switch (player.getNumber()) {
        case 1:
            player.move(-10, 0);
            break;
        case 2:
            player.move(-10, 10);
            break;
        case 3:
            player.move(-10, -10);
            break;
        case 4:
            player.move(-20, 0);
            break;
        case 5:
            player.move(-20, 10);
            break;
        case 6:
            player.move(-20, -10);
            break;
        case 7:
            player.move(-20, 20);
            break;
        case 8:
            player.move(-20, -20);
            break;
        case 9:
            player.move(-30, 0);
            break;
        case 10:
            player.move(-40, 10);
            break;
        case 11:
            player.move(-40, -10);
            break;
        default:
            throw new IllegalStateException("number must be initialized before move");
        }
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
