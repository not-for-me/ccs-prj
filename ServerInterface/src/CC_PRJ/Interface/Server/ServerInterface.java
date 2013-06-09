package CC_PRJ.Interface.Server;

import CC_PRJ.Communication.SocketListen;
import CC_PRJ.DataModel.StringQueue;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Component.WindowManager;
import CC_PRJ.SSM.SharedMode;
import CC_PRJ.SSM.Server.AbsMode.AbsoluteConsistency;
import CC_PRJ.SSM.Server.DeadMode.DeadReckoning;
import CC_PRJ.SSM.Server.FrqMode.FrequentStateRegenerate;
//import CC_PRJ.SSM.Server.DeadReckoning;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerInterface {
	private static ServerInterface instance = new ServerInterface();
	private static ArrayList<UserConnInfo> userConnInfoList = new ArrayList<UserConnInfo>();
	private static StringQueue userMSGQueue = new StringQueue();
	
	private int port = 5000;
	private int userNum = 0;
	private boolean listenFlag = false;
	private boolean startFlag = false;
	private int sockID = 0;

	private ServerInterface() {
	}		

	public static ServerInterface getInstance() {
		return instance;
	}

	public static ArrayList<UserConnInfo> getUserConnInfoList() {
		return userConnInfoList;
	}
	
	public static StringQueue getUserMSGQueue() {
		return userMSGQueue;
	}
	/*
	private void listen() {
		ServerSocket server;
		try {
			server = new ServerSocket(port);
			int count = 0;
			
			while(count < userNum) {
				System.out.println("Listen...");
				Socket sock = server.accept();
				BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
				PrintWriter out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );

				UserConnInfo newComer = new UserConnInfo(sockID, sock, in, out);
				getUserConnInfoList().add(newComer);
				System.out.println("New User Detected! Num of Users: " + getUserConnInfoList().size());
				System.out.println("User's ID: " + newComer.getId());
				
				ReceiveThread communicationThread = new ReceiveThread(newComer);
				communicationThread.start();
				WindowManager.getInstance().getMiddle().getUserTextField().setText(Integer.toString( getUserConnInfoList().size() ));
				sockID++;
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("User Connection End\nTotal User Number: " + getUserConnInfoList().size() );
	}
*/
	public void sendUserIDInfo(UserConnInfo userConnInfo) {
			PrintWriter out = userConnInfo.getOut();
			String sendMSG = Integer.toString( userConnInfo.getId() ); 
			out.println(sendMSG);
			out.flush();
	}

	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public boolean isListenFlag() {
		return listenFlag;
	}
	public void setListenFlag(boolean listenFlag) {
		this.listenFlag = listenFlag;
	}
	public boolean isStartFlag() {
		return startFlag;
	}
	public void setStartFlag(boolean startFlag) {
		this.startFlag = startFlag;
	}

	public static void main(String[] args){
		WindowManager.getInstance().drawWindow();
		
		while(true){
			switch(SharedMode.getInstance().getSharedMode()){
			case SharedMode.ABS_MODE:
				System.out.println("[Server] Absolute Consistency Mode!");
				
				while(ServerInterface.getInstance().isListenFlag() == false) {}
				//ServerInterface.getInstance().listen();
				
				SocketListen listen = new SocketListen();
				listen.start();
				
				WindowManager.getInstance().getBottom().getStartBtn().setText("Start");
				WindowManager.getInstance().getBottom().getStartBtn().setEnabled(false);
				//while(ServerInterface.getInstance().getStartFlag() == FALSE) {}
				AbsoluteConsistency absMode = new AbsoluteConsistency();
				absMode.run();
				
				break;
			case SharedMode.FRQ_MODE:
				System.out.println("[Server] Frequently State Regeneration Mode!");
				FrequentStateRegenerate frqMode = new FrequentStateRegenerate();
				frqMode.run();
				break;
			case SharedMode.DEAD_MODE:
				System.out.println("[Server] Dead Reckoning Mode!");
				
				while(ServerInterface.getInstance().isListenFlag() == false) {}
				//ServerInterface.getInstance().listen();
				
				SocketListen listen2 = new SocketListen();
				listen2.start();
				
				WindowManager.getInstance().getBottom().getStartBtn().setText("Start");
				WindowManager.getInstance().getBottom().getStartBtn().setEnabled(false);
				//while(ServerInterface.getInstance().getStartFlag() == FALSE) {}
				//ServerInterface.getInstance().sendUserIDInfo();
				DeadReckoning deadMode = new DeadReckoning();
				deadMode.run();
				
				break;
			default:
				break;
			}
		}
		// Initialization and ReDo ???
	}
}
