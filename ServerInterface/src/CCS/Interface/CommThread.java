package CCS.Interface;
import CCS.Model.UserConnInfo;

public class CommThread extends Thread {
	private UserConnInfo userConnInfo;
	
	public CommThread(UserConnInfo userConnInfo) {
		this.userConnInfo = userConnInfo;
	}

	public void run() {
		System.out.println("Communication Thread Run!!");
		try 
		{
			String inputString = null;
			
			while( ( inputString = userConnInfo.getIn().readLine() ) != null ){
				System.out.println("Income Message: " + inputString);
				userConnInfo.getOut().println(inputString);
				userConnInfo.getOut().flush();
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
