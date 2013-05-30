/*
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptThread extends Thread {
	private int port;

	public AcceptThread(int portNum) {
		System.out.println("Accept Thread Create\nPort: " + portNum);
		this.port = portNum;
	}

	public void run() {
		System.out.println("Accept Thread Run!!");
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(port);

			while(true) {
				System.out.println("Listen...");
				Socket sock = server.accept();
				synchronized(ServerInterface.getSocketMap()) {
					ServerInterface.getSocketMap().put(new Integer(ServerInterface.getSockID()), sock);
					System.out.println("Accept: New Client Come\nUser ID: " + ServerInterface.getSockID() + " " + sock);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
*/