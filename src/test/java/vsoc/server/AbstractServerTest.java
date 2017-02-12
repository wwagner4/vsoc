package vsoc.server;

import atan.model.Controller;
import vsoc.util.AbstractTest;

public abstract class AbstractServerTest extends AbstractTest {

	public AbstractServerTest(String name) {
		super(name);
	}
	
    protected void addPlayerWest(Server s, Controller c, double x, double y, double dir) {
        VsocPlayerWest p = new VsocPlayerWest(x, y, dir);
        p.setController(c);
        ServerUtil.current().addPlayerWest(s, p);
    }

    protected void addPlayerEast(Server s, Controller c, double x, double y, double dir) {
        VsocPlayerEast p = new VsocPlayerEast(x, y, dir);
        p.setController(c);
        ServerUtil.current().addPlayerEast(s, p);
    }

}
