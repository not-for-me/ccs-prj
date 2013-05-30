import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CommThread extends Thread {
	private int id;
	private Socket sock;
	
	public CommThread(int id, Socket sock) {
		this.id = id;
		this.sock = sock;
	}

	public void run() {
		System.out.println("Communication Thread Run!!");
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
			
			ServerInterface.getSocketMap().remove(new Integer(id));
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
