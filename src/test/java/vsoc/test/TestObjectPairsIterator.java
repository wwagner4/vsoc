package vsoc.test;

import junit.framework.*;
import junit.textui.*;
import vsoc.util.*;
import java.util.*;


public class TestObjectPairsIterator extends AbstractTest {

  public TestObjectPairsIterator(String name) {
    super(name);
  }
  public void test00 () {
    List l = new Vector();
    l.add("33");
    l.add("44");
    l.add("55");
    l.add("66");
    ObjectPairsIterator i = new ObjectPairsIterator(l);
    List resultSet = new Vector();
    resultSet.add("3344");
    resultSet.add("3355");
    resultSet.add("3366");
    resultSet.add("4455");
    resultSet.add("4466");
    resultSet.add("5566");
    assertTrue("has 1 pair", i.hasNext());
    Object[] p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has 2 pair", i.hasNext());
    p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has 3 pair", i.hasNext());
    p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has 4 pair", i.hasNext());
    p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has 5 pair", i.hasNext());
    p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has 6 pair", i.hasNext());
    p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has not 7 pair", !i.hasNext());
    assertTrue("resultSet empty", resultSet.isEmpty());
  }
  public void test01 () {
    List l = new Vector();
    l.add("33");
    l.add("44");
    l.add("55");
    ObjectPairsIterator i = new ObjectPairsIterator(l);
    List resultSet = new Vector();
    resultSet.add("3344");
    resultSet.add("3355");
    resultSet.add("4455");
    assertTrue("has 1 pair", i.hasNext());
    Object[] p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has 2 pair", i.hasNext());
    p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has 3 pair", i.hasNext());
    p = i.next();
    assertIncludes(resultSet, p);
    removeResult(resultSet, p);
    assertTrue("has not 4 pair", !i.hasNext());
    assertTrue("resultSet empty", resultSet.isEmpty());
  }
  private void assertIncludes(List resultSet, Object[] pair) {
    String s1 = (String)pair[0];
    String s2 = (String)pair[1];
    if (!resultSet.contains(s1+s2)) {
      if (!resultSet.contains(s2+s1)) {
        fail("resultSet contains no: "+s1+" "+s2);
      }
    }
  }
  private void removeResult(List resultSet, Object[] pair) {
    String s1 = (String)pair[0];
    String s2 = (String)pair[1];
    if (resultSet.contains(s1+s2)) {
      resultSet.remove(s1+s2);
    }
    else if (resultSet.contains(s2+s1)) {
      resultSet.remove(s2+s1);
    }
    else fail("could not remove: "+s1+" "+s2);
  }
  public static void main(String[] args) {
    TestRunner.run(new TestSuite(vsoc.test.TestObjectPairsIterator.class));
  }
}