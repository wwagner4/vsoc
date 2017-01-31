package vsoc.util.resulttable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Strait forward implementation of a result table. Not compressing.
 * 
 */
public class SimpleResultTable implements ResultTable {

	private static final long serialVersionUID = 1L;
	
    protected List<ColumnDesc> columnDescs = null;

    protected ColumnDesc serialDesc = null;

    protected List<ResultTableRow> rows = new ArrayList<>();

    public SimpleResultTable() {
        super();
    }

    public void addNextSerialValue(Number sval) {
        ResultTableRow row = new ResultTableRow();
        row.setSerialValue(sval);
        Iterator<ColumnDesc> iter = this.columnDescs.iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = iter.next();
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

    public List<ResultTableRow> getRows() {
        return this.rows;
    }

    public List<ColumnDesc> getColumnDescs() {
        return this.columnDescs;
    }

    public void setColumnDescs(List<ColumnDesc> columnDescs) {
        this.columnDescs = columnDescs;
    }

    public ColumnDesc getSerialDesc() {
        return this.serialDesc;
    }

    public void setSerialDesc(ColumnDesc serialDesc) {
        this.serialDesc = serialDesc;
    }

    public List<Number> getColumn(String id) {
        List<Number> re = new ArrayList<>();
        Iterator<ResultTableRow> iter = this.rows.iterator();
        while (iter.hasNext()) {
            ResultTableRow row = iter.next();
            re.add(row.getResultValue(id));
        }
        return re;
    }

    public List<Number> getSerial() {
        List<Number> re = new ArrayList<>();
        Iterator<ResultTableRow> iter = this.rows.iterator();
        while (iter.hasNext()) {
            ResultTableRow row = iter.next();
            re.add(row.getSerialValue());
        }
        return re;
    }

    public String currentRowAsNameValuePairs() {
        StringBuilder out = new StringBuilder();
        out.append("<");
        ResultTableRow actualRow = getActualRow();
        if (actualRow != null) {
    		appendNameValue(out, this.serialDesc.getId(), this.serialDesc
                    .getFormat().format(actualRow.getSerialValue()));
            Iterator<ColumnDesc> iter = this.columnDescs.iterator();
            while (iter.hasNext()) {
                ColumnDesc desc = iter.next();
                out.append("|");
                Number val = actualRow.getResultValue(desc.getId());
                if (val == null) {
                    appendNameValue(out, desc.getId(), "null");
                } else {
                    appendNameValue(out, desc.getId(), desc.getFormat().format(val));
                }
            }
        }
        out.append(">");
        return out.toString();
    }

    private void appendNameValue(StringBuilder out, String name, String value) {
        out.append(name);
        out.append("=");
        out.append(value);
    }

}
