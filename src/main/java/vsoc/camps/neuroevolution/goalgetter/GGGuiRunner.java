package vsoc.camps.neuroevolution.goalgetter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.*;
import vsoc.util.Serializer;
import vsoc.util.VsocUtil;
import vsoc.view.FieldFrame;

public class GGGuiRunner {

    private static final String PREFIX = "GGCAMP";

    public GGGuiRunner() {
        super();
    }

    public static void main(String[] args) {
        try {
        	Camp<?> camp = loadCamp();
            Serializer.current().startScheduledSerialization(PREFIX, 600, camp);
            VsocUtil u = VsocUtil.current();
            String campProperties = u.propsToString(camp.getProperties());
            System.out.println(campProperties);
            FieldFrame.open(camp, "GG camp");
        } catch (Exception e) {
            e.printStackTrace();
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
