package vsoc.camps.neuroevolution.goalgetter;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.*;
import vsoc.server.gui.FieldFrame;
import vsoc.util.Serializer;
import vsoc.util.VsocUtil;

public class GGGuiRunner {

	private static final Logger log = Logger.getLogger(AbstractCamp.class);
	
	private static final String PREFIX = "GGCAMP";

    private GGGuiRunner() {
        super();
    }

    public static void main(String[] args) {
        try {
        	Camp<?> camp = loadCamp();
            Serializer.current().startScheduledSerialization(PREFIX, 600, camp);
            VsocUtil u = VsocUtil.current();
            String campProperties = u.propsToString(camp.getProperties());
            log.info("\n" + campProperties);
            FieldFrame.open(camp, "GG camp");
        } catch (Exception e) {
        	log.error("Could not run 'GGGuiRunner'. " + e.getMessage(), e);
        }
    }

	private static Camp<?> loadCamp() {
    	Camp<?> camp = (Camp<?>) Serializer.current()
                .deserializeFromScheduled(PREFIX);
        if (camp == null) {
            ApplicationContext ctx = new ClassPathXmlApplicationContext(
                    "ggcamp.xml");
            camp = (Camp<?>) ctx.getBean("camp1");
        }
        return camp;
    }

}
