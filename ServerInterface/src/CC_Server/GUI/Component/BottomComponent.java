package CC_Server.GUI.Component;

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
	private JPanel bottomLeft1Panel;
	private JPanel bottomLeft2Panel;
	private JPanel bottomRightPanel;
	private JLabel portLabel;
	private JTextField portTextField;
	private JLabel userNumLabel;
	private JTextField userNumTextField;
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
		bottomLeftPanel.setLayout(new GridLayout(1,2));
		
		bottomLeft1Panel = new JPanel();
		bottomLeft1Panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		bottomLeft2Panel = new JPanel();
		bottomLeft2Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		portLabel = new JLabel("Port :");
		portLabel.setPreferredSize(new Dimension(40, 30));
		portTextField = new JTextField("0");
		portTextField.setHorizontalAlignment(JTextField.CENTER);
		portTextField.setPreferredSize(new Dimension(60, 20));
		
		bottomLeft1Panel.add(portLabel);
		bottomLeft1Panel.add(portTextField);
		
		userNumLabel = new JLabel("User :");
		userNumLabel.setPreferredSize(new Dimension(40, 30));
		userNumTextField = new JTextField("0");
		userNumTextField.setHorizontalAlignment(JTextField.CENTER);
		userNumTextField.setPreferredSize(new Dimension(60, 20));
		
		bottomLeft2Panel.add(userNumLabel);
		bottomLeft2Panel.add(userNumTextField);
		
		bottomLeftPanel.add(bottomLeft1Panel);
		bottomLeftPanel.add(bottomLeft2Panel);
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
	public JTextField getUserNumTextField() {
		return userNumTextField;
	}
	public JButton getStartBtn() {
		return startBtn;
	}
}