package vsoc.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import vsoc.util.Vec2D;
import atan.model.Flag;

/**
 * A Flag
 */
public class GoalWest extends SimObject {

	private static final long serialVersionUID = 1L;
	
    GoalWest(double x, double y) {
        super(x, y);
    }

    Vision createVisionForWestPlayer() {
        return new VisionGoalOwn(Flag.FLAG_CENTER);
    }

    Vision createVisionForEastPlayer() {
        return new VisionGoalOther(Flag.FLAG_CENTER);
    }

    public double getGoalWidth() {
        return 14.02;
    }

    public Vec2D getSouthPole() {
        Vec2D p = getPosition();
        return new Vec2D(p.getX(), p.getY() - this.getGoalWidth() / 2);
    }

    public Vec2D getNorthPole() {
        Vec2D p = getPosition();
        return new Vec2D(p.getX(), p.getY() + this.getGoalWidth() / 2);
    }

    public void paint(Graphics2D g) {
        double x = getPosition().getX() - 2;
        double y = -getGoalWidth() / 2;
        double w = 2;
        double h = getGoalWidth();
        Shape rect = new Rectangle2D.Double(x, y, w, h);
        g.setColor(Color.red);
        g.fill(rect);
        g.setColor(Color.black);
        g.draw(rect);
    }
}