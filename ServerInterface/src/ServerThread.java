import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ServerThread extends Thread {
	private Socket sock;

	public ServerThread(Socket sock) {
		ServerInterface.incUserNum();
		this.sock = sock;
	}

	public void run() {
		InetAddress inetaddr = sock.getInetAddress();
		System.out.println("IP: " + inetaddr.getHostAddress() + " Connected ");
		try 
		{
			InputStream inputStream = sock.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			OutputStream outputStream = sock.getOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			
			String inputString = null;
			
			System.out.println("!");
			
			while( (inputString = bufferedReader.readLine()) != null ){
				System.out.println("Income Message: " + inputString);
				System.out.println("#");
				bufferedWriter.write(inputString);
				bufferedWriter.flush();
			}
			
			ServerInterface.decUserNum();
			bufferedReader.close();
			bufferedWriter.close();
			sock.close();
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
	}
}
