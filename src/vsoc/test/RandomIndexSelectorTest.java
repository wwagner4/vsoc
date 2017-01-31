package vsoc.test;

import junit.framework.*;
import junit.textui.*;
import vsoc.VsocInvalidConfigurationException;
import vsoc.util.*;

public class RandomIndexSelectorTest extends TestCase {

  public RandomIndexSelectorTest(String name) {
    super(name);
  }

  public void testSize00 () {
    RandomIndexSelector sel = new RandomIndexSelector (0, 9, 4);
    assertTrue("has 1", sel.hasNext());
    sel.next();
    assertTrue("has 2", sel.hasNext());
    sel.next();
    assertTrue("has 3", sel.hasNext());
    sel.next();
    assertTrue("has 4", sel.hasNext());
    sel.next();
    assertTrue("has not 5", !sel.hasNext());
  }
  public void testSize01 () {
    RandomIndexSelector sel = new  RandomIndexSelector (0, 10, 3);
    assertTrue("has 1", sel.hasNext());
    sel.next();
    assertTrue("has 2", sel.hasNext());
    sel.next();
    assertTrue("has 3", sel.hasNext());
    sel.next();
    assertTrue("has not 4", !sel.hasNext());
  }
  public void testSize01a () {
    try {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 1, 3);
      fail("Error expected");
    } catch (VsocInvalidConfigurationException err) {
      assertEquals("count must always be bigger than (to-from) in RandomIndexSelector", 
        err.getMessage());
    }

  }
  public void testSize01b () {
    try {
      RandomIndexSelector sel = new  RandomIndexSelector (5, 1, 3);
      fail("Error expected");
    } catch (Error err) {
      assertEquals("to '1' must always be bigger than from '5' in RandomIndexSelector", 
        err.getMessage());
    }
  }
  public void testSize02 () {
    RandomIndexSelector sel = new  RandomIndexSelector (0, 6, 6);
    assertTrue("has 1", sel.hasNext());
    sel.next();
    assertTrue("has 2", sel.hasNext());
    sel.next();
    assertTrue("has 3", sel.hasNext());
    sel.next();
    assertTrue("has 4", sel.hasNext());
    sel.next();
    assertTrue("has 5", sel.hasNext());
    sel.next();
    assertTrue("has 6", sel.hasNext());
    sel.next();
    assertTrue("has not 7", !sel.hasNext());
  }
  public void testAllSmallerMax () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      assertTrue("1", sel.next() < 5);
      assertTrue("2", sel.next() < 5);
      assertTrue("3", sel.next() < 5);
    }
  }
  public void testAllGreaterEqual0 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      assertTrue("1", sel.next() >= 0);
      assertTrue("2", sel.next() >= 0);
      assertTrue("3", sel.next() >= 0);
    }
  }
  public void testIncludes0 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      if (sel.next() == 0) return;
      if (sel.next() == 0) return;
      if (sel.next() == 0) return;
    }
    fail("not included");
  }
  public void testIncludes1 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      if (sel.next() == 1) return;
      if (sel.next() == 1) return;
      if (sel.next() == 1) return;
    }
    fail("not included");
  }
  public void testIncludes2 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      if (sel.next() == 2) return;
      if (sel.next() == 2) return;
      if (sel.next() == 2) return;
    }
    fail("not included");
  }
  public void testIncludes3 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      if (sel.next() == 3) return;
      if (sel.next() == 3) return;
      if (sel.next() == 3) return;
    }
    fail("not included");
  }
  public void testIncludes4 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      if (sel.next() == 4) return;
      if (sel.next() == 4) return;
      if (sel.next() == 4) return;
    }
    fail("not included");
  }
  public void testDoublicates00 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      int a = sel.next();
      int b = sel.next();
      int c = sel.next();
      assertTrue("a,b", a != b);
      assertTrue("a,c", a != c);
      assertTrue("b,c", b != c);
    }
  }
  public void testIncludesBase00 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (2, 5, 3);
      if (sel.next() == 2) return;
      if (sel.next() == 2) return;
      if (sel.next() == 2) return;
    }
    fail("not included");
  }
  public void testIncludesBase01 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (3, 6, 3);
      if (sel.next() == 3) return;
      if (sel.next() == 3) return;
      if (sel.next() == 3) return;
    }
    fail("not included");
  }
  public void testIncludesBase02 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (3, 6, 3);
      if (sel.next() == 4) return;
      if (sel.next() == 4) return;
      if (sel.next() == 4) return;
    }
    fail("not included");
  }
  public void testIncludesBase03 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (0, 4, 3);
      if (sel.next() == 5) fail("includes 5");
      if (sel.next() == 5) fail("includes 5");
      if (sel.next() == 5) fail("includes 5");
    }
  }
  public void testIncludesBase04 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (2, 10, 3);
      if (sel.next() == 1) fail("includes 1");
      if (sel.next() == 1) fail("includes 1");
      if (sel.next() == 1) fail("includes 1");
    }
  }
  public void testIncludesBase05 () {
    for (int i=0; i<100; i++) {
      RandomIndexSelector sel = new  RandomIndexSelector (2, 10, 3);
      if (sel.next() == 0) fail("includes 0");
      if (sel.next() == 0) fail("includes 0");
      if (sel.next() == 0) fail("includes 0");
    }
  }
  public void testTested () {
    assertTrue("tested", true);
  }
  public static void main(String[] args) {
    TestRunner.run(new TestSuite(vsoc.test.RandomIndexSelectorTest.class));
  }
}
