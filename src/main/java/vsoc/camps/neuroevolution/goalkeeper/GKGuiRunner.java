package vsoc.camps.neuroevolution.goalkeeper;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vsoc.camps.*;
import vsoc.util.*;
import vsoc.view.FieldFrame;

public class GKGuiRunner {

	private static final String PREFIX = "GKCAMP";

	private GKGuiRunner() {
		super();
	}

	public static void main(String[] args) throws IOException {
		Camp<?> camp = createCamp();
		Serializer.current().startScheduledSerialization(PREFIX, 600, camp);
		VsocUtil u = VsocUtil.current();
		String campProperties = u.propsToString(camp.getProperties());
		System.out.println(campProperties);
		FieldFrame.open(camp, "GK camp");
	}

	private static Camp<?> createCamp() {
		Camp<?> camp = (Camp<?>) Serializer.current().deserializeFromScheduled(PREFIX);
		if (camp == null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("gkcamp.xml");
			camp = (Camp<?>) ctx.getBean("gkCamp");
		}
		return camp;
	}

}
