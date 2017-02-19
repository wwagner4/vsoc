package vsoc.camps.neuroevolution.goalgetter;

import java.util.Optional;

import atan.model.Player;
import vsoc.behaviour.*;

/**
 * Places the Players befor kickoff to its start positions
 */
public class GGBeforeKickoffBehaviour implements Behaviour {

	private static final long serialVersionUID = 1L;

	private Optional<Behaviour> child;

	public GGBeforeKickoffBehaviour(Behaviour child) {
		super();
		this.child = Optional.of(child);
	}

	public boolean shouldBeApplied(Sensors sens) {
		return sens.isBeforeKickOff();
	}

	public void apply(Sensors sens, Player player) {
		moveToStartPosition(player);
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

	public Optional<Behaviour> getChild() {
		return this.child;
	}

}
