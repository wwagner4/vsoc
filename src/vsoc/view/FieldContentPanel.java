package vsoc.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vsoc.model.Server;

public class FieldContentPanel extends JPanel implements ActionListener {

    FieldCanvas fieldCanvas = new FieldCanvas();

    JPanel speedPanel = new JPanel();

    JComboBox delayBox = new JComboBox();

    JComboBox stepsBox = new JComboBox();

    public FieldContentPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setServer(Server s) {
        this.fieldCanvas.setServer(s);
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        this.speedPanel.setLayout(new GridBagLayout());
        this.delayBox.addActionListener(this);
        this.delayBox.setModel(delayComboBoxModel());
        this.stepsBox.addActionListener(this);
        this.stepsBox.setModel(stepsComboBoxModel());
        this.speedPanel.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(this.fieldCanvas, BorderLayout.CENTER);
        this.add(this.speedPanel, BorderLayout.EAST);
        this.speedPanel.add(new JLabel("steps"), createGridBagConstraints(0, 0));
        this.speedPanel.add(this.stepsBox, createGridBagConstraints(1, 0));
        this.speedPanel.add(new JLabel("delay"), createGridBagConstraints(0, 1));
        this.speedPanel.add(this.delayBox, createGridBagConstraints(1, 1));
        JPanel fillPanel = new JPanel();
        GridBagConstraints fillConstr = createGridBagConstraints(0, 2);
        fillConstr.weighty = 1.0;
        this.speedPanel.add(fillPanel, fillConstr);
    }

    private GridBagConstraints createGridBagConstraints(int gx, int gy) {
        GridBagConstraints constr = new GridBagConstraints();
        constr.gridx = gx;
        constr.gridy = gy;
        constr.fill = GridBagConstraints.BOTH;
        constr.insets = new Insets(0, 5, 0, 0);
        return constr;
    }

    private ComboBoxModel delayComboBoxModel() {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        m.addElement(new Integer(0));
        m.addElement(new Integer(1));
        m.addElement(new Integer(5));
        m.addElement(new Integer(10));
        m.addElement(new Integer(50));
        m.addElement(new Integer(100));
        m.addElement(new Integer(200));
        m.addElement(new Integer(500));
        return m;
    }

    private ComboBoxModel stepsComboBoxModel() {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        m.addElement(new Integer(1));
        m.addElement(new Integer(2));
        m.addElement(new Integer(5));
        m.addElement(new Integer(10));
        m.addElement(new Integer(50));
        m.addElement(new Integer(100));
        m.addElement(new Integer(200));
        m.addElement(new Integer(500));
        return m;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == this.delayBox) {
            Integer delay = (Integer) this.delayBox.getSelectedItem();
            this.fieldCanvas.setDelay(delay.intValue());
        } else if (evt.getSource() == this.stepsBox) {
            Integer steps = (Integer) this.stepsBox.getSelectedItem();
            this.fieldCanvas.setSteps(steps.intValue());
        }
    }
}