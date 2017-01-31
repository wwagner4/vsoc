package vsoc.util.resulttable;

import java.io.Writer;

public interface ResultTableOutputter {

    public void setTable(ResultTable table);

    public void output(Writer writer);

}
