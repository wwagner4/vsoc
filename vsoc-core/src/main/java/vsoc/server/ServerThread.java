package vsoc.server;

/**
 * Thread performing the server actions.
 */

class ServerThread extends Thread {

    private Server server;

    private boolean running = true;

    ServerThread(Server s) {
        this.server = s;
    }

    @Override
    public void run() {
        while (this.running)
            this.server.takeStep();
    }

    void stopServer() {
        this.running = false;
    }
}