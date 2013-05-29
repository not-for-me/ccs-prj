import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ServerInterface implements ActionListener {
	private final static int FRAME_WIDTH = 500;
	private final static int FRAME_HEIGHT = 220;
	private final static int ABS_MODE = 1;
	private final static int FRQ_MODE = 2;
	private final static int DEAD_MODE = 3;
	private final static int TRUE = 1;
	private final static int FALSE = 0;

	private final static String FRAME_TITLE = "Server Window";
	private final static String ABSOLUTE_STRING = "Absolute Consistency";
	private final static String FRQ_STRING = "Frequently Update";
	private final static String DEAD_STRING = "Dead Reckoning";

	private static HashMap<Integer, Socket> sockMap;
	private int sharedMode = 0;
	private int portNum = 35000;
	private static int sockID = 0;
	private int stopFlag = 0;
	private static int userNum = 0;
	private JFrame frame;
	private JPanel upperPanel;
	private JPanel middlePanel;
	private JPanel middleLeftPanel;
	private JPanel middleRightPanel;
	private JPanel bottomPanel;
	private JPanel bottomLeftPanel;
	private JPanel bottomRightPanel;
	private JButton absBtn;
	private JButton frqBtn;
	private JButton deadBtn;
	private JLabel modeLabel;
	private JTextField modeTextField;
	private JLabel userNumLabel;
	private static JTextField userTextField;
	private JLabel portLabel;
	private JTextField portTextField;
	private JButton listenBtn;

	private void drawWindow() {
		this.drawFrame();
		this.drawTopPanel();
		this.drawMiddlePanel();
		this.drawBottomPanel();

		frame.add(upperPanel, BorderLayout.NORTH);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	private void drawFrame() {
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setTitle(FRAME_TITLE);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void drawTopPanel() {
		upperPanel = new JPanel();
		Border methodBorder = BorderFactory.createEtchedBorder();
		methodBorder = BorderFactory.createTitledBorder(methodBorder, "Shared State Method");
		upperPanel.setBorder(methodBorder);
		upperPanel.setLayout(new FlowLayout());

		absBtn = new JButton(ABSOLUTE_STRING);
		frqBtn = new JButton(FRQ_STRING);
		deadBtn = new JButton(DEAD_STRING);
		absBtn.setPreferredSize(new Dimension(160, 30));
		frqBtn.setPreferredSize(new Dimension(140, 30));
		deadBtn.setPreferredSize(new Dimension(140, 30));
		absBtn.addActionListener(this);
		frqBtn.addActionListener(this);
		deadBtn.addActionListener(this);

		upperPanel.add(absBtn);
		upperPanel.add(frqBtn);
		upperPanel.add(deadBtn);
	}

	private void drawMiddlePanel() {
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

	private void drawBottomPanel() {
		bottomPanel = new JPanel();
		Border connectionBorder = BorderFactory.createEtchedBorder();
		connectionBorder = BorderFactory.createTitledBorder(connectionBorder, "Connection Information");
		bottomPanel.setBorder(connectionBorder);
		bottomPanel.setLayout(new GridLayout(1,2));

		this.drawBottomLeftPanel();
		this.drawBottomRightPanel();

		bottomPanel.add(bottomLeftPanel);
		bottomPanel.add(bottomRightPanel);
	}

	private void drawBottomLeftPanel() {
		bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		portLabel = new JLabel("Port Num :");
		portLabel.setPreferredSize(new Dimension(60, 30));
		portTextField = new JTextField(Integer.toString(portNum));
		portTextField.setHorizontalAlignment(JTextField.CENTER);
		portTextField.setPreferredSize(new Dimension(100, 20));
		portTextField.addActionListener(this);

		bottomLeftPanel.add(portLabel);
		bottomLeftPanel.add(portTextField);
	}

	private void drawBottomRightPanel() {
		bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new FlowLayout());
		listenBtn = new JButton("Listen");
		listenBtn.setPreferredSize(new Dimension(140, 30));
		listenBtn.addActionListener(this);

		bottomRightPanel.add(listenBtn);
	}

	private void listen() {
		AcceptThread acceptThread = new AcceptThread(this.getPortNum());
		acceptThread.start();
		while(true) {
			Integer intID = new Integer(sockID);
			if(getSockMap().containsKey(intID)){
				System.out.println("Thread Acceptance OK");
				CommThread communicationThread = new CommThread(getSockMap().get(intID));
				communicationThread.start();
				userTextField.setText(Integer.toString(++userNum));
				sockID++;
				System.out.println("Thread Communcation OK");
			}
		}
	}

	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if(source == absBtn) {
			this.modeTextField.setText(ABSOLUTE_STRING);
			this.setSharedMode(ABS_MODE);
		}
		else if(source == frqBtn) {
			this.modeTextField.setText(FRQ_STRING);
			this.setSharedMode(FRQ_MODE);
		}
		else if(source == deadBtn) {
			this.modeTextField.setText(DEAD_STRING);
			this.setSharedMode(DEAD_MODE);
		}
		else if(source == portTextField) {
			this.setPortNum(Integer.parseInt(portTextField.getText()));

		}
		else if(source == listenBtn) {
			if(listenBtn.getText().compareTo("Listen") == 0) {
				listenBtn.setText("Stop");
				
			}
			else if(listenBtn.getText().compareTo("Stop") == 0) {
				listenBtn.setText("Listen");
			}

		}
	}

	public int getSharedMode() {
		return sharedMode;
	}

	public void setSharedMode(int sharedMode) {
		this.sharedMode = sharedMode;
	}

	public int getPortNum() {
		return portNum;
	}

	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}

	public static HashMap<Integer, Socket> getSockMap() {
		if(sockMap == null) {
			sockMap = new HashMap<Integer, Socket>();
		}

		return sockMap;
	}
	
	public static int getSockID() {
		return sockID;
	}

	public static void setSockID(int sockID) {
		ServerInterface.sockID = sockID;
	}

	public static void main(String[] args){
		ServerInterface serverProgram = new ServerInterface();
		serverProgram.drawWindow();
		serverProgram.listen();
	}

}
