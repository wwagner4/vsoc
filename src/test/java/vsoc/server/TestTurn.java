package vsoc.server;

import junit.framework.TestSuite;
import junit.textui.TestRunner;
import vsoc.util.Vec2D;

/**
 * Testcases for vec2D
 */

public class TestTurn extends AbstractServerTest {

    public TestTurn(String name) {
        super(name);
    }

    public void testTurn00() {
        Server s = new Server();
        TestController lp = new TestController();
        addPlayerWest(s, lp, 0, 0, 0);
        s.addSimObject(new Ball(4, 0));
        ServerUtil.current().addFlagEast(s, ServerFlag.CENTER, 4, 2);
        // Flag type is irrelevant for that test
        ServerUtil.current().addFlagEast(s, ServerFlag.CENTER, -1, 4);
        // Flag type is irrelevant for that test
        lp.getPlayer().turn(-45);
        lp.init();
        s.takeStep();
        lp.init();
        s.informController((VsocPlayer) lp.getPlayer(), lp);
        assertEquals("ball", new Vec2D(2 * S2, -2 * S2), lp.ball);
        assertEquals("otherFlags[0]", new Vec2D(3 * S2, -S2), lp.otherFlags[0]);
        assertTrue("otherFlags[1]", lp.otherFlags[1] == null);
        lp.getPlayer().turn(-45);
        s.takeStep();
        lp.init();
        s.informController((VsocPlayer) lp.getPlayer(), lp);
        assertEquals("otherFlags[0]", new Vec2D(4, 1), lp.otherFlags[0]);
        assertTrue("otherFlags[0]", lp.otherFlags[1] == null);
        assertTrue("ball", lp.ball == null);
    }

    public void testTurn01() {
        Server s = new Server();
        TestController lp = new TestController();
        addPlayerWest(s, lp, 0, 0, 0);
        s.addSimObject(new Ball(4, 0));
        ServerUtil.current().addFlagEast(s, ServerFlag.CENTER, 4, 2);
        ServerUtil.current().addFlagEast(s, ServerFlag.CENTER, -1, 4);
        lp.getPlayer().turn(-90);
        s.takeStep();
        lp.init();
        s.informController((VsocPlayer) lp.getPlayer(), lp);
        assertEquals("otherFlag", new Vec2D(4, 1), lp.otherFlags[0]);
        assertTrue("otherFlag", lp.otherFlags[1] == null);
        assertTrue("otherFlag", lp.ball == null);
    }

    public static void main(String[] args) {
        TestRunner.run(new TestSuite(vsoc.server.TestTurn.class));
    }
}