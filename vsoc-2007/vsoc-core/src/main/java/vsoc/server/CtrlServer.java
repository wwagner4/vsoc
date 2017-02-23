package vsoc.server;

import vsoc.server.gui.CtrlSimulation;

public class CtrlServer extends Server implements CtrlSimulation {

	private static final long serialVersionUID = 1L;

	private int delay = 0;

	@Override
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	@Override
	public void takeStep() {
		super.takeStep();
		pause();
	}

	private synchronized void pause() {
		if (this.delay > 0) {
			try {
				wait(this.delay);
			} catch (InterruptedException ex) {
				// continue
			}
		}
	}

}
