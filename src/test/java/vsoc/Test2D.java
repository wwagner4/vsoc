package vsoc;

import junit.framework.*;
import junit.textui.*;
import vsoc.util.*;

/**
 * Testcases for vec2D
 */

public class Test2D extends AbstractTest {

    public Test2D(String name) {
        super(name);
    }

    public void testAdd00() {
        Vec2D a = new Vec2D(0, 0);
        Vec2D b = new Vec2D(1, 1);
        assertEquals(a.add(b), new Vec2D(1, 1));
    }

    public void testAdd02() {
        Vec2D a = new Vec2D(0.0, 0.0);
        Vec2D b = new Vec2D(1.0, 1.0);
        assertEquals(a.add(b), new Vec2D(1.0, 1));
    }

    public void testAdd03() {
        Vec2D a = new Vec2D(1.0, 0.0);
        Vec2D b = new Vec2D(1.0, 1.0);
        assertEquals(a.add(b), new Vec2D(2, 1));
    }

    public void testAdd04() {
        Vec2D a = new Vec2D(1.0, 0.0);
        Vec2D b = new Vec2D(-1.0, 0.0);
        assertEquals(a.add(b), new Vec2D(0, 0));
    }

    private void subAssert(Vec2D a, Vec2D b) {
        Vec2D c = a.add(b);
        assertEquals(c.sub(b), a);
        assertEquals(c.sub(a), b);
    }

    public void testSub00() {
        subAssert(new Vec2D(0, 0), new Vec2D(0, 0));
    }

    public void testSub01() {
        subAssert(new Vec2D(0.4, 0.7), new Vec2D(10, 100));
    }

    public void testSub02() {
        subAssert(new Vec2D(-0, -300), new Vec2D(-200, 234234));
    }

    public void testSub03() {
        subAssert(new Vec2D(-1, 0), new Vec2D(1, 0));
    }

    public void testSub04() {
        subAssert(new Vec2D(2340, 234), new Vec2D(-234, .000003));
    }

    private void lengthAssert(Vec2D v, double l) {
        assertEquals(v.length(), l, 0.000001);
    }

    public void testLength00() {
        lengthAssert(new Vec2D(0, 0), 0);
    }

    public void testLength01() {
        lengthAssert(new Vec2D(1, 1), Math.sqrt(2));
    }

    public void testLength02() {
        lengthAssert(new Vec2D(1, 0), 1);
    }

    public void testLength03() {
        lengthAssert(new Vec2D(0, 2), 2);
    }

    public void testLength04() {
        lengthAssert(new Vec2D(-1, 0), 1);
    }

    public void testLength05() {
        lengthAssert(new Vec2D(1200, 2322.45), 2614.1488103204833);
    }

    public void testLength06() {
        lengthAssert(new Vec2D(-1200, -2322.45), 2614.1488103204833);
    }

    public void testLength07() {
        lengthAssert(new Vec2D(-1200, 2322.45), 2614.1488103204833);
    }

    public void testIsInSector() {
        assertTrue("isInSec,(1,1)", new Vec2D(1, 1).isInSec());
        assertTrue("isInSec,(1,-1)", new Vec2D(1, -1).isInSec());
        assertTrue("isInSec,(1,-1.00...001)", !new Vec2D(1, -1.0000000001)
                .isInSec());
    }

    public void testRot00() {
        Vec2D a = new Vec2D(1.0, 0.0);
        Vec2D r = new Vec2D(1.0, 1.0);
        Vec2D b = new Vec2D(1 / S2, -1 / S2);
        assertEquals(b, a.rot(r.phi()));
    }

    public void testRot01() {
        Vec2D a = new Vec2D(1, 1);
        Vec2D r = new Vec2D(1, 1);
        Vec2D b = new Vec2D(S2, 0);
        assertEquals(b, a.rot(r.phi()));
    }

    public void testRot03() {
        Vec2D a = new Vec2D(1, 1);
        Vec2D r = new Vec2D(0, 1);
        Vec2D b = new Vec2D(1, -1);
        assertEquals(b, a.rot(r.phi()));
    }

    public void testRot04() {
        Vec2D a = new Vec2D(102.4, 77.5);
        Vec2D r = new Vec2D(0, 1);
        Vec2D b = new Vec2D(77.5, -102.4);
        assertEquals(b, a.rot(r.phi()));
    }

    public void testRot05() {
        Vec2D a = new Vec2D(0, 1);
        Vec2D r = new Vec2D(-1, 0);
        Vec2D b = new Vec2D(0, -1);
        assertEquals(b, a.rot(r.phi()));
    }

    public void testRot06() {
        Vec2D a = new Vec2D(1, 0);
        Vec2D r = new Vec2D(-1, 0);
        Vec2D b = new Vec2D(-1, 0);
        assertEquals(b, a.rot(r.phi()));
    }

    public void testRot07() {
        Vec2D a = new Vec2D(1, 0);
        Vec2D r = new Vec2D(-1, 0);
        Vec2D b = new Vec2D(-1, 0);
        assertEquals(b, a.rotInternal(r.phi()));
    }

    public void testTrans00() {
        Vec2D a = new Vec2D(-1, 10);
        Vec2D o = new Vec2D(4, 5);
        Vec2D r = new Vec2D(1, 2);
        Vec2D b = new Vec2D(Math.sqrt(5), Math.sqrt(45));
        assertEquals(b, a.trans(o, r.phi()));
    }

    public void testTrans01() {
        Vec2D a = new Vec2D(3, -2);
        Vec2D o = new Vec2D(4, 5);
        Vec2D r = new Vec2D(1, 2);
        Vec2D b = new Vec2D(-Math.sqrt(45), -Math.sqrt(5));
        assertEquals(b, a.trans(o, r.phi()));
    }

    public void testTrans02() {
        Vec2D a = new Vec2D(-7, 0);
        Vec2D o = new Vec2D(-6, 3);
        Vec2D r = new Vec2D(-1, 1);
        Vec2D b = new Vec2D(-Math.sqrt(2), Math.sqrt(8));
        assertEquals(b, a.trans(o, r.phi()));
    }

    public void testTrans03() {
        Vec2D a = new Vec2D(-3, -3);
        Vec2D o = new Vec2D(1, -5);
        Vec2D r = new Vec2D(-1, 3);
        Vec2D b = new Vec2D(Math.sqrt(10), Math.sqrt(10));
        assertEquals(b, a.trans(o, r.phi()));
    }

    public void testTrans04() {
        Vec2D a = new Vec2D(5, -7);
        Vec2D o = new Vec2D(1, -5);
        Vec2D r = new Vec2D(-1, 3);
        Vec2D b = new Vec2D(-Math.sqrt(10), -Math.sqrt(10));
        assertEquals(b, a.trans(o, r.phi()));
    }

    public void testUnit00() {
        Vec2D a = new Vec2D(1, 0);
        Vec2D u = new Vec2D(1, 0);
        assertEquals(u, a.unit());
    }

    public void testUnit01() {
        Vec2D a = new Vec2D(0, 1);
        Vec2D u = new Vec2D(0, 1);
        assertEquals(u, a.unit());
    }

    public void testUnit02() {
        Vec2D a = new Vec2D(0, -1);
        Vec2D u = new Vec2D(0, -1);
        assertEquals(u, a.unit());
    }

    public void testUnit03() {
        Vec2D a = new Vec2D(1, 1);
        Vec2D u = new Vec2D(1 / S2, 1 / S2);
        assertEquals(u, a.unit());
    }

    public void testUnit04() {
        Vec2D a = new Vec2D(0, -34.5666);
        Vec2D u = new Vec2D(0, -1);
        assertEquals(u, a.unit());
    }

    public void testPhi00() {
        assertEquals(0.0, new Vec2D(1, 0).phi(), 0.00001);
    }

    public void testPhi01() {
        assertEquals(Vec2D.degToRad(45.0), new Vec2D(1.0, 1.0).phi(), 0.00001);
    }

    public void testPhi02() {
        assertEquals(Vec2D.degToRad(-45.0), new Vec2D(1, -1).phi(), 0.00001);
    }

    public void testPhi03() {
        assertEquals(Vec2D.degToRad(60.0), new Vec2D(1, Math.sqrt(3)).phi(),
                0.00001);
    }

    public void testPhi04() {
        assertEquals(Vec2D.degToRad(120.0), new Vec2D(-1, Math.sqrt(3)).phi(),
                0.00001);
    }

    public void testCreateWithAngle00() {
        Vec2D v = new Vec2D(Vec2D.degToRad(35.0));
        assertEquals(Vec2D.degToRad(35.0), v.phi(), 0.0001);
    }

    public void testCreateWithAngle01() {
        Vec2D v = new Vec2D(Vec2D.degToRad(135.0));
        assertEquals(Vec2D.degToRad(135.0), v.phi(), 0.0001);
    }

    public void testCreateWithAngle02() {
        Vec2D v = new Vec2D(Vec2D.degToRad(-135.0));
        assertEquals(Vec2D.degToRad(-135.0 + 360.0), v.phi(), 0.0001);
    }

    public void testCreateWithAngle03() {
        Vec2D v = new Vec2D(Vec2D.degToRad(-180.0));
        assertEquals(Vec2D.degToRad(180.0), v.phi(), 0.0001);
    }

    public void testMul() {
        assertEquals("a", new Vec2D(8, 12), new Vec2D(4, 6).mul(2));
        assertEquals("b", new Vec2D(-6, 4), new Vec2D(-3, 2).mul(2));
        assertEquals("c", new Vec2D(6, -4), new Vec2D(-3, 2).mul(-2));
    }

    public void testSubForKick() {
        Vec2D v1 = new Vec2D(0, 0);
        Vec2D v2 = new Vec2D(1, 0);
        assertTrue("forKick a", v1.sub(v2).length() >= 0.7);
        assertTrue("forKick b", v2.sub(v1).length() >= 0.7);
    }

    public void testIsNorthOfHorizontalLine00() {
        Vec2D v = new Vec2D(10, 30);
        assertTrue("", !v.isNorthOfHorizontalLine(31));
    }

    public void testIsNorthOfHorizontalLine01() {
        Vec2D v = new Vec2D(10, 30);
        assertTrue("", v.isNorthOfHorizontalLine(29));
    }

    public void testIsNorthOfHorizontalLine02() {
        Vec2D v = new Vec2D(10, -30.5);
        assertTrue("", !v.isNorthOfHorizontalLine(23));
    }

    public void testIsNorthOfHorizontalLine03() {
        Vec2D v = new Vec2D(10, -30.5);
        assertTrue("", !v.isNorthOfHorizontalLine(-30));
    }

    public void testIsNorthOfHorizontalLine04() {
        Vec2D v = new Vec2D(10, -30.5);
        assertTrue("", v.isNorthOfHorizontalLine(-31));
    }

    public void testIsNorthOfHorizontalLine05() {
        Vec2D v = new Vec2D(10, -30.5);
        assertTrue("", !v.isNorthOfHorizontalLine(-30.5));
    }

    public void testIsNorthOfHorizontalLine06() {
        Vec2D v = new Vec2D(10, 30.5);
        assertTrue("", !v.isNorthOfHorizontalLine(31.5));
    }

    public void testIsSouthOfHorizontalLine00() {
        Vec2D v = new Vec2D(11, 0);
        assertTrue("", !v.isSouthOfHorizontalLine(-31));
    }

    public void testIsSouthOfHorizontalLine01() {
        Vec2D v = new Vec2D(11, 0);
        assertTrue("", v.isSouthOfHorizontalLine(1));
    }

    public void testIsSouthOfHorizontalLine02() {
        Vec2D v = new Vec2D(11, -10);
        assertTrue("", !v.isSouthOfHorizontalLine(-11));
    }

    public void testIsSouthOfHorizontalLine03() {
        Vec2D v = new Vec2D(11, -10);
        assertTrue("", v.isSouthOfHorizontalLine(10));
    }

    public void testIsSouthOfHorizontalLine04() {
        Vec2D v = new Vec2D(11, 100);
        assertTrue("", !v.isSouthOfHorizontalLine(99.999));
    }

    public void testIsSouthOfHorizontalLine05() {
        Vec2D v = new Vec2D(11, 100);
        assertTrue("", v.isSouthOfHorizontalLine(100.0001));
    }

    public void testIsSouthOfHorizontalLine06() {
        Vec2D v = new Vec2D(11, 12312.123123);
        assertTrue("", !v.isSouthOfHorizontalLine(0));
    }

    public void testIsSouthOfHorizontalLine07() {
        Vec2D v = new Vec2D(11, 10);
        assertTrue("", !v.isSouthOfHorizontalLine(10));
    }

    public void testIsSouthOfHorizontalLine08() {
        Vec2D v = new Vec2D(11, -10);
        assertTrue("", !v.isSouthOfHorizontalLine(-10));
    }

    public void testIsEastOfVerticalLine00() {
        Vec2D v = new Vec2D(-10, 0);
        assertTrue("Failure", v.isEastOfVerticalLine(-1000));
    }

    public void testIsEastOfVerticalLine01() {
        Vec2D v = new Vec2D(-100, 0);
        assertTrue("Failure", !v.isEastOfVerticalLine(-33));
    }

    public void testIsEastOfVerticalLine02() {
        Vec2D v = new Vec2D(0, 0);
        assertTrue("Failure", v.isEastOfVerticalLine(-1));
    }

    public void testIsEastOfVerticalLine03() {
        Vec2D v = new Vec2D(10, 0);
        assertTrue("Failure", v.isEastOfVerticalLine(0));
    }

    public void testIsEastOfVerticalLine04() {
        Vec2D v = new Vec2D(-10, 0);
        assertTrue("Failure", !v.isEastOfVerticalLine(0));
    }

    public void testIsEastOfVerticalLine05() {
        Vec2D v = new Vec2D(99, 0);
        assertTrue("Failure", !v.isEastOfVerticalLine(100));
    }

    public void testIsEastOfVerticalLine06() {
        Vec2D v = new Vec2D(99, 0);
        assertTrue("Failure", !v.isEastOfVerticalLine(99));
    }

    public void testIsEastOfVerticalLine07() {
        Vec2D v = new Vec2D(-99, 0);
        assertTrue("Failure", !v.isEastOfVerticalLine(-99));
    }

    public void testIsWestOfVerticalLine00() {
        Vec2D v = new Vec2D(-10, 0);
        assertTrue("Failure", v.isWestOfVerticalLine(0));
    }

    public void testIsWestOfVerticalLine01() {
        Vec2D v = new Vec2D(-10, 0);
        assertTrue("Failure", v.isWestOfVerticalLine(-9.999));
    }

    public void testIsWestOfVerticalLine02() {
        Vec2D v = new Vec2D(0, 0);
        assertTrue("Failure", v.isWestOfVerticalLine(0.0001));
    }

    public void testIsWestOfVerticalLine03() {
        Vec2D v = new Vec2D(10, 0);
        assertTrue("Failure", !v.isWestOfVerticalLine(0));
    }

    public void testIsWestOfVerticalLine04() {
        Vec2D v = new Vec2D(10, 0);
        assertTrue("Failure", !v.isWestOfVerticalLine(-100));
    }

    public void testIsWestOfVerticalLine05() {
        Vec2D v = new Vec2D(10, 0);
        assertTrue("Failure", !v.isWestOfVerticalLine(10));
    }

    public void testIsWestOfVerticalLine06() {
        Vec2D v = new Vec2D(-10, 0);
        assertTrue("Failure", !v.isWestOfVerticalLine(-10));
    }

    public void testIsNorthOfPoint0() {
        Vec2D v = new Vec2D(0, 10);
        assertTrue("Failure", v.isNorthOfPoint(new Vec2D(0, 0)));
    }

    public void testIsNorthOfPoint1() {
        Vec2D v = new Vec2D(0, -10);
        assertTrue("Failure", v.isNorthOfPoint(new Vec2D(0, -100)));
    }

    public void testIsNorthOfPoint2() {
        Vec2D v = new Vec2D(0, 10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(0, 999.9)));
    }

    public void testIsNorthOfPoint3() {
        Vec2D v = new Vec2D(0, 10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(0, 11)));
    }

    public void testIsNorthOfPoint4() {
        Vec2D v = new Vec2D(0, -10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(0, -9)));
    }

    public void testIsNorthOfPoint5() {
        Vec2D v = new Vec2D(0, 10);
        assertTrue("Failure", v.isNorthOfPoint(new Vec2D(0, 0)));
    }

    public void testIsNorthOfPoint6() {
        Vec2D v = new Vec2D(1, -10);
        assertTrue("Failure", v.isNorthOfPoint(new Vec2D(10, -100)));
    }

    public void testIsNorthOfPoint7() {
        Vec2D v = new Vec2D(0, 10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(-4, 999.9)));
    }

    public void testIsNorthOfPoint8() {
        Vec2D v = new Vec2D(6, 10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(0, 11)));
    }

    public void testIsNorthOfPoint9() {
        Vec2D v = new Vec2D(5, -10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(-4, -9)));
    }

    public void testIsNorthOfPoint10() {
        Vec2D v = new Vec2D(5, -10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(-4, -10)));
    }

    public void testIsNorthOfPoint11() {
        Vec2D v = new Vec2D(5, 10);
        assertTrue("Failure", !v.isNorthOfPoint(new Vec2D(-4, 10)));
    }

    public void testIsSouthOfPoint1() {
        Vec2D v = new Vec2D(5, -4);
        assertTrue("Failure", v.isSouthOfPoint(new Vec2D(-4, 3.99)));
    }

    public void testIsSouthOfPoint2() {
        Vec2D v = new Vec2D(5, 23423.4);
        assertTrue("Failure", !v.isSouthOfPoint(new Vec2D(-4, -2323.3434345)));
    }

    public void testIsSouthOfPoint3() {
        Vec2D v = new Vec2D(5, 0);
        assertTrue("Failure", v.isSouthOfPoint(new Vec2D(-4, 10)));
    }

    public void testIsSouthOfPoint4() {
        Vec2D v = new Vec2D(5, -10);
        assertTrue("Failure", !v.isSouthOfPoint(new Vec2D(-4, -11)));
    }

    public void testIsSouthOfPoint5() {
        Vec2D v = new Vec2D(5, 10);
        assertTrue("Failure", !v.isSouthOfPoint(new Vec2D(-4, 9)));
    }

    public void testIsSouthOfPoint6() {
        Vec2D v = new Vec2D(5, 11);
        assertTrue("Failure", !v.isSouthOfPoint(new Vec2D(-4, -11)));
    }

    public void testIsSouthOfPoint7() {
        Vec2D v = new Vec2D(5, 11);
        assertTrue("Failure", !v.isSouthOfPoint(new Vec2D(-4, 11)));
    }

    public void testIsSouthOfPoint8() {
        Vec2D v = new Vec2D(5, -11);
        assertTrue("Failure", !v.isSouthOfPoint(new Vec2D(-4, -11)));
    }

    public void testIsEastOfPoint1() {
        Vec2D v = new Vec2D(5, 11);
        assertTrue("Failure", v.isEastOfPoint(new Vec2D(-4, -11)));
    }

    public void testIsEastOfPoint2() {
        Vec2D v = new Vec2D(-5, 11);
        assertTrue("Failure", !v.isEastOfPoint(new Vec2D(-4, -11)));
    }

    public void testIsEastOfPoint3() {
        Vec2D v = new Vec2D(5, 11);
        assertTrue("Failure", v.isEastOfPoint(new Vec2D(4, 11)));
    }

    public void testIsEastOfPoint4() {
        Vec2D v = new Vec2D(-5, -10);
        assertTrue("Failure", !v.isEastOfPoint(new Vec2D(-5, -11)));
    }

    public void testIsWestOfPoint1() {
        Vec2D v = new Vec2D(5, 11);
        assertTrue("Failure", !v.isWestOfPoint(new Vec2D(4, 211)));
    }

    public void testIsWestOfPoint2() {
        Vec2D v = new Vec2D(-5, -10);
        assertTrue("Failure", !v.isWestOfPoint(new Vec2D(-5, -11)));
    }

    public void testIsConnectionLineNorthOfPoint00() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, 1);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint01() {
        Vec2D point = new Vec2D(1, 0);
        Vec2D conn = new Vec2D(-1, 1);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint02() {
        Vec2D point = new Vec2D(1, 0);
        Vec2D conn = new Vec2D(-1, 1);
        Vec2D other = new Vec2D(0, 1);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint03() {
        Vec2D point = new Vec2D(1, -1);
        Vec2D conn = new Vec2D(-1, 0.5);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint04() {
        Vec2D point = new Vec2D(1, -1);
        Vec2D conn = new Vec2D(-1, 1.5);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint05() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -0.5);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint06() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -1.5);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint07() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(2, 1.5);
        Vec2D other = new Vec2D(0, 0);
        try {
            point.isConnectionLineNorthOfPoint(conn, other);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // Nothing to be done
        }
    }

    public void testIsConnectionLineNorthOfPoint08() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(2, 2.5);
        Vec2D other = new Vec2D(0, 0);
        try {
            point.isConnectionLineNorthOfPoint(conn, other);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // Nothing to be done
        }
    }

    public void testIsConnectionLineNorthOfPoint09() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -0.5);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint10() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -1.5);
        Vec2D other = new Vec2D(0, 0);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint11() {
        Vec2D point = new Vec2D(-1, 1);
        Vec2D conn = new Vec2D(-2, 1.5);
        Vec2D other = new Vec2D(0, 0);
        try {
            point.isConnectionLineNorthOfPoint(conn, other);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // Nothing to be done
        }
    }

    public void testIsConnectionLineNorthOfPoint12() {
        Vec2D point = new Vec2D(-1, 1);
        Vec2D conn = new Vec2D(-2, 2.5);
        Vec2D other = new Vec2D(0, 0);
        try {
            point.isConnectionLineNorthOfPoint(conn, other);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // Nothing to be done
        }
    }

    public void testIsConnectionLineNorthOfPoint13() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -1);
        Vec2D other = new Vec2D(0, -10);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint14() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -1);
        Vec2D other = new Vec2D(0, 10);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint15() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -1);
        Vec2D other = new Vec2D(-0.01, 0);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint16() {
        Vec2D point = new Vec2D(1, 1);
        Vec2D conn = new Vec2D(-1, -1);
        Vec2D other = new Vec2D(0.01, 0);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint17() {
        Vec2D point = new Vec2D(-1, -1);
        Vec2D conn = new Vec2D(1, 1);
        Vec2D other = new Vec2D(0.01, 0);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint18() {
        Vec2D point = new Vec2D(-1, -1);
        Vec2D conn = new Vec2D(1, 1);
        Vec2D other = new Vec2D(-0.01, 0);
        assertTrue("Failure", point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint19() {
        Vec2D point = new Vec2D(-1, -1);
        Vec2D conn = new Vec2D(1, 1);
        Vec2D other = new Vec2D(-0.01, 10);
        assertTrue("Failure", !point.isConnectionLineNorthOfPoint(conn, other));
    }

    public void testIsConnectionLineNorthOfPoint20() {
        Vec2D point = new Vec2D(-1, -1);
        Vec2D conn = new Vec2D(1, 1);
        Vec2D other = new Vec2D(5, 5.01);
        try {
            point.isConnectionLineNorthOfPoint(conn, other);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // Nothing to be done
        }
    }

    public static void main(String[] args) {
        TestRunner.run(new TestSuite(vsoc.Test2D.class));
    }
}