package vsoc.util;

import java.util.*;

/**
 * Vector of Integers and IntVector.
 * <p>
 * Allows you to build a tree of integer values.
 */

public class IntVector extends Vector<Object> {

	private static final long serialVersionUID = 1L;
	
    public void addElement(int i) {
        addElement(new Integer(i));
    }

    public void removeElement(int i) {
        removeElement(new Integer(i));
    }

    public void insertElementAt(int i, int index) {
        insertElementAt(new Integer(i), index);
    }

    public void setElementAt(int i, int index) {
        setElementAt(new Integer(i), index);
    }

    public int lastElementAt() {
        Integer io;
        io = (Integer) lastElement();
        return (io.intValue());
    }

    public int firstElementAt() {
        Integer io;
        io = (Integer) firstElement();
        return (io.intValue());
    }

    public int intAt(int index) {
        return ((Integer) elementAt(index)).intValue();
    }

    public IntVector intVectorAt(int index) {
        return (IntVector) elementAt(index);
    }

    /**
     * Returns true if all values in al subvectors are equal.
     */
    public boolean equals(Object o) {
        if (!(o instanceof IntVector))
            return false;
        IntVector iv;
        iv = (IntVector) o;
        if (iv.size() != size())
            return false;
        Enumeration<Object> enum1, enum2;
        Object elem1, elem2;
        enum1 = elements();
        enum2 = iv.elements();
        while (enum1.hasMoreElements()) {
            elem1 = enum1.nextElement();
            elem2 = enum2.nextElement();
            if ((elem1 instanceof Integer) && (elem2 instanceof Integer)) {
                int i1, i2;
                i1 = ((Integer) elem1).intValue();
                i2 = ((Integer) elem2).intValue();
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
