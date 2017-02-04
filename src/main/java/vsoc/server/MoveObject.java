package vsoc.server;

import vsoc.util.*;

/**
 * Any simulated object. E.g. Payers, Ball, Flag, ...
 */
public abstract class MoveObject extends SimObject {

	private static final long serialVersionUID = 1L;
	
    private double direction = 0.0;

    private Server server = null;

    private double velo = 0.0;

    MoveObject(double x, double y, double angle) {
        super(x, y);
        this.direction = Vec2D.degToRad(angle);
    }

    MoveObject(double x, double y) {
        this(x, y, 0.0);
    }

    public Vec2D getVeloVector() {
        return new Vec2D(Math.cos(this.direction) * this.velo, Math
                .sin(this.direction)
                * this.velo);
    }

    public void turn(double angle) {
        this.direction = this.direction - Vec2D.degToRad(angle);
    }

    protected void moveFromVelo() {
        // nothing to do
    }

    protected void moveRelative(Vec2D v) {
        setPosition(getPosition().add(v));
    }

    public double getDirection() {
        return this.direction;
    }

    public void setDirection(double val) {
        this.direction = val;
    }

    public double getVelo() {
        return this.velo;
    }

    public void setVelo(double val) {
        this.velo = val;
    }

    Server getServer() {
        return this.server;
    }

    void setServer(Server s) {
        this.server = s;
    }
}