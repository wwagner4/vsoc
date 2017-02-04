package vsoc.camps;

import java.io.Serializable;

import vsoc.reports.Reportable;
import vsoc.server.Server;

public interface Camp extends Serializable, Runnable, Reportable {

    public Server getServer();

    public void setMaxGenerations(int i);

    public boolean isFinished();

    public void takeOneStep();

}
