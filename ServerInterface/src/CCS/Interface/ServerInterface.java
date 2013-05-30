package CCS.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import CCS.Model.UserConnInfo;
import CCS.Window.WindowManager;

public class ServerInterface {
	public final static int ABS_MODE = 1;
	public final static int FRQ_MODE = 2;
	public final static int DEAD_MODE = 3;
	public final static int TRUE = 1;
	public final static int FALSE = 0;

	private static ServerInterface instance = new ServerInterface();
	private static ArrayList<UserConnInfo> userConnInfoList = new ArrayList<UserConnInfo>();
	
	private int sharedMode = 0;
	private int port = 0;
	private int listenFlag = FALSE;
	private int startFlag = FALSE;

	
	private static int sockID = 0;

	private ServerInterface() {
	}		

	public static ServerInterface getInstance() {
		return instance;
	}

	public static ArrayList<UserConnInfo> getUserConnInfoList() {
		return userConnInfoList;
	}

	public static int getSockID() {
		return sockID;
	}

	private void listen() {
		ServerSocket server;

		try {
			server = new ServerSocket(port);

			while(startFlag == FALSE) {
				System.out.println("Listen...");
				Socket sock = server.accept();
				BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
				PrintWriter out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );

				UserConnInfo newComer = new UserConnInfo(sockID, sock, in, out);
				getUserConnInfoList().add(newComer);
				System.out.println("New User Detected! Num of Users: " + getUserConnInfoList().size());

				CommThread communicationThread = new CommThread(newComer);
				communicationThread.start();
				WindowManager.getInstance().getMiddle().getUserTextField().setText(Integer.toString( getUserConnInfoList().size() ));
				sockID++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Listen End and Total User Num: " + getUserConnInfoList().size() );
	}

	private void start() {
		while(startFlag == TRUE) {}


		switch(sharedMode){
		case ABS_MODE:
			goAbsoluteMode();
			break;
		case FRQ_MODE:
			break;
		case  DEAD_MODE:
			break;
		default:
			break;
		}
	}

	private void sendSharedMode() {
	}

	private void goAbsoluteMode() {

	}

	public int getSharedMode() {
		return sharedMode;
	}
	public void setSharedMode(int sharedMode) {
		this.sharedMode = sharedMode;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getListenFlag() {
		return listenFlag;
	}
	public void setListenFlag(int listenFlag) {
		this.listenFlag = listenFlag;
	}
	public int getStartFlag() {
		return startFlag;
	}
	public void setStartFlag(int startFlag) {
		this.startFlag = startFlag;
	}
	public static void main(String[] args){
		WindowManager.getInstance().drawWindow();

		while(ServerInterface.getInstance().getListenFlag() == FALSE) {}
		ServerInterface.getInstance().listen();
		//serverProgram.start();
	}

}
