package CCS.Window.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class BottomComponent {
	private JPanel bottomPanel;
	private JPanel bottomLeftPanel;
	private JPanel bottomRightPanel;
	private JLabel portLabel;
	private JTextField portTextField;
	private JButton startBtn;
	
	public BottomComponent() {
		bottomPanel = new JPanel();
		Border connectionBorder = BorderFactory.createEtchedBorder();
		connectionBorder = BorderFactory.createTitledBorder(connectionBorder, "Connection Information");
		bottomPanel.setBorder(connectionBorder);
		bottomPanel.setLayout(new GridLayout(1,2));

		drawBottomLeftPanel();
		drawBottomRightPanel();

		bottomPanel.add(bottomLeftPanel);
		bottomPanel.add(bottomRightPanel);
	}

	private void drawBottomLeftPanel() {
		bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		portLabel = new JLabel("Port Num :");
		portLabel.setPreferredSize(new Dimension(60, 30));
		portTextField = new JTextField("0");
		portTextField.setHorizontalAlignment(JTextField.CENTER);
		portTextField.setPreferredSize(new Dimension(100, 20));
		bottomLeftPanel.add(portLabel);
		bottomLeftPanel.add(portTextField);
	}

	private void drawBottomRightPanel() {
		bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new FlowLayout());
		startBtn = new JButton("Listen");
		startBtn.setPreferredSize(new Dimension(140, 30));
		bottomRightPanel.add(startBtn);
	}
	
	public JPanel getBottomPanel(){
		return bottomPanel;
	}
	public JTextField getPortTextField() {
		return portTextField;
	}
	public JButton getStartBtn() {
		return startBtn;
	}
}
