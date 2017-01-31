package vsoc.camps.goalgetter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.Camp;
import vsoc.reports.velocity.VelocityHtmlReport;
import vsoc.util.Serializer;

public class GGCampRunner {

    private static Logger log = Logger.getLogger(GGCampRunner.class);

    private String ts = initTimestamp();

    private Collection camps = null;

    public GGCampRunner() {
        super();
    }

    private String initTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        return df.format(now);
    }

    public static void main(String[] args) {
        try {
            ApplicationContext ctx = new ClassPathXmlApplicationContext(
                    "ggcamp.xml");
            GGCampRunner runner = (GGCampRunner) ctx.getBean("campRunner");
            runner.run();
            System.out.println("- F I N I S H E D -");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        log.info("started");
        int num = 0;
        Iterator iter = this.camps.iterator();
        while (iter.hasNext()) {
            GGCamp camp = (GGCamp) iter.next();
            run(camp, num);
            num++;
        }
    }

    private void run(GGCamp camp, int num) throws IOException {
        log.info("started GGCamp " + num);
        while (!camp.isFinished()) {
            camp.takeOneStep();
        }
        File dir = getResultDir(num);
        serialize(camp, dir);
        report(camp, dir);
    }

    private void report(Camp camp, File dir) throws IOException {
        VelocityHtmlReport report = new VelocityHtmlReport();
        File file = new File(dir, "report.html");
        report.setOutWriter(new FileWriter(file));
        report.setTemplateName("reports/ReportTemplate.vl");
        FileUtils.copyFileToDirectory(new File("reports/Report.css"), dir);
        report.build(camp);
    }

    private void serialize(GGCamp camp, File dir) throws IOException {
        File file = new File(dir, "ggcamp.ser");
        Serializer.current().serialize(camp, file);
        log.info("saved ggcamp to " + file);
    }

    private File getResultDir(int num) {
        File allResultsDir = new File("results/ggcamprunner");
        if (!allResultsDir.exists()) {
            allResultsDir.mkdirs();
        }

        File thisResultsDir = new File(allResultsDir, this.ts + "/" + num);
        if (!thisResultsDir.exists()) {
            thisResultsDir.mkdirs();
        }
        return thisResultsDir;
    }

    public Collection getCamps() {
        return this.camps;
    }

    public void setCamps(Collection camps) {
        this.camps = camps;
    }

}
