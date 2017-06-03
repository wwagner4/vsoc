package vsoc.server;

import java.awt.Color;

import vsoc.util.Vec2D;

/**
 * Player of the east team.
 */

public class VsocPlayerEast extends VsocPlayer {

	private static final long serialVersionUID = 1L;

	public VsocPlayerEast(double x, double y, double direction) {
		super(x, y, direction);
	}

	Vision createVision(SimObject o) {
		return o.createVisionForEastPlayer();
	}

	Vision createVisionForWestPlayer() {
		return new VisionPlayerOther(this.getNumber());
	}

	Vision createVisionForEastPlayer() {
		return new VisionPlayerOwn(this.getNumber());
	}

	void increaseWestGoalCount() {
		increaseOtherGoalCount();
	}

	void increaseEastGoalCount() {
		increaseOwnGoalCount();
	}

	public String getType() {
		return "EAST";
	}

	protected Color color() {
		return Color.RED;
	}

	public String getTeamName() {
		return ("VsocEast");
	}

	public boolean isTeamEast() {
		return true;
	}

	public void move(int x, int y) {
		setPosition(new Vec2D(-x, y));
	}
}