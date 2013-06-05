package CC_PRJ.Interface.Server;
import CC_PRJ.DataModel.UserConnInfo;

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
