package CC_PRJ.Interface.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class MiddleComponent {
	private JPanel middlePanel;
	private JPanel middleLeftPanel;
	private JPanel middleRightPanel;
	private JLabel modeLabel;
	private JTextField modeTextField;
	private JLabel userNumLabel;
	private JTextField userTextField;
	
	public MiddleComponent() {
		middlePanel = new JPanel();
		Border infoBorder = BorderFactory.createEtchedBorder();
		infoBorder = BorderFactory.createTitledBorder(infoBorder, "Information Status");
		middlePanel.setBorder(infoBorder);
		middlePanel.setLayout(new GridLayout(1,2));

		this.drawMiddleLeftPanel();
		this.drawMiddleRightPanel();

		middlePanel.add(middleLeftPanel);
		middlePanel.add(middleRightPanel);
	}

	private void drawMiddleLeftPanel() {
		middleLeftPanel = new JPanel();
		middleLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		modeLabel = new JLabel("Method :");
		modeLabel.setPreferredSize(new Dimension(60, 20));
		modeTextField = new JTextField("Not Determined");
		modeTextField.setHorizontalAlignment(JTextField.CENTER);
		modeTextField.setEditable(false);
		modeTextField.setPreferredSize(new Dimension(150, 20));

		middleLeftPanel.add(modeLabel);
		middleLeftPanel.add(modeTextField);
	}

	private void drawMiddleRightPanel() {
		middleRightPanel = new JPanel();
		middleRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		userNumLabel = new JLabel("User Connections :");
		userNumLabel.setPreferredSize(new Dimension(120,20));
		userTextField = new JTextField("0");
		userTextField.setHorizontalAlignment(JTextField.RIGHT);
		userTextField.setEditable(false);
		userTextField.setPreferredSize(new Dimension(60, 20));

		middleRightPanel.add(userNumLabel);
		middleRightPanel.add(userTextField);		
	}
	
	public JPanel getMiddlePanel() {
		return middlePanel;
	}
	public JTextField getModeTextField() {
		return modeTextField;
	}
	public JTextField getUserTextField() {
		return userTextField;
	}
}
