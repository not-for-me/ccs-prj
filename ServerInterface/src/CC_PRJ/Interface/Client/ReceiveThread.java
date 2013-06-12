package CC_PRJ.Interface.Client;

/*
 * 클라이언트 프로그램에서 서비 및 다른 클라이언트로부터 메시지를 받아 큐에 넣는 역할 만 하는 스레드
 */

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
