package CC_PRJ.Interface.Client;

import CC_PRJ.DataModel.StringQueue;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Component.ClientExecutionWindow;
import CC_PRJ.SSM.SharedMode;
import CC_PRJ.SSM.Client.AbsMode.AbsoluteConsistency;
import CC_PRJ.SSM.Client.DeadMode.DeadReckoning;
import CC_PRJ.SSM.Client.FrqMode.FrequentUpdate;
//import CC_PRJ.SSM.Client.DeadReckoning;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInterface {
	public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	public final static int TRUE = 1;
	public final static int FALSE = 0;

	private static ClientInterface instance = new ClientInterface();
	private static StringQueue msgQueue = new StringQueue();

	private String address;
	private int port = 5000;
	private int loginFlag = FALSE;
	private int closeFlag = FALSE;
	private Socket sock;
	private int userID;
	private PrintWriter out;
	private BufferedReader in;

	public static ClientInterface getInstance() {
		return instance;
	}
	public static StringQueue getMSGQueue() {
		return msgQueue;
	}

	private void connect() {
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
	public int getCloseFlag() {
		return closeFlag;
	}
	public void setCloseFlag(int closeFlag) {
		this.closeFlag = closeFlag;
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
	/*
	public int getSharedMode() {
		return sharedMode;
	}
	public void setSharedMode(int sharedMode) {
		this.sharedMode = sharedMode;
	}
	 */
	
	private void getDefaultInfo() {
		try {
			String input;
			input = in.readLine();
			//StringTokenizer str = new StringTokenizer(input, "/");
			//userID = Integer.parseInt( str.nextToken() );
			userID = Integer.parseInt( input );
			System.out.println("My User Id: " + userID);
			//sharedMode = Integer.parseInt( str.nextToken() );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	private void start() {
		//this.getDefaultInfo();
		System.out.println("Received Shrared Stated Mode from Server: " + SharedMode.getInstance().getSharedMode());
		switch(SharedMode.getInstance().getSharedMode()){
		case SharedMode.ABS_MODE:
			System.out.println("Here is Absolute Consistency Mode!");
			ReceiveThread rcvThread = new ReceiveThread(in);
			rcvThread.start();
			AbsoluteConsistency absMode = new AbsoluteConsistency(this);
			absMode.run();
			break;
		case SharedMode.FRQ_MODE:
			System.out.println("Here is Frequently State Update Mode!");
			FrequentUpdate frqMode = new FrequentUpdate();
			frqMode.run();
			break;
		case SharedMode.DEAD_MODE:
			System.out.println("Here is Dead Reckoning Mode!");
			//DeadReckoning deadMode = new DeadReckoning();
			break;
		default:
			break;
		}
	}
	 */
	public static void main(String[] args) {
		ClientExecutionWindow.getInstance().drawWindow();

		while(true){
			if(SharedMode.getInstance().getSharedMode() != 0) {
				System.out.println("Received Shrared Stated Mode from Server: " + SharedMode.getInstance().getSharedMode());
				switch(SharedMode.getInstance().getSharedMode()){
				case SharedMode.ABS_MODE:
					System.out.println("Here is Absolute Consistency Mode!");

					ClientInterface.getInstance().connect();
					//ClientInterface.getInstance().start();
					ClientInterface.getInstance().getDefaultInfo();
					ReceiveThread rcvThread = new ReceiveThread(ClientInterface.getInstance().getIn());
					rcvThread.start();
					
					UserConnInfo userInfo = new UserConnInfo(ClientInterface.getInstance().getUserID(), ClientInterface.getInstance().getSock(), 
															ClientInterface.getInstance().getIn(), ClientInterface.getInstance().getOut() );
					
					AbsoluteConsistency absMode = new AbsoluteConsistency(userInfo);
					absMode.run();


					System.out.println("End?");
					ClientInterface.getInstance().close();
					System.exit(0);
					break;
				case SharedMode.FRQ_MODE:
					System.out.println("Here is Frequently State Update Mode!");
					while(ClientInterface.getInstance().getLoginFlag() == FALSE) {}
					FrequentUpdate frqMode = new FrequentUpdate();
					frqMode.run();
					System.exit(0);
					break;
				case SharedMode.DEAD_MODE:
					System.out.println("Here is Dead Reckoning Mode!!!");
					
					ClientInterface.getInstance().connect();
					//ClientInterface.getInstance().start();
					ClientInterface.getInstance().getDefaultInfo();
					ReceiveThread rcv2Thread = new ReceiveThread(ClientInterface.getInstance().getIn());
					rcv2Thread.start();
					
					UserConnInfo userInfo2 = new UserConnInfo(ClientInterface.getInstance().getUserID(), ClientInterface.getInstance().getSock(), 
															ClientInterface.getInstance().getIn(), ClientInterface.getInstance().getOut() );
					
					DeadReckoning deadMode = new DeadReckoning(userInfo2);
					deadMode.run();
					
					System.out.println("End?");
					ClientInterface.getInstance().close();
					System.exit(0);
					break;
				default:
					break;
				}
			}
		}

	}
}
