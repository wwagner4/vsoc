package vsoc.nn.feedforward;

import java.util.Random;

import junit.framework.TestSuite;
import junit.textui.TestRunner;
import vsoc.*;
import vsoc.nn.feedforward.FFNet;


public class TestNet extends AbstractTest {

  private Random ran = new Random();
public TestNet(String name) {
    super(name);
  }
  public void testDistance00 () {
    FFNet n1 = createTestNet();
    assertEquals ("dist n1 n1", 0.0, n1.distance(n1), 0.00001);
  }
  
  public void testDistance01 () {
    for (int i=0; i<100; i++) {
      FFNet n1 = createTestNet();
      n1.setParametersRandom(this.ran.nextLong());
      FFNet n2 = createTestNet();
      n2.setParametersRandom(this.ran.nextLong());
      assertTrue("distance <= 5.0", n1.distance(n2) > 5.0);
    }
  }
  public void xtestDistance02 () {
    for (int i=0; i<100; i++) {
      FFNet n1 = createTestNet();
      n1.setParametersRandom(this.ran.nextLong());
      FFNet n2 = createTestNet();
      n2.setParametersRandom(this.ran.nextLong());
      System.out.println(n1.distance(n2));
    }
  }
  public static void main(String[] args) {
    TestRunner.run(new TestSuite(vsoc.nn.feedforward.TestNet.class));
  }
  
  private FFNet createTestNet() {
	  AbstractFFNetConnector c = new vsoc.TestNetConnector();
	  FFNet n = new FFNet();
	  c.connectNet(n);
	  return n;
  }
  

}