package vsoc.server;

/**
 * An interface for views to be able to react on changes of the server.
 */

@FunctionalInterface
public interface ServerListener {

  public void serverChangePerformed (Server s);
}