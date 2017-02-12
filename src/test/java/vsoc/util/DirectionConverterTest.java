package vsoc.util;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import vsoc.util.DirectionConverter;

/**
* Testcases.
*/
public class DirectionConverterTest extends TestCase {

    private DirectionConverter conv;
    private double pi = Math.PI;

    public DirectionConverterTest(String s) {
        super(s);
    }
    protected void setUp() {
        this.conv = new DirectionConverter(100);
    }
    public void testA() {
        assertEquals(this.conv.getA(0), 0);
        assertEquals(this.conv.getA(this.pi / 2), 100);
        assertEquals(this.conv.getA(this.pi), 0);
        assertEquals(this.conv.getA(3 * this.pi / 2), -100);
        assertEquals(this.conv.getA(2 * this.pi), 0);
        assertEquals(this.conv.getA(5 * this.pi / 2), 100);
        assertEquals(this.conv.getA(-this.pi), 0);
        assertEquals(this.conv.getA(1), 84);
        assertEquals(this.conv.getA(5), -96);
    }
    public void testB() {
        assertEquals(this.conv.getB(0), 100);
        assertEquals(this.conv.getB(this.pi), -100);
        assertEquals(this.conv.getB(5), 28);
        assertEquals(this.conv.getB(3), -99);
    }
    public static void main(String[] args) {
        TestRunner.run(new TestSuite(vsoc.util.DirectionConverterTest.class));
    }
}
