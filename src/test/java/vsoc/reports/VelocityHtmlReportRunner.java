package vsoc.reports;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import vsoc.camps.goalgetter.GGCampResultTable;
import vsoc.camps.goalgetter.GGCampResultColumns;
import vsoc.reports.Reportable;
import vsoc.reports.velocity.VelocityHtmlReport;
import vsoc.util.resulttable.ResultTable;

public class VelocityHtmlReportRunner {
    
    private Random ran = new Random();

    public VelocityHtmlReportRunner() {
        super();
    }

    public static void main(String[] args) {
        try {
            VelocityHtmlReportRunner runner = new VelocityHtmlReportRunner();
            runner.run();
            System.out.println("finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException {
        VelocityHtmlReport report = new VelocityHtmlReport();
        report.setOutWriter(new FileWriter("reports/Report.html"));
        report.setTemplateName("reports/ReportTemplate.vl");
        report.build(createReportable());
    }

    private Reportable createReportable() {
        TestReportable re = new TestReportable();
        re.setProperties(createTestProperties());
        re.setResultTable(createTestResultTable());
        return re;
    }

    private ResultTable createTestResultTable() {
        GGCampResultTable re = new GGCampResultTable();
        for (int i=0; i<100; i++) {
            re.addNextSerialValue(new Integer(i));
            re.setValue(GGCampResultColumns.DIVERSITY.getName(), randomValue());
            re.setValue(GGCampResultColumns.GOALS.getName(), randomValue());
            if (this.ran.nextBoolean()) {
                re.setValue(GGCampResultColumns.OWNGOALS.getName(), randomValue());
            }
            re.setValue(GGCampResultColumns.KICKOUTS.getName(), randomValue());
            re.setValue(GGCampResultColumns.KICKS.getName(), randomValue());

        }
        return re;

    }

    private Double randomValue() {
        return new Double(this.ran.nextDouble() * 1000);
    }

    private Properties createTestProperties() {
        Properties re = new Properties();
        re.setProperty("Mutation Rate", "0.04");
        re.setProperty("Going", "1.1");
        re.setProperty("Name", "Test");
        re.setProperty("Desc", "Testdescription ");
        return re;
    }

}
