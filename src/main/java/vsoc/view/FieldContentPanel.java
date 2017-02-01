package vsoc.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;

import vsoc.model.Server;

public class FieldContentPanel extends JPanel implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	FieldCanvas fieldCanvas = new FieldCanvas();

	JPanel speedPanel = new JPanel();

	JToggleButton animateButton = new JToggleButton("animate");

	JSlider speedSlider = new JSlider();

	public FieldContentPanel() {
		try {
			jbInit();
			this.fieldCanvas.setSteps(Integer.MAX_VALUE);
			this.fieldCanvas.setDelay(0);
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
		speedSlider.setModel(speedSliderModel());
		speedSlider.addChangeListener(this);
		animateButton.addActionListener(this);
		this.speedPanel.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(this.fieldCanvas, BorderLayout.CENTER);
		this.add(this.speedPanel, BorderLayout.EAST);
		this.speedPanel.add(new JLabel(""), createGridBagConstraints(0, 0));
		this.speedPanel.add(this.animateButton, createGridBagConstraints(1, 0));
		this.speedPanel.add(new JLabel("speed"), createGridBagConstraints(0, 1));
		this.speedPanel.add(this.speedSlider, createGridBagConstraints(1, 1));
		JPanel fillPanel = new JPanel();
		GridBagConstraints fillConstr = createGridBagConstraints(0, 2);
		fillConstr.weighty = 1.0;
		this.speedPanel.add(fillPanel, fillConstr);
	}

	private BoundedRangeModel speedSliderModel() {
		return new DefaultBoundedRangeModel(0, 5, 0, 100);
	}

	private GridBagConstraints createGridBagConstraints(int gx, int gy) {
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = gx;
		constr.gridy = gy;
		constr.fill = GridBagConstraints.BOTH;
		constr.insets = new Insets(0, 5, 0, 0);
		return constr;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == this.animateButton) {
			if (this.animateButton.isSelected()) {
				this.fieldCanvas.setDelay(adjust(this.speedSlider.getValue()));
				this.fieldCanvas.setSteps(1);
			} else {
				this.fieldCanvas.setDelay(0);
				this.fieldCanvas.setSteps(Integer.MAX_VALUE);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == this.speedSlider) {
			if (this.animateButton.isSelected()) {
				this.fieldCanvas.setDelay(adjust(this.speedSlider.getValue()));
			}
		}

	}

	private int adjust(int value) {
		return 5 * value * value / 1000;
	}
}