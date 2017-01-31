package vsoc.util.resulttable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Steight forward implementation of a result table. Not compressing.
 * 
 */
public class SimpleResultTable implements ResultTable {

    protected List columnDescs = null;

    protected ColumnDesc serialDesc = null;

    protected List rows = new ArrayList();

    public SimpleResultTable() {
        super();
    }

    public void addNextSerialValue(Number sval) {
        ResultTableRow row = new ResultTableRow();
        row.setSerialValue(sval);
        Iterator iter = this.columnDescs.iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = (ColumnDesc) iter.next();
            row.getResultValues().put(desc.getId(), null);
        }
        this.rows.add(row);
    }

    public void setValue(String id, Number val) {
        ResultTableRow row = getActualRow();
        if (row == null) {
            throw new InvalidStateException(
                    "No actual row defined. Probably you did not call addNextSerialValue() before.");
        }
        row.setResultValue(id, val);
    }

    private ResultTableRow getActualRow() {
        ResultTableRow re = null;
        int size = this.rows.size();
        if (size > 0) {
            re = (ResultTableRow) this.rows.get(size - 1);
        }
        return re;
    }

    public List getRows() {
        return this.rows;
    }

    public List getColumnDescs() {
        return this.columnDescs;
    }

    public void setColumnDescs(List columnDescs) {
        this.columnDescs = columnDescs;
    }

    public ColumnDesc getSerialDesc() {
        return this.serialDesc;
    }

    public void setSerialDesc(ColumnDesc serialDesc) {
        this.serialDesc = serialDesc;
    }

    public List getColumn(String id) {
        List re = new ArrayList();
        Iterator iter = this.rows.iterator();
        while (iter.hasNext()) {
            ResultTableRow row = (ResultTableRow) iter.next();
            re.add(row.getResultValue(id));
        }
        return re;
    }

    public List getSerial() {
        List re = new ArrayList();
        Iterator iter = this.rows.iterator();
        while (iter.hasNext()) {
            ResultTableRow row = (ResultTableRow) iter.next();
            re.add(row.getSerialValue());
        }
        return re;
    }

    public String currentRowAsNameValuePairs() {
        StringBuffer out = new StringBuffer();
        out.append("<");
        appendNameValue(out, this.serialDesc.getId(), this.serialDesc
                .getFormat().format(getActualRow().getSerialValue()));
        Iterator iter = this.columnDescs.iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = (ColumnDesc) iter.next();
            out.append("|");
            Number val = getActualRow().getResultValue(desc.getId());
            if (val == null) {
                appendNameValue(out, desc.getId(), "null");
            } else {
                appendNameValue(out, desc.getId(), desc.getFormat().format(val));
            }
        }
        out.append(">");
        return out.toString();
    }

    private void appendNameValue(StringBuffer out, String name, String value) {
        out.append(name);
        out.append("=");
        out.append(value);
    }

}
