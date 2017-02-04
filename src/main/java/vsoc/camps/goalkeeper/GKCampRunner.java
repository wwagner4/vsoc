package vsoc.camps.goalkeeper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.Camp;
import vsoc.reports.velocity.VelocityHtmlReport;
import vsoc.util.*;

/**
 * Goal keeper camp
 */
public class GKCampRunner {

    private static Logger log = Logger.getLogger(GKCampRunner.class);

    private String ts = initTimestamp();

    private Collection<GKCamp> camps = null;

    public GKCampRunner() {
        super();
    }

    private String initTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        return df.format(now);
    }

    public static void main(String[] args) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("gkcamp.xml");
		GKCampRunner runner = (GKCampRunner) ctx.getBean("runner");
		runner.run();
		log.info("- F I N I S H E D -");
    }

    public void run() throws IOException {
        log.info("started");
        int num = 0;
        Iterator<GKCamp> iter = this.camps.iterator();
        while (iter.hasNext()) {
            GKCamp camp = iter.next();
            run(camp, num);
            num++;
        }
    }

    private void run(GKCamp camp, int num) throws IOException {
        VsocUtil u = VsocUtil.current();
        String campProperties = u.propsToString(camp.getProperties());
        log.info("started GGCamp " + num + "\n" + campProperties);
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

    private void serialize(GKCamp camp, File dir) throws IOException {
        File file = new File(dir, "ggcamp.ser");
        Serializer.current().serialize(camp, file);
        log.info("saved ggcamp to " + file);
    }

    private File getResultDir(int num) {
        File allResultsDir = new File("results/gkcamprunner");
        if (!allResultsDir.exists()) {
            allResultsDir.mkdirs();
        }

        File thisResultsDir = new File(allResultsDir, this.ts + "/" + num);
        if (!thisResultsDir.exists()) {
            thisResultsDir.mkdirs();
        }
        return thisResultsDir;
    }

    public Collection<GKCamp> getCamps() {
        return this.camps;
    }

    public void setCamps(Collection<GKCamp> camps) {
        this.camps = camps;
    }

}
