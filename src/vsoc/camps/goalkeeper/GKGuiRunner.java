package vsoc.camps.goalkeeper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.Camp;
import vsoc.util.Serializer;
import vsoc.util.VsocUtil;
import vsoc.view.FieldFrame;

public class GKGuiRunner {

    private static final String PREFIX = "GKCAMP";

    public GKGuiRunner() {
        super();
    }

    public static void main(String[] args) {
        try {
            Camp camp = createCamp();
            Serializer.current().startScheduledSerialization(PREFIX, 600, camp);
            VsocUtil u = VsocUtil.current();
            String campProperties = u.propsToString(camp.getProperties());
            System.out.println(campProperties);
            FieldFrame.open(camp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Camp createCamp() {
        Camp camp = (Camp) Serializer.current()
                .deserializeFromScheduled(PREFIX);
        if (camp == null) {
            ApplicationContext ctx = new ClassPathXmlApplicationContext(
                    "gkcamp.xml");
            camp = (Camp) ctx.getBean("gkCamp");
        }
        return camp;
    }

}
