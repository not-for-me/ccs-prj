package CC_Server.GUI.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class TopComponent {
	public final static String ABSOLUTE_STRING = "Absolute Consistency";
	public final static String FRQ_STRING = "Frequently Update";
	public final static String DEAD_STRING = "Dead Reckoning";
	
	private JPanel topPanel;
	private JButton absBtn;
	private JButton frqBtn;
	private JButton deadBtn;

	public TopComponent() {
		topPanel = new JPanel();
		Border methodBorder = BorderFactory.createEtchedBorder();
		methodBorder = BorderFactory.createTitledBorder(methodBorder, "Shared State Method");
		topPanel.setBorder(methodBorder);
		topPanel.setLayout(new FlowLayout());

		absBtn = new JButton(ABSOLUTE_STRING);
		frqBtn = new JButton(FRQ_STRING);
		deadBtn = new JButton(DEAD_STRING);
		absBtn.setPreferredSize(new Dimension(160, 30));
		frqBtn.setPreferredSize(new Dimension(140, 30));
		deadBtn.setPreferredSize(new Dimension(140, 30));

		topPanel.add(absBtn);
		topPanel.add(frqBtn);
		topPanel.add(deadBtn);
	}
	
	public JPanel getTopPanel() {
		return topPanel;
	}
	public JButton getAbsBtn() {
		return absBtn;
	}
	public JButton getFrqBtn() {
		return frqBtn;
	}
	public JButton getDeadBtn() {
		return deadBtn;
	}
}
