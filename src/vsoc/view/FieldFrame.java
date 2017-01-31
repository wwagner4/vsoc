package vsoc.view;

import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import vsoc.camps.Camp;
import vsoc.model.Server;

/**
 * Contains a FieldPanel .
 */
@SuppressWarnings("serial")
public class FieldFrame extends JFrame implements WindowListener {

    private FieldContentPanel fieldContentPanel;

    static Logger log = Logger.getLogger(FieldFrame.class);

    public FieldFrame() {
        initGuiComponents();
    }

    private void initGuiComponents() {
        addWindowListener(this);
        this.fieldContentPanel = new FieldContentPanel();
        setContentPane(this.fieldContentPanel);
        setSize(600, 300);
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
