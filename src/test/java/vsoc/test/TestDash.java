package vsoc.test;

import vsoc.model.Ball;
import vsoc.model.Server;
import vsoc.model.ServerUtil;
import vsoc.model.VsocPlayer;
import vsoc.util.Vec2D;

/**
 * Testcases for vec2D
 */

public class TestDash extends AbstractTest {

    public TestDash(String name) {
        super(name);
    }
    public void testDash00() {
        Server s = new Server();
        TestController c = new TestController();
        addPlayerWest(s, c, 0, 0, 0);
        s.addSimObject(new Ball(4, 0));
        c.getPlayer().dash(10);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 1", new Vec2D(4 - 0.06, 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 2", new Vec2D(4 - 0.06 - 0.06 * 0.4, 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 3", new Vec2D(4 - 0.06 - 0.06 * 0.4 - 0.06 * 0.4 * 0.4, 0), c.ball);
    }
    public void testDash01() {
        Server s = new Server();
        TestController c = new TestController();
        addPlayerWest(s, c, 0, 0, 0);
        s.addSimObject(new Ball(4, 0));
        c.getPlayer().dash(-10);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 1", new Vec2D(4 + 0.06, 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 2", new Vec2D(4 + 0.06 + 0.06 * 0.4, 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 3", new Vec2D(4 + 0.06 + 0.06 * 0.4 + 0.06 * 0.4 * 0.4, 0), c.ball);
    }
    public void testDash02() {
        Server s = new Server();
        TestController c = new TestController();
        addPlayerEast(s, c, 0, 0, 225 + 180);
        s.addSimObject(new Ball(1, 1));
        c.getPlayer().dash(20);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 1 ", new Vec2D(S2 - 0.12, 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 2 ", new Vec2D(S2 - (0.12 + 0.12 * 0.4), 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 3 ", new Vec2D(S2 - (0.12 + 0.12 * 0.4 + 0.12 * 0.4 * 0.4), 0), c.ball);
    }
    public void testDash03() {
        Server s = new Server();
        TestController c = new TestController();
        addPlayerEast(s, c, 0, 0, 45);
        s.addSimObject(new Ball(1, 1));
        c.getPlayer().dash(-20);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 1 ", new Vec2D(S2 + 0.12, 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 2 ", new Vec2D(S2 + (0.12 + 0.12 * 0.4), 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("step 3 ", new Vec2D(S2 + (0.12 + 0.12 * 0.4 + 0.12 * 0.4 * 0.4), 0), c.ball);
    }
}