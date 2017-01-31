package vsoc.reports.velocity;

import java.io.BufferedWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import vsoc.reports.Report;
import vsoc.reports.Reportable;
import vsoc.reports.VsocReportException;
import vsoc.util.resulttable.ColumnDesc;
import vsoc.util.resulttable.ResultTable;
import vsoc.util.resulttable.ResultTableRow;

public class VelocityHtmlReport implements Report {

    private Writer outWriter;

    private String templateName;

    public VelocityHtmlReport() {
        super();
        try {
            Velocity.init();
        } catch (Throwable e) {
            throw new VsocReportException(
                    "Could not instantiate the VelocityHtmlReport because: "
                            + e.getMessage(), e);
        }
    }

    public Writer getOutWriter() {
        return this.outWriter;
    }

    public void setOutWriter(Writer outWriter) {
        this.outWriter = outWriter;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void build(Reportable reportable) {
        try {
            VelocityContext context = new VelocityContext();
            context.put("properties", properties(reportable.getProperties()));
            context.put("head", head(reportable.getResultTable()));
            context.put("rows", rows(reportable.getResultTable()));
            context.put("legend", legend(reportable.getResultTable()));
            Template templ = Velocity.getTemplate(this.templateName);
            BufferedWriter writer = writer = new BufferedWriter(this.outWriter);
            templ.merge(context, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new VsocReportException("Could not generate report because: "
                    + e.getMessage(), e);
        }
    }

    private Collection legend(ResultTable resultTable) {
        ArrayList re = new ArrayList();
        ColumnDesc sdesc = resultTable.getSerialDesc();
        ArrayList sprop = new ArrayList();
        sprop.add(sdesc.getId());
        sprop.add(sdesc.getName());
        re.add(sprop);
        Iterator iter = resultTable.getColumnDescs().iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = (ColumnDesc) iter.next();
            ArrayList prop = new ArrayList();
            prop.add(desc.getId());
            prop.add(desc.getName());
            re.add(prop);
        }
        return re;
    }

    private Collection head(ResultTable resultTable) {
        ArrayList re = new ArrayList();
        re.add(resultTable.getSerialDesc().getId());
        Iterator iter = resultTable.getColumnDescs().iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = (ColumnDesc) iter.next();
            re.add(desc.getId());
        }
        return re;
    }

    private Collection rows(ResultTable resultTable) {
        ArrayList re = new ArrayList();
        Iterator iter = resultTable.getRows().iterator();
        while (iter.hasNext()) {
            ResultTableRow row = (ResultTableRow) iter.next();
            re.add(row(row, resultTable.getColumnDescs()));
        }
        return re;
    }

    private Collection row(ResultTableRow row, Collection columnDescs) {
        ArrayList re = new ArrayList();
        re.add(row.getSerialValue());
        Iterator iter = columnDescs.iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = (ColumnDesc) iter.next();
            NumberFormat format = desc.getFormat();
            Number val = row.getResultValue(desc.getId());
            String str = "";
            if (val != null) {
                str = format.format(val.doubleValue());
            }
            re.add(str);
        }
        return re;
    }

    private Collection properties(Properties properties) {
        ArrayList re = new ArrayList();
        Iterator iter = properties.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            ArrayList prop = new ArrayList();
            prop.add(key);
            prop.add(properties.getProperty(key));
            re.add(prop);
        }
        return re;
    }

}
