package CC_PRJ.SSM.Client.FrqMode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class ReceiveThread extends Thread {
	private int userPort = 25000;
	private DatagramPacket dp; // 받는 것
	private MulticastSocket ms; // 받는것
	
	public ReceiveThread() {
	}
	
	public void run() {
		System.out.println("Frq Mode Receive Mode Start!!!");
		
		try{
			ms = new MulticastSocket(33333);
			ms.joinGroup(InetAddress.getByName("239.2.3.4"));
		}catch(IOException ie){
			ie.printStackTrace();
		}
		while(true){
			try{
				dp = new DatagramPacket(new byte[128], 128);
				System.out.println("Waiting...");
				ms.receive(dp);
				String strPacket =  new String(dp.getData());
				System.out.println("Result: " + strPacket);
				FrequentUpdate.getMSGQueue().enqueueString(strPacket);
			}catch(IOException ie){}
		}
		
		
		/*
		while(true){
			DatagramSocket datagramSocket;
			try {
				datagramSocket = new DatagramSocket( userPort );

				byte[] packetBuffer = new byte[1024];
				DatagramPacket datagramPacket = new DatagramPacket( packetBuffer , packetBuffer.length );
				
				System.out.println("Waiting...");
				datagramSocket.receive(datagramPacket);
				String strPacket = new String( datagramPacket.getData(), 0, datagramPacket.getLength() );
				
				System.out.println("Result: " + strPacket);
				FrequentUpdate.getMSGQueue().enqueueString(strPacket);
				
				datagramSocket.close();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}
}
