import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CommThread extends Thread {
	private Socket sock;

	public CommThread(Socket sock) {
		System.out.println("Communication Thread Create, Socket: " + sock);
		this.sock = sock;
	}

	public void run() {
		System.out.println("Communication Thread Run!!");
		InetAddress inetaddr = sock.getInetAddress();
		System.out.println("IP: " + inetaddr.getHostAddress() + " Connected ");
		try 
		{
			BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
			PrintWriter out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );
			
			String inputString = null;
			
			while( ( inputString = in.readLine() ) != null ){
				System.out.println("Income Message: " + inputString);
				out.println(inputString);
				out.flush();
			}
			
			in.close();
			out.close();
			sock.close();
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
	}
}
