package vsoc.camps.neuroevolution.goalgetter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.util.Serializer;

public class GGCampRunner {

	private static Logger log = Logger.getLogger(GGCampRunner.class);

	private String ts = initTimestamp();

	private Collection<GGCamp> camps = null;

	public GGCampRunner() {
		super();
	}

	private String initTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		return df.format(now);
	}

	public static void main(String[] args) throws IOException {
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("ggcamp.xml")) {
			GGCampRunner runner = (GGCampRunner) ctx.getBean("campRunner");
			runner.run();
			log.info("- F I N I S H E D -");
		}
	}

	public void run() throws IOException {
		log.info("started");
		int num = 0;
		Iterator<GGCamp> iter = this.camps.iterator();
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

	public Collection<GGCamp> getCamps() {
		return this.camps;
	}

	public void setCamps(Collection<GGCamp> camps) {
		this.camps = camps;
	}

}
