package CC_Client.Interface;

import java.io.BufferedReader;

public class ReceiveThread extends Thread{
	private BufferedReader in;
	
	public ReceiveThread(BufferedReader in) {
		this.in = in;
	}
	
	public void run() {
		System.out.println("Receive Thread Run!!");
		try 
		{
			String input = null;
			while( ( input = in.readLine() ) != null ){
				System.out.println("Income Message: " + input);
				ClientInterface.getMSGQueue().enqueueString(input);
			}
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
	}
}
