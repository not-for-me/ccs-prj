package CC_Server.Model;

public class Message {
	public final static int UPDATE_SERVER = 1;
	public final static int UPDATE_USER = 2;
	public final static int RCV_ACK = 3;
	public final static int ALLOW_VIEW = 4;
	
	private int msgType;
	private int userID;
	private String msgContent;
	private Ball ball;
	
	public Message() {
		this.msgType = 0;
		this.userID = 0;
		this.msgContent = "";
		ball = new Ball();
	}

	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
}
