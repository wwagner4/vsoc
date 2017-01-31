package vsoc.camps.goalkeeper;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.goalgetter.GGCamp;
import vsoc.util.Serializer;

public class GoalgetterGenerator {

    private static Logger log = Logger.getLogger(GoalgetterGenerator.class);

    private GoalgetterGenerator() {
        super();
    }

    public static void main(String[] args) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("gkcamp.xml");
		GGCamp camp = (GGCamp) ctx.getBean("ggCamp");
		while (!camp.isFinished()) {
			camp.takeOneStep();
		}
		String dirName = "conf";
		File outDir = new File(dirName);
		if (!outDir.exists()) {
			outDir.mkdirs();
			log.warn("Output direcory " + outDir + " did not exist. Was created.");
		}
		File file = new File(outDir, "ggcamp.ser");
		Serializer.current().serialize(camp, file);
		System.out.println("Created goalgetters in " + file + ". Press any key to finish ....");
		System.in.read();
    }

}
