package CC_Server.Model;

import java.util.StringTokenizer;

public class MessageParser {
	String input;
	Message msg;
	
	public MessageParser(String input) {
		this.input = input;
		this.msg = new Message();
	}
	
	public Message parseMessage() {
		StringTokenizer str = new StringTokenizer(input, "/");
		
		msg.setMsgType( Integer.parseInt( str.nextToken() ) );
		
		if(msg.getMsgType() != Message.UPDATE_SERVER)
			msg.setUserID(  Integer.parseInt( str.nextToken() ) );
		
		msg.setMsgContent( str.nextToken() );
		System.out.println("Parsed Result");
		System.out.println("MSG Type: " + msg.getMsgType());
		System.out.println("User ID: " + msg.getUserID());
		System.out.println("MSG Content: " + msg.getMsgContent());
		
		if(msg.getMsgType() == Message.UPDATE_USER) {
			String locationStr =  msg.getMsgContent();
			StringTokenizer locStr = new StringTokenizer(locationStr, ",");
			msg.getBall().setPos_x( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setPos_y( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setVel_x( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setVel_y( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setAcc_x( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setAcc_x( Integer.parseInt( locStr.nextToken() ) );
			
			System.out.println("Ball position of x: " + msg.getBall().getPos_x());
			System.out.println("Ball position of y: " + msg.getBall().getPos_y());
			System.out.println("Ball velocity of x: " + msg.getBall().getVel_x());
			System.out.println("Ball velocity of y: " + msg.getBall().getVel_y());
			System.out.println("Ball accelorator of x: " + msg.getBall().getAcc_x());
			System.out.println("Ball accelorator of y: " + msg.getBall().getAcc_y());
		}
		return msg;
	}
}
