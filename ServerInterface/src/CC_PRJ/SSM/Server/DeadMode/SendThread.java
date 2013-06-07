package CC_PRJ.SSM.Server.DeadMode;

import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Server.ServerInterface;

public class SendThread extends Thread {
	//private UserConnInfo userConnInfo;
	private int delayTime = 1000;
	
	//public SendThread(UserConnInfo userConnInfo) {
	public SendThread() {
		//this.userConnInfo = userConnInfo;
	}

	public void run() {
		System.out.println("Send Thread Run in Server !!!");
/*
		try 
		{
			try { Thread.sleep(delayTime); } catch (InterruptedException e) { System.out.println("Interrupted"); }
			
			String inputString = null;
			
			while( ( inputString = userConnInfo.getIn().readLine() ) != null ){
				System.out.println("Income Message: " + inputString);
				ServerInterface.getUserMSGQueue().enqueueString(inputString);
			}
			
			synchronized(ServerInterface.getUserConnInfoList()){
				ServerInterface.getUserConnInfoList().remove(userConnInfo);
			}
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
		*/
	}

}
