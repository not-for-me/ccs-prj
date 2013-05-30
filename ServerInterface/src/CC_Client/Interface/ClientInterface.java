package CC_Client.Interface;

import CC_Client.GUI.Component.LoginWindow;
import CC_Client.Model.Method.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInterface {
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
	public final static int ABS_MODE = 1;
	public final static int FRQ_MODE = 2;
	public final static int DEAD_MODE = 3;
	public final static int TRUE = 1;
	public final static int FALSE = 0;

	private static ClientInterface instance = new ClientInterface();

	private String address;
	private int port;
	private int loginFlag = FALSE;
	private Socket sock;
	private int sharedMode;
	private PrintWriter out;
	private BufferedReader in;


	private ClientInterface() {
	}		

	public static ClientInterface getInstance() {
		return instance;
	}

	private void connectServer() {
		LoginWindow.getInstance().drawLoginWindow();

		while(loginFlag == FALSE) {}
		try {
			sock  = new Socket(address, port);
			out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );
			in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(int loginFlag) {
		this.loginFlag = loginFlag;
	}

	private void deceiveSharedStateMethod() {
		try {
			String mode;
			mode = in.readLine();

			System.out.println("Received Shrared Stated Mode from Server: " + mode);
			sharedMode = Integer.parseInt(mode);

			switch(sharedMode){
			case ABS_MODE:
				System.out.println("Here is Absolute Consistency Mode!");
				AbsoluteConsistency absMode = new AbsoluteConsistency();
				break;
			case FRQ_MODE:
				System.out.println("Here is Frequently State Update Mode!");
				FrequentUpdate frqMode = new FrequentUpdate();
				break;
			case DEAD_MODE:
				System.out.println("Here is Dead Reckoning Mode!");
				DeadReckoning deadMode = new DeadReckoning();
				break;
			default:
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*
	private void testChat() {
		try {

			String inputString = null;

			while(true) {
				System.out.print("Input Message: ");
				inputString = stdin.readLine();

				if(inputString.compareTo("q") == 0)
					break;

				out.println(inputString);
				out.flush();

				String result = in.readLine();
				System.out.println("Return Message: " + result);
			}

			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	public static void main(String[] args) {
		ClientInterface.getInstance().connectServer();
		ClientInterface.getInstance().deceiveSharedStateMethod();

	}
}
