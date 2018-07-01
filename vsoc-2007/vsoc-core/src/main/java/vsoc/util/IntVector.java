package vsoc.util;

import java.util.*;

/**
 * Vector of Integers and IntVector.
 * <p>
 * Allows you to build a tree of integer values.
 */
public class IntVector extends Vector<Object> {

	private static final long serialVersionUID = 1L;
	
    public int lastElementAt() {
        return (int) lastElement();
    }

    public int firstElementAt() {
        return (int) firstElement();
    }

    public int intAt(int index) {
        return (int) elementAt(index);
    }

    public IntVector intVectorAt(int index) {
        return (IntVector) elementAt(index);
    }

    /**
     * Returns true if all values in al subvectors are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntVector)) {
            return false;
        }
        IntVector iv;
        iv = (IntVector) o;
        if (iv.size() != size()) {
            return false;
        }
        Enumeration<Object> enum1 = elements();
        Enumeration<Object> enum2 = iv.elements();
        while (enum1.hasMoreElements()) {
            Object elem1 = enum1.nextElement();
            Object elem2 = enum2.nextElement();
            if ((elem1 instanceof Integer) && (elem2 instanceof Integer)) {
                int i1 = (int) elem1;
                int i2 = (int) elem2;
                if (i1 != i2)
                    return false;
            } else if (elem1 instanceof IntVector) {
                if (!elem1.equals(elem2))
                    return false;
            } else
                return false;
        }
        return true;
    }
    
    @Override
    public synchronized int hashCode() {
    	return 0;
    }
}
