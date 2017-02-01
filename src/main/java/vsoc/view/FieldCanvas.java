package vsoc.view;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import vsoc.model.Server;
import vsoc.model.ServerListener;

/**
 * A component that can display a Server
 */

public class FieldCanvas extends Canvas implements ServerListener,
        ComponentListener {

	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger(FieldCanvas.class);

    private BufferedImage buffer;

    private Graphics2D bg;

    private Server server = new NullServer();

    private int delay = 0;

    public FieldCanvas() {
        super();
        addComponentListener(this);
    }

    public void setServer(Server s) {
        this.server = s;
        s.addListener(this);
        this.setSteps(Integer.MAX_VALUE);
        this.setDelay(0);
    }

    public void paint(Graphics g) {
        try {
            if (this.isShowing()) {
                initBuffer();
                this.server.paint(this.bg);
                g.drawImage(this.buffer, 0, 0, this);
            }
        } catch (Exception ex) {
            g.drawString("[vsoc.view.FieldCanvas] not initialized", 10, 20);
        }
    }

    void initBuffer() {
        if (this.buffer == null) {
            this.buffer = new BufferedImage(2000, 1200,
                    BufferedImage.TYPE_3BYTE_BGR);
            this.bg = (Graphics2D) this.buffer.getGraphics();
            setTransform();
        }
    }

    private void setTransform() {
        if (this.bg != null) {
            AffineTransform trans = new AffineTransform();
            double h = getHeight();
            double w = getWidth();
            double s = getWidth() / 160;
            if (log.isDebugEnabled()) {
                log.debug("setTransform h=" + h + " w=" + w + " s=" + s);
            }
            trans.translate(w / 2, h / 2);
            trans.scale(s, -s);
            this.bg.setTransform(trans);
            this.repaint();
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void serverChangePerformed(Server s) {
        repaint();
        if (this.isShowing())
            s.setDelay(this.getDelay());
        else
            s.setDelay(0);
    }

    public void componentResized(ComponentEvent e) {
        setTransform();
    }

    public void componentMoved(ComponentEvent e) {
        // Nothing to do
    }

    public void componentShown(ComponentEvent e) {
        setTransform();
    }

    public void componentHidden(ComponentEvent e) {
        // nothing to be done.
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int val) {
        this.delay = val;
    }

    public void setSteps(int steps) {
        this.server.setSteps(steps);
    }
}
