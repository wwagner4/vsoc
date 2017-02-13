package vsoc.camps.neuroevolution.goalkeeper;

import java.io.*;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.neuroevolution.goalgetter.GGCamp;
import vsoc.util.Serializer;

public class GoalgetterGenerator {

	private static Logger log = Logger.getLogger(GoalgetterGenerator.class);

	private GoalgetterGenerator() {
		super();
	}

	public static void main(String[] args) throws IOException {
		try(ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("gkcamp.xml")) {
			GGCamp camp = (GGCamp) ctx.getBean("ggCamp");
			while (!camp.isFinished()) {
				camp.takeOneStep();
			}
			String dirName = "src/main/resources";
			File outDir = new File(dirName);
			if (!outDir.exists()) {
				outDir.mkdirs();
				log.warn("Output direcory " + outDir + " did not exist. Was created.");
			}
			File file = new File(outDir, "ggcamp.ser");
			Serializer.current().serialize(camp, file);
			System.out.println("Created goalgetters in " + file);
		}
	}

}
