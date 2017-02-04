package vsoc.view;

import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import org.apache.log4j.Logger;

import vsoc.camps.Camp;
import vsoc.server.Server;

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


    public static void open(Camp camp, String string) {
        FieldFrame ff = new FieldFrame();
        ff.setTitle("vsoc " + string);
        ff.setServer(camp.getServer());
        Thread campThread = new Thread(camp, "Camp");
        campThread.start();
        ff.setVisible(true);
    }

    public static void open(Camp camp) {
        open(camp, "");
    }
    
    public void setServer(Server srv) {
        this.fieldContentPanel.setServer(srv);
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
