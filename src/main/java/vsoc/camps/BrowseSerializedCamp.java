package vsoc.camps;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import vsoc.server.gui.FieldFrame;
import vsoc.util.Serializer;

public class BrowseSerializedCamp {
	
	private static Logger log = Logger.getLogger(BrowseSerializedCamp.class);

    public BrowseSerializedCamp() {
        super();
    }

    public static void main(String[] args) {
        BrowseSerializedCamp browser = new BrowseSerializedCamp();
        browser.run();
    }

    private void run() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                Object obj = Serializer.current().deserialize(file);
                if (obj instanceof Camp) {
                    FieldFrame.open((Camp<?>) obj, "camp");
                } else {
                    throw new IllegalStateException("The selected file '"
                            + file + "' does not contain a camp but "
                            + obj.getClass());
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "No serialized file was selected !\nProgram aborted.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()
                    + " !\nProgram aborted.");
            log.error("Could not browse serialized camp. " + e.getMessage(), e);
        }
    }

}
