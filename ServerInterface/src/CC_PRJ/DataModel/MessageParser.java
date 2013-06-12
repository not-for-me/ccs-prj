package CC_PRJ.DataModel;

/*
 * ������ Ŭ���̾�Ʈ ���� ���۵� �޽��� ��ü�� �Ľ��ϱ� ���� Ŭ���� 
 */

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
		
		if(msg.getMsgType() == Message.REQUIRE_INFO ||msg.getMsgType() == Message.UPDATE_USER || msg.getMsgType() == Message.RCV_ACK)
			msg.setUserID(  Integer.parseInt( str.nextToken() ) );
		
		msg.setMsgContent( str.nextToken() );
		
		printParsingResult();
		
		if(msg.getMsgType() == Message.UPDATE_USER || msg.getMsgType() == Message.UPDATE_SERVER  || msg.getMsgType() == Message.DEFAULT_INFO) {
			String locationStr =  msg.getMsgContent();
			StringTokenizer locStr = new StringTokenizer(locationStr, ",");
			msg.getBall().setPos_x( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setPos_y( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setVel_x( Integer.parseInt( locStr.nextToken() ) );
			msg.getBall().setVel_y( Integer.parseInt( locStr.nextToken() ) );
			//msg.getBall().printBallInfo();
		}
		
		return msg;
	}
	
	private void printParsingResult(){
		System.out.println("\n=======================\nIn Message Parser (Parsing Result)");
		System.out.println("MSG Type: " + msg.getMsgType());
		System.out.println("User ID: " + msg.getUserID());
		System.out.println("MSG Content: " + msg.getMsgContent());
		System.out.println("=======================\n");
	}
}
