package CC_PRJ.Interface.Component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import CC_PRJ.Interface.Client.ClientInterface;
import CC_PRJ.SSM.SharedMode;

public class ClientExecutionWindow implements ActionListener {
	final static int CONN_FRAME_WIDTH = 500;
	final static int CONN_FRAME_HEIGHT = 160;
	final static String CONN_FRAME_TITLE = "Login Window";
	private JFrame connectionFrame;
	private TopComponent top;
	private JPanel ipPortPanel;
	private JPanel loginPanel;

	private JLabel addressLabel;
	private JLabel portLabel;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JButton logInBtn;
	private JButton cancelBtn;

	private static ClientExecutionWindow instance = new ClientExecutionWindow();

	private ClientExecutionWindow() {

	}

	public static ClientExecutionWindow getInstance() {
		return instance;
	}

	private void drawFrame() {
		connectionFrame = new JFrame();
		connectionFrame.setSize(CONN_FRAME_WIDTH, CONN_FRAME_HEIGHT);
		connectionFrame.setTitle(CONN_FRAME_TITLE);
		connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connectionFrame.setResizable(false);
		connectionFrame.setLayout(new BorderLayout());
	}

	private void drawIpPortPanel() {
		ipPortPanel = new JPanel();

		addressLabel = new JLabel("IP: ");
		addressLabel.setPreferredSize(new Dimension(20, 30));

		ipTextField = new JTextField("localhost");
		ipTextField.setPreferredSize(new Dimension(80, 20));
		ipTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ClientInterface.getInstance().setAddress( ipTextField.getText() );
				System.out.println("IP Input: " + ClientInterface.getInstance().getAddress() );
			}
			public void changedUpdate(DocumentEvent arg0) {}
			public void removeUpdate(DocumentEvent arg0) {}
		});

		portLabel = new JLabel("Port: ");
		portLabel.setPreferredSize(new Dimension(30, 30));

		portTextField = new JTextField("5000");
		portTextField.setPreferredSize(new Dimension(60, 20));
		portTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ClientInterface.getInstance().setPort( Integer.parseInt(portTextField.getText()) );
				System.out.println("Port Number Input: " + ClientInterface.getInstance().getPort() );
			}
			public void changedUpdate(DocumentEvent arg0) {}
			public void removeUpdate(DocumentEvent arg0) {}
		});

		ipPortPanel.add(addressLabel);
		ipPortPanel.add(ipTextField);
		ipPortPanel.add(portLabel);
		ipPortPanel.add(portTextField);
	}

	private void drawLoginPanel(){
		loginPanel = new JPanel();
		logInBtn = new JButton("Log In");
		logInBtn.setPreferredSize(new Dimension(80, 30));
		cancelBtn = new JButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(80, 30));

		logInBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		loginPanel.add(logInBtn);
		loginPanel.add(cancelBtn);
	}

	public void drawWindow() {
		drawFrame();
		top = new TopComponent();
		top.getAbsBtn().addActionListener(this);
		top.getFrqBtn().addActionListener(this);
		top.getDeadBtn().addActionListener(this);

		drawIpPortPanel();
		drawLoginPanel();

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2));
		Border loginBorder = BorderFactory.createEtchedBorder();
		loginBorder = BorderFactory.createTitledBorder(loginBorder, "Login Information");
		bottomPanel.setBorder(loginBorder);

		bottomPanel.add(ipPortPanel);
		bottomPanel.add(loginPanel);

		connectionFrame.add(top.getTopPanel(), BorderLayout.NORTH);
		connectionFrame.add(bottomPanel, BorderLayout.CENTER);
		connectionFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if(source == top.getAbsBtn()) {
			SharedMode.getInstance().setSharedMode(SharedMode.ABS_MODE);
		}
		else if(source == top.getFrqBtn()) {
			SharedMode.getInstance().setSharedMode(SharedMode.FRQ_MODE);
			ipTextField.setText("localhost");
			portTextField.setText("35000");
			ipTextField.setEditable(false);
			portTextField.setEditable(false);
			logInBtn.setText("Start");
		}
		else if(source == top.getDeadBtn()) {
			SharedMode.getInstance().setSharedMode(SharedMode.DEAD_MODE);
		}
		else if(source == logInBtn) {
			//if(logInBtn.getText().compareTo("Start") == 0) {
				
			//}
		//	else if(logInBtn.getText().compareTo("Log In") == 0) {
				ClientInterface.getInstance().setLoginFlag(ClientInterface.TRUE);
			//}
			connectionFrame.setVisible(false);
		}
		else if(source == cancelBtn) {
			connectionFrame.setVisible(false);
			System.exit(0);
		}
	}
}
