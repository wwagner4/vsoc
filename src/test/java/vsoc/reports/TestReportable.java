package vsoc.reports;

import java.util.Properties;

import vsoc.reports.Reportable;
import vsoc.util.resulttable.ResultTable;

public class TestReportable implements Reportable {

    private ResultTable resultTable;

    private Properties properties;

    public TestReportable() {
        super();
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public ResultTable getResultTable() {
        return this.resultTable;
    }

    public void setResultTable(ResultTable restultTable) {
        this.resultTable = restultTable;
    }


}
