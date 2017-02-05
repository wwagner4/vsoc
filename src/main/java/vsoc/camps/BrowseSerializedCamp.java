package vsoc.camps;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import vsoc.util.Serializer;
import vsoc.view.FieldFrame;

public class BrowseSerializedCamp {

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
            e.printStackTrace();
        }
    }

}
