import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class AcceptThread extends Thread {
	private int port;
	
	public AcceptThread(int portNum) {
		System.out.println("Accept Thread Create, Port: " + portNum);
		this.port = portNum;
	}
	
	public void run() {
		System.out.println("Accept Thread Run!!");
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Listen port: " + port );
			
			while(true) {
				Socket sock = server.accept();
				int sockID = ServerInterface.getSockID();
				ServerInterface.getSockMap().put(new Integer(sockID), sock);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
