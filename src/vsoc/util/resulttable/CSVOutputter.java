package vsoc.util.resulttable;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;

/**
 * Creates a tab seperated output to be used in MS-Excel. See
 * results/ChartTemplate.xls
 */
public class CSVOutputter implements ResultTableOutputter {

    private char separator = ';';

    private ResultTable table;

    public CSVOutputter() {
        super();
    }

    public ResultTable getTable() {
        return this.table;
    }

    public void setTable(ResultTable table) {
        this.table = table;
    }

    public void output(Writer writer) {
        PrintWriter pw = new PrintWriter(writer);
        output(pw);
        pw.flush();
    }

    private void output(PrintWriter pw) {
        outputHeader(pw);
        Iterator iter = this.table.getRows().iterator();
        while (iter.hasNext()) {
            ResultTableRow row = (ResultTableRow) iter.next();
            outputRow(pw, row);
        }
    }

    private void outputRow(PrintWriter pw, ResultTableRow row) {
        ColumnDesc sdesc = this.table.getSerialDesc();
        Number sval = row.getSerialValue();
        pw.print(sdesc.getFormat().format(sval));
        pw.print(this.separator);
        Iterator descs = this.table.getColumnDescs().iterator();
        while (descs.hasNext()) {
            ColumnDesc desc = (ColumnDesc) descs.next();
            Number val = row.getResultValue(desc.getId());
            pw.print(desc.getFormat().format(val));
            pw.print(this.separator);
        }
        pw.println();
    }

    private void outputHeader(PrintWriter pw) {
        ColumnDesc sdesc = this.table.getSerialDesc();
        pw.print(sdesc.getId());
        pw.print(this.separator);
        Iterator descs = this.table.getColumnDescs().iterator();
        while (descs.hasNext()) {
            ColumnDesc desc = (ColumnDesc) descs.next();
            pw.print(desc.getId());
            pw.print(this.separator);
        }
        pw.println();
    }

    public char getSeparator() {
        return this.separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

}
