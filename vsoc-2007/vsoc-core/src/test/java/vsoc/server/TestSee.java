package vsoc.server;

import atan.model.Controller;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import vsoc.util.Vec2D;

/**
 * Testcases for vec2D
 */

public class TestSee extends AbstractServerTest {

    public TestSee(String name) {
        super(name);
    }

    public void testSee00() {
        Server s = new Server();
        TestController a = new TestController();
        TestController b = new TestController();
        TestController c = new TestController();
        initServer00(s, a, b, c);
        a.init();
        s.informController((VsocPlayer) a.getPlayer(), a);
        assertEquals("LeftFlag:3", new Vec2D(3.5 * S2, 2.5 * S2),
                a.leftFlags[0]);
        assertEquals("LeftFlag:4", new Vec2D(6.5 * S2, -0.5 * S2),
                a.leftFlags[1]);
        assertEquals("LeftFlag:5", new Vec2D(3 * S2, -S2), a.leftFlags[2]);
        assertNull("LeftFlag", a.leftFlags[3]);
        assertNull("LeftFlag", a.leftFlags[4]);
        assertNull("LeftFlag", a.leftFlags[5]);
        assertNull("LeftFlag", a.leftFlags[6]);
        assertNull("LeftFlag", a.leftFlags[7]);
        assertNull("LeftFlag", a.leftFlags[8]);
        assertNull("LeftFlag", a.leftFlags[9]);
        assertNull("LeftFlag", a.leftFlags[10]);
        assertNull("LeftFlag", a.leftFlags[11]);
    }

    public void testSee01r1() {
        Server s = new Server();
        TestController r1 = new TestController();
        TestController r2 = new TestController();
        TestController l1 = new TestController();
        TestController l2 = new TestController();
        initServer01(s, r1, r2, l1, l2);
        r1.init();
        s.informController((VsocPlayer) r1.getPlayer(), r1);
        assertEquals("left player 1", new Vec2D(3, -2), r1.otherPlayer[0]);
        assertEquals("left goal", new Vec2D(6, -1), r1.otherGoal);
        assertEquals("left flag", new Vec2D(6, 2), r1.otherFlags[0]);
    }

    public void testSee01r2() {
        Server s = new Server();
        TestController r1 = new TestController();
        TestController r2 = new TestController();
        TestController l1 = new TestController();
        TestController l2 = new TestController();
        initServer01(s, r1, r2, l1, l2);
        r2.init();
        s.informController((VsocPlayer) r2.getPlayer(), r2);
        assertEquals("left player 1", new Vec2D(12, -2), r2.otherPlayer[0]);
        assertEquals("left player 2", new Vec2D(10, 3), r2.otherPlayer[1]);
        assertEquals("right player 1", new Vec2D(9, 0), r2.ownPlayer[0]);
        assertEquals("bottom flag", new Vec2D(7, 4), r2.leftFlags[0]);
        assertEquals("left flag", new Vec2D(15, 2), r2.otherFlags[0]);
    }

    public void testSee01l1() {
        Server s = new Server();
        TestController r1 = new TestController();
        TestController r2 = new TestController();
        TestController l1 = new TestController();
        TestController l2 = new TestController();
        initServer01(s, r1, r2, l1, l2);
        l1.init();
        s.informController((VsocPlayer) l1.getPlayer(), l1);
        assertEquals("top flag", new Vec2D(5, 2), l1.leftFlags[0]);
        assertEquals("right player 1", new Vec2D(3, -2), l1.otherPlayer[0]);
        assertEquals("right flag", new Vec2D(13, 1), l1.otherFlags[0]);
        assertEquals("right goal", new Vec2D(13, -1), l1.otherGoal);
        assertEquals("right player 1", new Vec2D(12, -2), l1.otherPlayer[1]);
    }

    public void testSee01l2() {
        Server s = new Server();
        TestController r1 = new TestController();
        TestController r2 = new TestController();
        TestController l1 = new TestController();
        TestController l2 = new TestController();
        initServer01(s, r1, r2, l1, l2);
        l2.init();
        s.informController((VsocPlayer) l2.getPlayer(), l2);
        assertEquals("left goal", new Vec2D(5, -4), l2.ownGoal);
        assertEquals("left flag", new Vec2D(5, -1), l2.ownFlags[0]);
    }

    private void initServer01(Server s, Controller r1, Controller r2,
            Controller l1, Controller l2) {
        addPlayerEast(s, r1, -4, -1, 180);
        addPlayerEast(s, r2, 5, -1, 180);
        addPlayerWest(s, l1, -7, 1, 0);
        addPlayerWest(s, l2, -5, -4, 180);
        ServerUtil u = ServerUtil.current();
        u.addGoalWest(s, -10, 0);
        u.addFlagWest(s, ServerFlag.CENTER, -10, -3); // Flag type is irrelevant
        u.addFlagNorth(s, ServerFlag.CENTER, -2, 3); // Flag type is irrelevant
        u.addFlagSouth(s, ServerFlag.CENTER, -2, -5); // Flag type is irrelevant
        u.addGoalEast(s, 6, 0);
        u.addFlagEast(s, ServerFlag.CENTER, 6, 2); // Flag type is irrelevant
    }

    private void initServer00(Server s, Controller a, Controller b, Controller c) {
        addPlayerWest(s, a, 4, 6, 45);
        addPlayerWest(s, b, -3, 4, 90);
        addPlayerWest(s, c, 1, -2, 240);
        // Flag type is irrelevant.
        ServerUtil u = ServerUtil.current();
        u.addFlagNorth(s, ServerFlag.CENTER, 4, 16);
        u.addFlagNorth(s, ServerFlag.CENTER, 2, 12);
        u.addFlagNorth(s, ServerFlag.CENTER, 5, 12);
        u.addFlagNorth(s, ServerFlag.CENTER, 11, 12);
        u.addFlagNorth(s, ServerFlag.CENTER, 8, 8);
        u.addFlagNorth(s, ServerFlag.CENTER, 9, 4);
        u.addFlagNorth(s, ServerFlag.CENTER, -8, 7);
        u.addFlagNorth(s, ServerFlag.CENTER, -3, -3);
        u.addFlagNorth(s, ServerFlag.CENTER, -1, -4);
        u.addFlagNorth(s, ServerFlag.CENTER, 4, -4);
    }

    public static void main(String[] args) {
        TestRunner.run(new TestSuite(vsoc.server.TestSee.class));
    }
}