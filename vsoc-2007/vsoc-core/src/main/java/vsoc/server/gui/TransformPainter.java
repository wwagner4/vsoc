package vsoc.server.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

public class TransformPainter {

	private Simulation sim = new NullSimulation();

	private Buffer buffer = new Buffer();

	private ImageObserver obs;

	public TransformPainter(ImageObserver obs) {
		this.obs = obs;
	}

	public void setSim(Simulation sim) {
		this.sim = sim;
	}

	public void paintTransformed(Graphics g) {
		this.sim.paint(buffer.getBufferGraphics());
		g.drawImage(buffer.getBuffer(), 0, 0, obs);
	}

	public void setTransform(double w, double h) {
		AffineTransform trans = new AffineTransform();
		double s = w / 160;
		trans.translate(w / 2, h / 2);
		trans.scale(s, -s);
		buffer.getBufferGraphics().setTransform(trans);
	}

}

class Buffer {

	private BufferedImage buffer;

	private Graphics2D bufferGraphics;

	public Buffer() {
		super();
		this.buffer = new BufferedImage(2000, 1200, BufferedImage.TYPE_3BYTE_BGR);
		this.bufferGraphics = (Graphics2D) this.buffer.getGraphics();
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public Graphics2D getBufferGraphics() {
		return bufferGraphics;
	}

}
