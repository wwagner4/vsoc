package vsoc.server;

import java.awt.*;
import java.awt.geom.*;
import vsoc.util.*;

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

	public void paint(Graphics2D g) {
		double x = getPosition().getX();
		double y = getPosition().getY();
		double w = 2;
		double h = 2;
		Shape s = new Rectangle2D.Double(x - 1, y - 1, w, h);
		g.setColor(Color.red);
		g.fill(s);
		g.setColor(Color.black);
		g.draw(s);
		double a = 1 * Math.cos(getDirection());
		double b = 1 * Math.sin(getDirection());
		Line2D l = new Line2D.Double(x, y, x + a, y + b);
		g.draw(l);
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