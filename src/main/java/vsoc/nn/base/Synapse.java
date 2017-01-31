package vsoc.nn.base;

import java.io.*;

public class Synapse implements Serializable {

    private static final long serialVersionUID = 0L;

    protected Weighter weighter;

    private short wgt;

    private LayerNode ln;

    public Synapse() {
        super();
    }

    public Synapse(Weighter wgt) {
        this.weighter = wgt;
    }

    public LayerNode getLayerNode() {
        return this.ln;
    }

    public void connectLayerNode(LayerNode ln) {
        this.ln = ln;
    }

    public LayerNode layerNode() {
        return this.ln;
    }

    public void setWeight(short wgt) {
        this.wgt = wgt;
    }

    public void setWeightRandom(RandomWgt rw) {
        setWeight(rw.nextValue());
    }

    public short getWeight() {
        return this.wgt;
    }

    int getWeightedCalculatedValue() {
        this.ln.calculate();
        return this.weighter.getWeightedValue(this.ln.getValue(), getWeight());
    }

    public String toString() {
        String str;
        if (this.ln == null) {
            str = "(." + this.wgt + ")";
        } else {
            str = "(*" + this.wgt + ")";
        }
        return str;
    }

    public boolean equals(Object o) {
        Synapse syn;

        if (!(o instanceof Synapse))
            return false;
        syn = (Synapse) o;
        if (syn.getWeight() == getWeight())
            return true;
        return false;
    }
}
