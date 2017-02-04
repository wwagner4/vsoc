package vsoc.reports.velocity;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.Velocity;

import vsoc.reports.*;
import vsoc.util.resulttable.*;

public class VelocityHtmlReport implements Report {

    private Writer outWriter;

    private String templateName;

    public VelocityHtmlReport() {
        super();
        try {
            Velocity.init();
        } catch (Exception e) {
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
            BufferedWriter writer = new BufferedWriter(this.outWriter);
            templ.merge(context, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new VsocReportException("Could not generate report because: "
                    + e.getMessage(), e);
        }
    }

    private Collection<List<Object>> legend(ResultTable resultTable) {
        ArrayList<List<Object>> re = new ArrayList<>();
        ColumnDesc sdesc = resultTable.getSerialDesc();
        ArrayList<Object> sprop = new ArrayList<>();
        sprop.add(sdesc.getId());
        sprop.add(sdesc.getName());
        re.add(sprop);
        Iterator<ColumnDesc> iter = resultTable.getColumnDescs().iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = (ColumnDesc) iter.next();
            ArrayList<Object> prop = new ArrayList<>();
            prop.add(desc.getId());
            prop.add(desc.getName());
            re.add(prop);
        }
        return re;
    }

    private Collection<String> head(ResultTable resultTable) {
        ArrayList<String> re = new ArrayList<>();
        re.add(resultTable.getSerialDesc().getId());
        Iterator<ColumnDesc> iter = resultTable.getColumnDescs().iterator();
        while (iter.hasNext()) {
            ColumnDesc desc = (ColumnDesc) iter.next();
            re.add(desc.getId());
        }
        return re;
    }

    private Collection<Object> rows(ResultTable resultTable) {
        ArrayList<Object> re = new ArrayList<>();
        Iterator<ResultTableRow> iter = resultTable.getRows().iterator();
        while (iter.hasNext()) {
            ResultTableRow row = iter.next();
            re.add(row(row, resultTable.getColumnDescs()));
        }
        return re;
    }

    private Collection<Object> row(ResultTableRow row, Collection<ColumnDesc> columnDescs) {
        ArrayList<Object> re = new ArrayList<>();
        re.add(row.getSerialValue());
        Iterator<ColumnDesc> iter = columnDescs.iterator();
        while (iter.hasNext()) {
            ColumnDesc desc =iter.next();
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

    private Collection<List<Object>> properties(Properties properties) {
        ArrayList<List<Object>> re = new ArrayList<>();
        Iterator<Object> iter = properties.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            ArrayList<Object> prop = new ArrayList<>();
            prop.add(key);
            prop.add(properties.getProperty(key));
            re.add(prop);
        }
        return re;
    }

}
