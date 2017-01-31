package vsoc.camps;

import java.io.Serializable;

import vsoc.model.Server;
import vsoc.reports.Reportable;

public interface Camp extends Serializable, Runnable, Reportable {

    public Server getServer();

    public void setMaxGenerations(int i);

    public boolean isFinished();

    public void takeOneStep();

}
