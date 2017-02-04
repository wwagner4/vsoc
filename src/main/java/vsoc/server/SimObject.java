package vsoc.server;

import java.util.*;
import java.awt.*;
import vsoc.util.*;
import java.awt.geom.*;
import java.io.Serializable;

/**
 * Any simulated object. E.g. Payers, Ball, Flag, ...
 */
public abstract class SimObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Vec2D position = null;

    private static long seed = new Date().getTime();

    protected Random random = new Random(seed++);

    SimObject(double x, double y) {
        setPosition(new Vec2D(x, y));
    }

    public Vec2D getPosition() {
        return this.position;
    }

    public void setPosition(Vec2D vec) {
        this.position = vec;
    }

    abstract Vision createVisionForWestPlayer();

    abstract Vision createVisionForEastPlayer();

    protected void paint(Graphics2D g) {
        double x = getPosition().getX();
        double y = getPosition().getY();
        double w = 1;
        double h = 1;
        Shape s = new Rectangle2D.Double(x - 0.5, y - 0.5, w, h);
        g.setColor(Color.black);
        g.draw(s);
    }

    public void handleError(String msg) {
        System.out.println("ERROR: " + msg);
    }
}