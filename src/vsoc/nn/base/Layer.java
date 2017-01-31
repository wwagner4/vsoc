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
        this.lns = new Vector<>();
    }

    public Layer(int size) {
        int i;

        this.lns = new Vector<>();
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
        this.layerNodeAt(i).setValue(value);
    }

    public short getValueAt(int i) {
        return this.layerNodeAt(i).getValue();
    }

    public void setValuesRandom(RandomValue rv) {
        LayerNode ln;
        Enumeration<LayerNode> e;
        for (e = layerNodes(); e.hasMoreElements();) {
            ln = (LayerNode) e.nextElement();
            ln.setValueRandom(rv);
        }
    }

    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            toStream(sw);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return sw.toString();
    }

    public String toValuesString() {
        StringWriter sw = new StringWriter();
        try {
            toValuesStream(sw);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return sw.toString();
    }

    public void toStream(Writer w) throws IOException {
        w.write("[");
        for (Enumeration<LayerNode> e = layerNodes(); e.hasMoreElements();) {
        	LayerNode ln = e.nextElement();
            w.write(ln.toString());
        }
        w.write("]");
    }

    public void toValuesStream(Writer w) throws IOException {
        LayerNode ln;
        for (Enumeration<LayerNode> e = layerNodes(); e.hasMoreElements();) {
            ln = e.nextElement();
            w.write(ln.getValue() + "\t");
        }
    }

    String valuesToString() {
        StringBuilder str = new StringBuilder();
        LayerNode ln;
        for (Enumeration<LayerNode> e = layerNodes(); e.hasMoreElements();) {
            ln = e.nextElement();
            str.append(ln.valueToString() + ";");
        }
        return str.toString();
    }

    public void resetCalculated() {
        LayerNode ln;
        int size = size();
        for (int i = 0; i < size; i++) {
            ln = layerNodeAt(i);
            ln.resetCalculated();
        }
    }

    public void calculate() {
        LayerNode ln;
        int size = size();
        for (int i = 0; i < size; i++) {
            ln = layerNodeAt(i);
            if (!ln.isCalculated()) {
                ln.calculate();
            }
        }
    }

    public Enumeration<LayerNode> layerNodes() {
        return new EnumLayerNodesOfLayer(this);
    }

    @Override
    public boolean equals(Object o) {
        return equalsInValues(o);
    }
    
    @Override
    public int hashCode() {
    	return 0;
    }

    public boolean equalsInValues(Object o) {
        boolean equals = true;
        if (!(o instanceof Layer))
            return false;
        Layer l = (Layer) o;
        Enumeration<LayerNode> lns = l.layerNodes();
        Enumeration<LayerNode> thisLns = layerNodes();
        while (lns.hasMoreElements() && thisLns.hasMoreElements() && equals) {
            LayerNode ln = lns.nextElement();
            LayerNode thisLn = thisLns.nextElement();
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
