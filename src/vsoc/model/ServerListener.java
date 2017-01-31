package vsoc.model;

/**
 * An interface for views to be able to react on changes of the server.
 */

public interface ServerListener {

  public void serverChangePerformed (Server s);
}