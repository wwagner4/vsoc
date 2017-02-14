package vsoc.camps.neuroevolution.goalkeeper;

import atan.model.Player;
import vsoc.behaviour.*;

/**
 * Places the Players befor kickoff to its start positions
 */
public class GKBeforeKickoffBehaviour implements Behaviour {

	private static final long serialVersionUID = 1L;

	private Behaviour child;

	public GKBeforeKickoffBehaviour(Behaviour child) {
		super();
		this.child = child;
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
			player.move(-40, 0);
			break;
		default:
			throw new IllegalStateException("number must be initialized before move");
		}
	}

	public Behaviour getChild() {
		return this.child;
	}

}
