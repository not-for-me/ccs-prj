package CC_PRJ.Interface.Client;

import CC_PRJ.DataModel.StringQueue;
import CC_PRJ.Interface.Component.LoginWindow;
import CC_PRJ.SSM.Client.AbsoluteConsistency;
import CC_PRJ.SSM.Client.FrequentUpdate;
//import CC_PRJ.SSM.Client.DeadReckoning;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientInterface {
	public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	public final static int ABS_MODE = 1;
	public final static int FRQ_MODE = 2;
	public final static int DEAD_MODE = 3;
	public final static int TRUE = 1;
	public final static int FALSE = 0;

	private static ClientInterface instance = new ClientInterface();
	private static StringQueue msgQueue = new StringQueue();
	
	private String address;
	private int port = 5000;
	private int loginFlag = FALSE;
	private Socket sock;
	private int userID;
	private int sharedMode;
	private PrintWriter out;
	private BufferedReader in;

	public static ClientInterface getInstance() {
		return instance;
	}
	public static StringQueue getMSGQueue() {
		return msgQueue;
	}

	private void connect() {
		LoginWindow.getInstance().drawLoginWindow();

		while(loginFlag == FALSE) {}
		try {
			sock  = new Socket(address, port);
			out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );
			in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(int loginFlag) {
		this.loginFlag = loginFlag;
	}
	public Socket getSock() {
		return sock;
	}
	public void setSock(Socket sock) {
		this.sock = sock;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public PrintWriter getOut() {
		return out;
	}
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	public BufferedReader getIn() {
		return in;
	}
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	public int getSharedMode() {
		return sharedMode;
	}
	public void setSharedMode(int sharedMode) {
		this.sharedMode = sharedMode;
	}
	/*
	private void getDefaultInfo() {
		try {
			String input;
			input = in.readLine();
			StringTokenizer str = new StringTokenizer(input, "/");
			userID = Integer.parseInt( str.nextToken() );
			sharedMode = Integer.parseInt( str.nextToken() );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	private void start() {
		//this.getDefaultInfo();
		System.out.println("Received Shrared Stated Mode from Server: " + sharedMode);
		switch(sharedMode){
		case ABS_MODE:
			System.out.println("Here is Absolute Consistency Mode!");
			ReceiveThread rcvThread = new ReceiveThread(in);
			rcvThread.start();
			AbsoluteConsistency absMode = new AbsoluteConsistency(this);
			absMode.run();
			break;
		case FRQ_MODE:
			System.out.println("Here is Frequently State Update Mode!");
			FrequentUpdate frqMode = new FrequentUpdate();
			frqMode.run();
			break;
		case DEAD_MODE:
			System.out.println("Here is Dead Reckoning Mode!");
			//DeadReckoning deadMode = new DeadReckoning();
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {
		ClientInterface.getInstance().connect();
		ClientInterface.getInstance().start();
		System.out.println("End?");
		ClientInterface.getInstance().close();
	}
}
