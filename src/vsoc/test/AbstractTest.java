package vsoc.test;

import junit.framework.TestCase;
import vsoc.model.Server;
import vsoc.model.ServerUtil;
import vsoc.model.VsocPlayerEast;
import vsoc.model.VsocPlayerWest;
import vsoc.util.Vec2D;
import atan.model.Controller;

/**
 * General test behaviour.
 */

public abstract class AbstractTest extends TestCase {

    static final double S2 = Math.sqrt(2);

    static final double S3 = Math.sqrt(3);

    public AbstractTest(String name) {
        super(name);
    }

    private double round(double x) {
        return Math.round(x * 1000.0) / 1000.0;
    }

    void assertEquals(String info, Vec2D a, Vec2D b) {
        double dx = Math.abs(a.getX() - b.getX());
        double dy = Math.abs(a.getY() - b.getY());
        if ((dx > 0.001) || (dy > 0.001))
            fail(info + "expected:" + a + "but was:" + b + "diff:(" + round(dx)
                    + "," + round(dy) + ")");
    }

    void assertEquals(Vec2D a, Vec2D b) {
        assertEquals("Vec2D:", a, b);
    }

    void addPlayerWest(Server s, Controller c, double x, double y, double dir) {
        VsocPlayerWest p = new VsocPlayerWest(x, y, dir);
        p.setController(c);
        ServerUtil.current().addPlayerWest(s, p);
    }

    void addPlayerEast(Server s, Controller c, double x, double y, double dir) {
        VsocPlayerEast p = new VsocPlayerEast(x, y, dir);
        p.setController(c);
        ServerUtil.current().addPlayerEast(s, p);
    }

    protected synchronized void pause(int ms) {
        try {
            wait(ms);
        } catch (InterruptedException ex) {
            // continue
        }
    }
}