package vsoc.nn.base;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;

/**
 * A set of layer nodes.
 * 
 * @see LayerNode
 */

public class Layer implements Serializable {

    private static final long serialVersionUID = 0L;

    Vector<LayerNode> lns;

    public Layer() {
        this.lns = new Vector();
    }

    public Layer(int size) {
        int i;

        this.lns = new Vector();
        for (i = 1; i <= size; i++) {
            this.addLayerNode(new LayerNode());
        }
    }

    public int size() {
        return this.lns.size();
    }

    public LayerNode layerNodeAt(int i) {
        return (LayerNode) this.lns.elementAt(i);
    }

    public void addLayerNode(LayerNode ln) {
        this.lns.addElement(ln);
    }

    public void setValueAt(int i, short value) {
        LayerNode ln;
        ln = this.layerNodeAt(i);
        ln.setValue(value);
    }

    public short getValueAt(int i) {
        LayerNode ln;
        ln = this.layerNodeAt(i);
        return (ln.getValue());
    }

    public void setValuesRandom(RandomValue rv) {
        LayerNode ln;
        Enumeration<LayerNode> e;
        for (e = layerNodes(); e.hasMoreElements();) {
            ln = (LayerNode) e.nextElement();
            ln.setValueRandom(rv);
        }
    }

    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            toStream(sw);
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        return sw.toString();
    }

    public String toValuesString() {
        StringWriter sw = new StringWriter();
        try {
            toValuesStream(sw);
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        return sw.toString();
    }

    public void toStream(Writer w) throws IOException {
        Enumeration e;
        LayerNode ln;
        w.write("[");
        for (e = layerNodes(); e.hasMoreElements();) {
            ln = (LayerNode) e.nextElement();
            w.write(ln.toString());
        }
        w.write("]");
    }

    public void toValuesStream(Writer w) throws IOException {
        Enumeration e;
        LayerNode ln;
        for (e = layerNodes(); e.hasMoreElements();) {
            ln = (LayerNode) e.nextElement();
            w.write(ln.getValue() + "\t");
        }
    }

    String valuesToString() {
        String str;
        Enumeration e;
        LayerNode ln;
        str = "";
        for (e = layerNodes(); e.hasMoreElements();) {
            ln = (LayerNode) e.nextElement();
            str = str + ln.valueToString() + ";";
        }
        return str;
    }

    public void resetCalculated() {
        int i, size;
        LayerNode ln;
        size = size();
        for (i = 0; i < size; i++) {
            ln = layerNodeAt(i);
            ln.resetCalculated();
        }
    }

    public void calculate() {
        int i, size;
        LayerNode ln;
        size = size();
        for (i = 0; i < size; i++) {
            ln = layerNodeAt(i);
            if (!ln.isCalculated()) {
                ln.calculate();
            }
        }
    }

    public Enumeration<LayerNode> layerNodes() {
        return new EnumLayerNodesOfLayer(this);
    }

    public boolean equals(Object o) {
        return equalsInValues(o);
    }

    public boolean equalsInValues(Object o) {
        LayerNode ln, thisLn;
        Enumeration<LayerNode> lns, thisLns;
        boolean equals = true;
        Layer l;
        if (!(o instanceof Layer))
            return false;
        l = (Layer) o;
        lns = l.layerNodes();
        thisLns = layerNodes();
        while (lns.hasMoreElements() && thisLns.hasMoreElements() && equals) {
            ln = (LayerNode) lns.nextElement();
            thisLn = (LayerNode) thisLns.nextElement();
            if (!ln.equalsInValue(thisLn))
                equals = false;
        }
        if (equals == true) {
            if (thisLns.hasMoreElements() || lns.hasMoreElements())
                return false;
            return true;
        }
        return false;
    }

    // TODO Remove the Enum classes
    class EnumLayerNodesOfLayer implements Enumeration<LayerNode> {
        Vector<LayerNode> v;

        int i;
        int size;

        EnumLayerNodesOfLayer(Layer l) {
            this.v = l.lns;
            this.i = 0;
            this.size = this.v.size();
        }

        public boolean hasMoreElements() {
            return this.i < this.size;
        }

        public LayerNode nextElement() {
        	LayerNode o = this.v.elementAt(this.i);
            this.i++;
            return o;
        }
    }
}
