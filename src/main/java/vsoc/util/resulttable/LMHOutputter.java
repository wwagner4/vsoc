package vsoc.util.resulttable;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;

/**
 * Creates a tab seperated output to be used in MS-Excel. See
 * results/ChartTemplate.xls
 */
public class LMHOutputter implements ResultTableOutputter {

    private static final char SEPERATOR = '\t';

    private ResultTable table;

    public LMHOutputter() {
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
        Iterator<ColumnDesc> descs = this.table.getColumnDescs().iterator();
        while (descs.hasNext()) {
            ColumnDesc desc = (ColumnDesc) descs.next();
            outputColumn(pw, desc);
        }
        pw.println();
    }

    private void outputColumn(PrintWriter pw, ColumnDesc desc) {
        ColumnDesc sdesc = this.table.getSerialDesc();
        pw.println(desc.getName());
        pw.print(sdesc.getId());
        pw.print(SEPERATOR);
        pw.print("L");
        pw.print(SEPERATOR);
        pw.print("H");
        pw.print(SEPERATOR);
        pw.print("M");
        pw.print(SEPERATOR);
        pw.println();
        Iterator<ResultTableRow> iter = this.table.getRows().iterator();
        while (iter.hasNext()) {
            ResultTableRow row = iter.next();
            Number val = row.getResultValue(desc.getId());
            pw.print(format(desc, row.getSerialValue()));
            pw.print(SEPERATOR);
            pw.print(format(desc, val));
            pw.print(SEPERATOR);
            pw.print(format(desc, val));
            pw.print(SEPERATOR);
            pw.print(format(desc, val));
            pw.print(SEPERATOR);
            pw.println();
        }
    }

    private String format(ColumnDesc desc, Number num) {
        String re = "";
        if (num != null) {
            re = desc.getFormat().format(num);
        }
        return re;
    }

}
