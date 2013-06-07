package CC_PRJ.SSM.Server.FrqMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FrequentUpdate {
	private boolean lockFlag = false;
	private int port = 35000;
	
	public FrequentUpdate() {

	}

	public void run(){
		System.out.println("Start Frequently Update Mode!!!");

		while(true){
			listen();
		}
	}
	
	private void listen() {
		try {
			ServerSocket server;
			server = new ServerSocket(port);
			System.out.println("Waiting Locking Accpet!");
			Socket sock = server.accept();
			BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
			PrintWriter out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );
			
			String request = in.readLine();
			
			if(request.compareTo("LOCK_REQUEST") == 0) {
				System.out.println("LOCKING REQUEST");
				if(lockFlag == true) {
					System.out.println("REQUEST REJECT");
					out.println("LOCK_REJECT");
					out.flush();
				}
				else if(lockFlag == false){
					System.out.println("LOCKING ALLOW");
					out.println("ALLOW_LOCK");
					out.flush();
					lockFlag = true;
				}
			}
			else if(request.compareTo("LOCK_RELEASE") == 0) { 
				System.out.println("LOCKING RELEASE");
				out.println("RELEASE_OK");
				out.flush();
				lockFlag = false;
			}
			
			server.close();
			sock.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
