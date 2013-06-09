package CC_PRJ.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Component.WindowManager;
import CC_PRJ.Interface.Server.ReceiveThread;
import CC_PRJ.Interface.Server.ServerInterface;

public class SocketListen extends Thread {
	private int sockID = 0;
	
	public SocketListen(){
	}

	public void run() {
		
		this.listen();
	}
	
	private void listen() {
		ServerSocket server;
		try {
			server = new ServerSocket(ServerInterface.getInstance().getPort());
			
			while(true) {
				System.out.println("Listen...");
				Socket sock = server.accept();
				BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
				PrintWriter out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );

				UserConnInfo newComer = new UserConnInfo(sockID, sock, in, out);
				ServerInterface.getUserConnInfoList().add(newComer);
				System.out.println("New User Detected! Num of Users: " + ServerInterface.getUserConnInfoList().size());
				System.out.println("User's ID: " + newComer.getId());
				
				ReceiveThread communicationThread = new ReceiveThread(newComer);
				communicationThread.start();
				WindowManager.getInstance().getMiddle().getUserTextField().setText(Integer.toString( ServerInterface.getUserConnInfoList().size() ));
				ServerInterface.getInstance().sendUserIDInfo(newComer);
				sockID++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("User Connection End\nTotal User Number: " + ServerInterface.getUserConnInfoList().size() );
	}
}
