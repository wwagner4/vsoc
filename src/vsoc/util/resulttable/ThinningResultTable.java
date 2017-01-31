package vsoc.util.resulttable;

import vsoc.util.resulttable.util.Thinner;

public class ThinningResultTable extends SimpleResultTable {
    
    private int min = 50;
    
    private int max = 100;

    public ThinningResultTable() {
        super();
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void addNextSerialValue(Number sval) {
        if (this.rows.size() == this.max) {
            this.rows = Thinner.current().thin(this.rows, this.min);
        }
        super.addNextSerialValue(sval);
    }

}
