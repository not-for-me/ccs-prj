package CCS.Model;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserConnInfo {
	private int id;
	private Socket sock;
	private BufferedReader in;
	private PrintWriter out;
	
	public UserConnInfo(int id,	Socket sock, BufferedReader in,  PrintWriter out){
		this.id = id;
		this.sock = sock;
		this.in = in;
		this.out = out;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Socket getSock() {
		return sock;
	}
	public void setSock(Socket sock) {
		this.sock = sock;
	}
	public PrintWriter getOut() {
		return out;
	}
	public BufferedReader getIn() {
		return in;
	}
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	public void setOut(PrintWriter out) {
		this.out = out;
	}
}
