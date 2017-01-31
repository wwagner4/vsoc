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

@SuppressWarnings("serial")
public class FieldContentPanel extends JPanel implements ActionListener {

    FieldCanvas fieldCanvas = new FieldCanvas();

    JPanel speedPanel = new JPanel();

    JComboBox<Integer> delayBox = new JComboBox<>();

    JComboBox<Integer> stepsBox = new JComboBox<>();

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

    private void jbInit() {
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

    private ComboBoxModel<Integer> delayComboBoxModel() {
        DefaultComboBoxModel<Integer> m = new DefaultComboBoxModel<>();
        m.addElement(0);
        m.addElement(1);
        m.addElement(5);
        m.addElement(10);
        m.addElement(50);
        m.addElement(100);
        m.addElement(200);
        m.addElement(500);
        return m;
    }

    private ComboBoxModel<Integer> stepsComboBoxModel() {
        DefaultComboBoxModel<Integer> m = new DefaultComboBoxModel<>();
        m.addElement(1);
        m.addElement(2);
        m.addElement(5);
        m.addElement(10);
        m.addElement(50);
        m.addElement(100);
        m.addElement(200);
        m.addElement(500);
        return m;
    }

    @Override
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