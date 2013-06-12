package CC_PRJ.Interface.Server;
import CC_PRJ.DataModel.UserConnInfo;

/*
 * 클라이언트 프로그램에서 서비 및 다른 클라이언트로부터 메시지를 받아 큐에 넣는 역할 만 하는 스레드
 */

public class ReceiveThread extends Thread {
	private UserConnInfo userConnInfo;
	
	public ReceiveThread(UserConnInfo userConnInfo) {
		this.userConnInfo = userConnInfo;
	}

	public void run() {
		System.out.println("Receive Thread Run in Server !!!");
		System.out.println("User ID: " + userConnInfo.getId());
		try 
		{
			String inputString = null;
			
			while( ( inputString = userConnInfo.getIn().readLine() ) != null ){
				System.out.println("Income Message: " + inputString);
				ServerInterface.getUserMSGQueue().enqueueString(inputString);
			}
			
			userConnInfo.getSock().close();
			userConnInfo.getIn().close();
			userConnInfo.getOut().close();
			synchronized(ServerInterface.getUserConnInfoList()){
				ServerInterface.getUserConnInfoList().remove(userConnInfo);
			}
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
	}
}
