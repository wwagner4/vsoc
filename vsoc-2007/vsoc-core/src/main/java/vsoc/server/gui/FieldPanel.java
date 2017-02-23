package vsoc.server.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

/**
 * A component that can display a Simulation
 */

public class FieldPanel extends JPanel implements SimulationChangeListener, ComponentListener {

	private static final long serialVersionUID = 1L;
	
	private TransformPainter transformPainter;


	public FieldPanel() {
		super();
		this.transformPainter = new TransformPainter(this);
		addComponentListener(this);
	}

	public void setSim(Simulation s) {
		transformPainter.setSim(s);
		s.addListener(this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		if (this.isShowing()) {
			transformPainter.paintTransformed(g);
		}
	}

	@Override
	public void simulationChangePerformed(Paintable s) {
		repaint();
	}

	public void componentResized(ComponentEvent e) {
		transformPainter.setTransform(getWidth(), getHeight());
		this.repaint();
	}

	public void componentMoved(ComponentEvent e) {
		// Nothing to do
	}

	public void componentShown(ComponentEvent e) {
		transformPainter.setTransform(getWidth(), getHeight());
		this.repaint();
	}

	public void componentHidden(ComponentEvent e) {
		// nothing to be done.
	}

}

