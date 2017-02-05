package vsoc.view;

import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import org.apache.log4j.Logger;


/**
 * Contains a FieldPanel .
 */
public class FieldFrame extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	
    private FieldContentPanel fieldContentPanel;

    static Logger log = Logger.getLogger(FieldFrame.class);

    public FieldFrame() {
        initGuiComponents();
    }

    private void initGuiComponents() {
        addWindowListener(this);
        this.fieldContentPanel = new FieldContentPanel();
        setContentPane(this.fieldContentPanel);
        setSize(1200, 900);
        setLocation(0, 0);
        URL iconUrl = getClass().getClassLoader().getResource("logo.jpg");
        ImageIcon ii = new ImageIcon(iconUrl);
        setIconImage(ii.getImage());
    }


    public static void open(SimulationContainer simCont, String name) {
        FieldFrame ff = new FieldFrame();
        ff.setTitle("vsoc " + name);
        ff.setSim(simCont.getSimulation());
        Thread simThread = new Thread(simCont, name);
        simThread.start();
        ff.setVisible(true);
    }

    public void setSim(Simulation srv) {
        this.fieldContentPanel.setSim(srv);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Nothing to be done
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Nothing to be done
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Nothing to be done
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Nothing to be done
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Nothing to be done
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Nothing to be done
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Nothing to be done
    }


}
