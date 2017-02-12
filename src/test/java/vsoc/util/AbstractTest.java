package vsoc.util;

import junit.framework.TestCase;

/**
 * General test behaviour.
 */

public abstract class AbstractTest extends TestCase {

    public static final double S2 = Math.sqrt(2);

    public static final double S3 = Math.sqrt(3);

    public AbstractTest(String name) {
        super(name);
    }

    private double round(double x) {
        return Math.round(x * 1000.0) / 1000.0;
    }

    protected void assertEquals(String info, Vec2D a, Vec2D b) {
        double dx = Math.abs(a.getX() - b.getX());
        double dy = Math.abs(a.getY() - b.getY());
        if ((dx > 0.001) || (dy > 0.001))
            fail(info + "expected:" + a + "but was:" + b + "diff:(" + round(dx)
                    + "," + round(dy) + ")");
    }

    protected void assertEquals(Vec2D a, Vec2D b) {
        assertEquals("Vec2D:", a, b);
    }

    protected synchronized void pause(int ms) {
        try {
            wait(ms);
        } catch (InterruptedException ex) {
            // continue
        }
    }
}