package vsoc.model;

import vsoc.util.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * The Ball
 */
public class Ball extends MoveObject {

		private static final long serialVersionUID = 1L;
    
		private Vec2D previousPosition = new Vec2D(0, 0);

    private VsocPlayer kicker = null;

    public Ball(double x, double y) {
        super(x, y);
    }

    public Vec2D getVeloVector() {
        return new Vec2D(Math.cos(getDirection()) * getVelo(), Math
                .sin(getDirection())
                * getVelo());
    }

    protected void moveFromVelo() {
        setVelo(getVelo() * getServer().getBallDecay());
        moveRelative(getVeloVector());
    }

    Vision createVisionForWestPlayer() {
        return new VisionBall();
    }

    Vision createVisionForEastPlayer() {
        return new VisionBall();
    }

    public void setPosition(Vec2D newPosition) {
        this.previousPosition = getPosition();
        super.setPosition(newPosition);
    }

    void kicked(VsocPlayer kicker, int power, double dir) {
        if (kicker.getPosition().sub(getPosition()).length() <= 0.7) {
            Vec2D vkr = new Vec2D(kicker.getDirection() + Vec2D.degToRad(dir));
            Vec2D vk = vkr.mul(power * getServer().getKickPowerRate()
                    / getServer().getBallDecay());
            Vec2D v1 = getVeloVector().add(vk);
            setDirection(v1.phi());
            setVelo(v1.length());
            this.kicker = kicker;
            kicker.increaseKickCount();
        }
    }

    Vec2D getPreviousPosition() {
        return this.previousPosition;
    }

    public VsocPlayer getKicker() {
        return this.kicker;
    }

    public void paint(Graphics2D g) {
        double x = getPosition().getX();
        double y = getPosition().getY();
        double w = 1;
        double h = 1;
        Shape s = new Rectangle2D.Double(x - 0.5, y - 0.5, w, h);
        g.setColor(Color.white);
        g.fill(s);
        g.setColor(Color.black);
        g.draw(s);
    }
}
