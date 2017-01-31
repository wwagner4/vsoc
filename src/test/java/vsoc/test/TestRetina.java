package vsoc.test;

import junit.framework.*;
import junit.textui.*;
import vsoc.util.*;

public class TestRetina extends AbstractTest {

  public TestRetina(String name) {
    super(name);
  }
  public static void main(String[] args) {
    TestRunner.run(new TestSuite(vsoc.test.TestRetina.class));
  }
  public void test01 () {
    Retina r = new Retina();
    r.reset();
    addVision1(r);
    addVision2(r);
    addVision3(r);
    addVision4(r);
    assertEquals("A", 32, r.getA());
    assertEquals("B", 66, r.getB());
    assertEquals("C", 58, r.getC());
    assertEquals("D", 47, r.getD());
  }
  public void test02 () {
    Retina r = new Retina();
    r.reset();
    addVision2(r);
    addVision4(r);
    addVision6(r);
    addVision8(r);
    assertEquals("A", 53, r.getA());
    assertEquals("B", 76, r.getB());
    assertEquals("C", 29, r.getC());
    assertEquals("D", 99, r.getD());
  }
  public void test03 () {
    Retina r = new Retina();
    r.reset();
    addVision1(r);
    addVision2(r);
    addVision6(r);
    assertEquals("A", 75, r.getA());
    assertEquals("B", 87, r.getB());
    assertEquals("C", 11, r.getC());
    assertEquals("D", 0, r.getD());
  }
  public void test04 () {
    Retina r = new Retina();
    r.reset();
    addVision1(r);
    addVision2(r);
    addVision6(r);
    addVision9(r);
    assertEquals("A", 75, r.getA());
    assertEquals("B", 87, r.getB());
    assertEquals("C", 11, r.getC());
    assertEquals("D", 0, r.getD());
  }
  private void addVision1(Retina r) {
    addVision(r, 90, 30);
  }
  private void addVision2(Retina r) {
    addVision(r, 80, 11);
  }
  private void addVision3(Retina r) {
    addVision(r, 70, -3);
  }
  private void addVision4(Retina r) {
    addVision(r, 65, -33);
  }
  private void addVision6(Retina r) {
    addVision(r, 40, 22);
  }
  private void addVision8(Retina r) {
    addVision(r, 10, -44);
  }
  private void addVision9(Retina r) {
    addVision(r, 130, 30);
  }
  private void addVision (Retina r, int dist, double alpha) {
    r.addVision(dist, alpha);
  }
}