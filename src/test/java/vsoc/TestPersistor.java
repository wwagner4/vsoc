package vsoc;

import junit.framework.TestSuite;
import junit.textui.TestRunner;
import vsoc.nn.feedforward.FFNet;
import vsoc.util.Serializer;

/**
 * Testcases for Persistance
 */

public class TestPersistor extends AbstractTest {

    private Serializer ser = Serializer.current();
    
    public TestPersistor(String name) {
        super(name);
    }
    public void testPersB00() throws Exception {
        assertPersB("hallo", 10.0, 234);
    }
    public void testPersB01() throws Exception {
        assertPersB("", 10.0, 234);
    }
    public void testPersB02() throws Exception {
        assertPersB("       ", 10293847.0, -0);
    }
    public void testPersB03() throws Exception {
        assertPersB("h a l l o", 10.00000000123123, 0);
    }
    public void testPersB04() throws Exception {
        assertPersB("/\n\t", 0.000213, 223434);
    }
    public void testPersA00() throws Exception {
        assertPersB("hallo", 10.0, 234);
    }
    public void testPersA01() throws Exception {
        assertPersB("", 10.0, 234);
    }
    public void testPersA02() throws Exception {
        assertPersB("       ", 10293847.0, -0);
    }
    public void testPersA03() throws Exception {
        assertPersB("h a l l o", 10.00000000123123, 0);
    }
    public void testPersA04() throws Exception {
        assertPersB("/\n\t", 0.000213, 223434);
    }
    private void assertPersB(String str, double doubleVal, int intVal) throws Exception {
        PersB b = new PersB();
        b.stringValue = str;
        b.doubleValue = doubleVal;
        b.intValue = intVal;
        this.ser.serialize(b, TestUtil.tmp("test.object"));
        b = null;
        b = (PersB) this.ser.deserialize(TestUtil.tmp("test.object"));
        assertEquals(str, b.stringValue);
        assertEquals(doubleVal, b.doubleValue, 0.00001);
        assertEquals(intVal, b.intValue);
    }
    public void testPersANull() throws Exception {
        PersA a = new PersA();
        a.persB = null;
        this.ser.serialize(a, TestUtil.tmp("test.object"));
        a = null;
        a = (PersA) this.ser.deserialize(TestUtil.tmp("test.object"));
        assertTrue("b is null", a.persB == null);
    }
    public void testNet() throws Exception {
        FFNet n = new FFNet(new TestNetConnector());
        n.setParametersRandom(System.currentTimeMillis());
        this.ser.serialize(n, TestUtil.tmp("net.object"));
        Object p1 = this.ser.deserialize(TestUtil.tmp("net.object"));
        FFNet n1 = (FFNet) p1;
        assertTrue("equals in structure", n1.equalsInStructure(n));
        assertTrue("equals in weights", n1.equalsInWeights(n));
    }
    public static void main(String[] args) {
        TestRunner.run(new TestSuite(vsoc.TestPersistor.class));
    }
}
