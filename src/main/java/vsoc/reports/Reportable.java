package vsoc.reports;

import java.util.Properties;

import vsoc.util.resulttable.ResultTable;

public interface Reportable {
    
    public ResultTable getResultTable();
    
    public Properties getProperties();

}
