package vsoc.model;

import org.springframework.beans.factory.FactoryBean;

public class DefaultServerFactory implements FactoryBean {

    private ServerUtil util = ServerUtil.current();

    private int westPlayerCount = 3;

    private int eastPlayerCount = 3;

    public DefaultServerFactory() {
        super();
    }

    public Server createServer() {
        Server srv = new Server();
        this.util.addGoalWest(srv, -Server.WIDTH / 2, 0);
        this.util.addGoalEast(srv, Server.WIDTH / 2, 0);
        this.util.addFlags(srv);
        ServerUtil r = this.util;
        srv.addSimObject(new Ball(0, 0));
        for (int i = 0; i < this.eastPlayerCount; i++) {
            this.util.addPlayerEast(srv, new VsocPlayerEast(20, i * 10 - 10,
                    180));
        }
        for (int i = 0; i < this.westPlayerCount; i++) {
            this.util.addPlayerWest(srv,
                    new VsocPlayerWest(-20, i * 10 - 10, 0));
        }
        return srv;
    }

    public int getEastPlayerCount() {
        return this.eastPlayerCount;
    }

    public void setEastPlayerCount(int eastPlayerCount) {
        this.eastPlayerCount = eastPlayerCount;
    }

    public void setEastPlayerCount(String eastPlayerCount) {
        this.eastPlayerCount = Integer.parseInt(eastPlayerCount);
    }

    public int getWestPlayerCount() {
        return this.westPlayerCount;
    }

    public void setWestPlayerCount(int westPlayerCount) {
        this.westPlayerCount = westPlayerCount;
    }

    public void setWestPlayerCount(String westPlayerCount) {
        this.westPlayerCount = Integer.parseInt(westPlayerCount);
    }

    public Object getObject() throws Exception {
        return createServer();
    }

    public Class getObjectType() {
        return Server.class;
    }

    public boolean isSingleton() {
        return true;
    }

}
