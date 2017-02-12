package vsoc.util;

import vsoc.server.*;

/**
 * Testcases for vec2D
 */

public class TestKick extends AbstractServerTest {

    public TestKick(String name) {
        super(name);
    }

    public void testKickAroundRightPlayer00() {
        assertKickAroundRightPlayer(0.1, 0, 0, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundRightPlayer01() {
        assertKickAroundRightPlayer(-0.1, 0, 180, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundRightPlayer02() {
        assertKickAroundRightPlayer(0, 0.1, 90, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundRightPlayer03() {
        assertKickAroundRightPlayer(0, -0.1, -90, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundRightPlayer04() {
        assertKickAroundRightPlayer(0.1, 0.1, 45, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundRightPlayer05() {
        assertKickAroundRightPlayer(-0.1, 0.1, 135, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundRightPlayer06() {
        assertKickAroundRightPlayer(-0.1, -0.1, 225, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundRightPlayer07() {
        assertKickAroundRightPlayer(0.1, -0.1, -45, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer00() {
        assertKickAroundLeftPlayer(0.1, 0, 180, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer01() {
        assertKickAroundLeftPlayer(-0.1, 0, 0, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer02() {
        assertKickAroundLeftPlayer(0, 0.1, -90, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer03() {
        assertKickAroundLeftPlayer(0, -0.1, 90, 0, 0.1 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer04() {
        assertKickAroundLeftPlayer(0.1, 0.1, 225, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer05() {
        assertKickAroundLeftPlayer(-0.1, 0.1, -45, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer06() {
        assertKickAroundLeftPlayer(-0.1, -0.1, 45, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer07() {
        assertKickAroundLeftPlayer(0.1, -0.1, 135, 0, 0.1 * S2 + 0.16, 0);
    }

    public void testKickAroundLeftPlayer08() {
        assertKickAroundLeftPlayer(-0.1, 0, 0, 45, 0.1 + 0.16 / S2, 0.16 / S2);
    }

    public void testKickAroundLeftPlayer09() {
        assertKickAroundLeftPlayer(-0.1, 0, 0, 30, 0.1 + 0.16 * 0.5 * S3,
                0.16 * 0.5);
    }

    public void testKickAroundLeftPlayer10() {
        assertKickAroundLeftPlayer(-0.1, 0, 0, -45, 0.1 + 0.16 / S2, -0.16 / S2);
    }

    public void testKickAroundLeftPlayer11() {
        assertKickAroundLeftPlayer(-0.1, 0, 0, -30, 0.1 + 0.16 * 0.5 * S3,
                -0.16 * 0.5);
    }

    public void testKickAroundLeftPlayer12() {
        assertKickAroundLeftPlayer(0, -0.1, 90, 45, 0.1 + 0.16 / S2, 0.16 / S2);
    }

    public void testKickAroundLeftPlayer13() {
        assertKickAroundLeftPlayer(0, -0.1, 90, -45, 0.1 + 0.16 / S2, -0.16
                / S2);
    }

    public void testKickAroundLeftPlayer14() {
        assertKickAroundLeftPlayer(0.1, -0.1, 135, -45, 0.1 * S2 + 0.16 / S2,
                -0.16 / S2);
    }

    private void assertKickAroundLeftPlayer(double xPos, double yPos, int dir,
            int kickDir, double xSee, double ySee) {
        Server s = new Server();
        TestController c = new TestController();
        addPlayerWest(s, c, xPos, yPos, dir);
        s.addSimObject(new Ball(0, 0));
        c.getPlayer().kick(10, kickDir);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("ball", new Vec2D(xSee, ySee), c.ball);
    }

    private void assertKickAroundRightPlayer(double xPos, double yPos, int dir,
            int kickDir, double xSee, double ySee) {
        Server s = new Server();
        TestController c = new TestController();
        addPlayerEast(s, c, xPos, yPos, dir + 180);
        s.addSimObject(new Ball(0, 0));
        c.getPlayer().kick(10, kickDir);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("ball", new Vec2D(xSee, ySee), c.ball);
    }

    public void testKickDecay() {
        Server s = new Server();
        TestController c = new TestController();
        addPlayerEast(s, c, 0.1, 0, 180);
        s.addSimObject(new Ball(0, 0));
        c.getPlayer().kick(10, 0);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("ball step1:", new Vec2D(0.26, 0), c.ball);
        s.takeStep();
        c.init();
        s.informController((VsocPlayer) c.getPlayer(), c);
        assertEquals("ball step2:", new Vec2D(0.41, 0), c.ball);
    }
}