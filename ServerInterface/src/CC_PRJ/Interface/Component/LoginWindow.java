package CC_PRJ.Interface.Component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import CC_PRJ.Interface.Client.ClientInterface;

public class LoginWindow implements ActionListener {
	final static int CONN_FRAME_WIDTH = 300;
	final static int CONN_FRAME_HEIGHT = 100;
	final static String CONN_FRAME_TITLE = "Login Window";
	private JFrame connectionFrame;
	private JPanel upperPanel;
	private JPanel bottomPanel;

	private JLabel addressLabel;
	private JLabel portLabel;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JButton logInBtn;
	private JButton cancelBtn;
	
	private static LoginWindow instance = new LoginWindow();
	
	private LoginWindow() {
		
	}
	
	public static LoginWindow getInstance() {
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

	private void drawUpperPanel() {
		upperPanel = new JPanel();

		addressLabel = new JLabel("IP: ");
		addressLabel.setPreferredSize(new Dimension(50, 30));
		
		ipTextField = new JTextField("localhost");
		ipTextField.setPreferredSize(new Dimension(100, 20));
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
		portLabel.setPreferredSize(new Dimension(50, 30));
		
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

		upperPanel.add(addressLabel);
		upperPanel.add(ipTextField);
		upperPanel.add(portLabel);
		upperPanel.add(portTextField);
	}

	private void drawBottomPanel(){
		bottomPanel = new JPanel();
		logInBtn = new JButton("Log In");
		logInBtn.setPreferredSize(new Dimension(80, 20));
		cancelBtn = new JButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(80, 20));

		logInBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		bottomPanel.add(logInBtn);
		bottomPanel.add(cancelBtn);
	}

	public void drawLoginWindow() {
		drawFrame();
		drawUpperPanel();
		drawBottomPanel();
		connectionFrame.add(upperPanel, BorderLayout.NORTH);
		connectionFrame.add(bottomPanel, BorderLayout.CENTER);
		connectionFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if(source == logInBtn) {
			ClientInterface.getInstance().setLoginFlag(ClientInterface.TRUE);
			connectionFrame.setVisible(false);
		}
		else if(source == cancelBtn) {
			connectionFrame.setVisible(false);
			System.exit(0);
		}
	}
}
