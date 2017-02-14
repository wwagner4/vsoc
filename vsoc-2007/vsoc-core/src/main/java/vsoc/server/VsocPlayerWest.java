package vsoc.server;

import java.awt.Color;

import vsoc.util.Vec2D;

/**
 * Player of the west team.
 */
public class VsocPlayerWest extends VsocPlayer {

	private static final long serialVersionUID = 1L;

	public VsocPlayerWest(double x, double y, double direction) {
		super(x, y, direction);
	}

	Vision createVision(SimObject o) {
		return o.createVisionForWestPlayer();
	}

	Vision createVisionForWestPlayer() {
		return new VisionPlayerOwn(this.getNumber());
	}

	Vision createVisionForEastPlayer() {
		return new VisionPlayerOther(this.getNumber());
	}

	void increaseWestGoalCount() {
		increaseOwnGoalCount();
	}

	void increaseEastGoalCount() {
		increaseOtherGoalCount();
	}

	public String getType() {
		return "WEST";
	}

	protected Color color() {
		return Color.RED;
	}


	public String getTeamName() {
		return ("VsocWest");
	}

	public boolean isTeamEast() {
		return false;
	}

	public void move(int x, int y) {
		setPosition(new Vec2D(x, -y));
	}
}