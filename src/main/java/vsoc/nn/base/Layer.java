package vsoc.nn.base;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A set of layer nodes.
 * 
 * @see LayerNode
 */

public class Layer implements Serializable {

    private static final long serialVersionUID = 0L;

    private List<LayerNode> lns;

    public Layer() {
        this.lns = new ArrayList<>();
    }

    public Layer(int size) {
        int i;

        this.lns = new ArrayList<>();
        for (i = 1; i <= size; i++) {
            this.addLayerNode(new LayerNode());
        }
    }

    public int size() {
        return this.lns.size();
    }

    public LayerNode layerNodeAt(int i) {
        return (LayerNode) this.lns.get(i);
    }

    public void addLayerNode(LayerNode ln) {
        this.lns.add(ln);
    }

    public void setValueAt(int i, short value) {
        this.layerNodeAt(i).setValue(value);
    }

    public short getValueAt(int i) {
        return this.layerNodeAt(i).getValue();
    }
    
    public double[] values() {
    	return this.lns.stream()
    			.map(ln -> ln.getValue())
    			.mapToDouble(s -> (double)s)
    			.toArray();
    }

    public void setValuesRandom(RandomValue rv) {
        LayerNode ln;
        Iterator<LayerNode> e;
        for (e = layerNodes(); e.hasNext();) {
            ln = (LayerNode) e.next();
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
        for (Iterator<LayerNode> e = layerNodes(); e.hasNext();) {
        	LayerNode ln = e.next();
            w.write(ln.toString());
        }
        w.write("]");
    }

    public void toValuesStream(Writer w) throws IOException {
        LayerNode ln;
        for (Iterator<LayerNode> e = layerNodes(); e.hasNext();) {
            ln = e.next();
            w.write(ln.getValue() + "\t");
        }
    }

    String valuesToString() {
        StringBuilder str = new StringBuilder();
        LayerNode ln;
        for (Iterator<LayerNode> e = layerNodes(); e.hasNext();) {
            ln = e.next();
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

    public Iterator<LayerNode> layerNodes() {
        return new LayerNodesOfLayerIterator(this);
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
        Iterator<LayerNode> lns1 = l.layerNodes();
        Iterator<LayerNode> thisLns = layerNodes();
        while (lns1.hasNext() && thisLns.hasNext() && equals) {
            LayerNode ln = lns1.next();
            LayerNode thisLn = thisLns.next();
            if (!ln.equalsInValue(thisLn))
                equals = false;
        }
        if (equals) {
            if (thisLns.hasNext() || lns1.hasNext())
                return false;
            return true;
        }
        return false;
    }

    class LayerNodesOfLayerIterator implements Iterator<LayerNode> {
        List<LayerNode> v;

        int i;
        int size;

        LayerNodesOfLayerIterator(Layer l) {
            this.v = l.lns;
            this.i = 0;
            this.size = this.v.size();
        }

        @Override
        public boolean hasNext() {
            return this.i < this.size;
        }

        @Override
        public LayerNode next() {
        	LayerNode o = this.v.get(this.i);
        	if (o == null) {
        		throw new NoSuchElementException();
        	}
            this.i++;
            return o;
        }
    }
}
