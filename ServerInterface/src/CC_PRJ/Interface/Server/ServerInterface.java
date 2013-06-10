package CC_PRJ.Interface.Server;

import java.io.PrintWriter;
import java.util.ArrayList;

import CC_PRJ.DataModel.StringQueue;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Component.WindowManager;
import CC_PRJ.SSM.SharedMode;
import CC_PRJ.SSM.Server.AbsMode.AbsoluteConsistency;
import CC_PRJ.SSM.Server.FrqMode.FrequentStateRegenerate;
import CC_PRJ.SSM.Server.DeadMode.DeadReckoning;


public class ServerInterface {
	private static ServerInterface instance = new ServerInterface();
	private static ArrayList<UserConnInfo> userConnInfoList = new ArrayList<UserConnInfo>();
	private static StringQueue userMSGQueue = new StringQueue();
	
	private int port = 5000;
	private int userNum = 0;
	private boolean listenFlag = false;
	private boolean startFlag = false;

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
				
				SocketListen abslisten = new SocketListen();
				abslisten.start();
				
				WindowManager.getInstance().getBottom().getStartBtn().setText("Start");
				WindowManager.getInstance().getBottom().getStartBtn().setEnabled(false);
				//while(ServerInterface.getInstance().getStartFlag() == FALSE) {}
				AbsoluteConsistency absMode = new AbsoluteConsistency();
				absMode.run();
				break;
				
			case SharedMode.FRQ_MODE:
				System.out.println("[Server] Frequently State Regeneration Mode!");
				WindowManager.getInstance().getBottom().getStartBtn().setText("Start");
				WindowManager.getInstance().getBottom().getStartBtn().setEnabled(false);
				FrequentStateRegenerate frqMode = new FrequentStateRegenerate();
				frqMode.run();
				break;
				
			case SharedMode.DEAD_MODE:
				System.out.println("[Server] Dead Reckoning Mode!");
				while(ServerInterface.getInstance().isListenFlag() == false) {}
				
				SocketListen daedlisten = new SocketListen();
				daedlisten.start();
				
				WindowManager.getInstance().getBottom().getStartBtn().setText("Start");
				WindowManager.getInstance().getBottom().getStartBtn().setEnabled(false);
				//while(ServerInterface.getInstance().getStartFlag() == FALSE) {}
				DeadReckoning deadMode = new DeadReckoning();
				deadMode.run();
				break;
			default:
				break;
			}
		}
	}
}
