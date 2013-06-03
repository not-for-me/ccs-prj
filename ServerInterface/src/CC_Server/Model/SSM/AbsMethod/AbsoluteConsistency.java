package CC_Server.Model.SSM.AbsMethod;

import java.io.PrintWriter;
import java.util.Iterator;

import CC_Server.Interface.ServerInterface;
import CC_Server.Model.Ball;
import CC_Server.Model.Message;
import CC_Server.Model.MessageParser;
import CC_Server.Model.UserConnInfo;

public class AbsoluteConsistency {
	private Ball ball;	
	private int userNum;
	private int[] updateFlagArr; 
	
	public AbsoluteConsistency(){
		ball = new Ball();
	}

	public void run(){
		userNum = ServerInterface.getUserConnInfoList().size();
		updateFlagArr = new int[userNum];

		while(true) {
			if(ServerInterface.getUserMSGQueue().getQueue().isEmpty() != true) {
				String output = ServerInterface.getUserMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();

				switch(msg.getMsgType()) {
				case Message.UPDATE_USER:
					System.out.println("Rcv Packet is Update User Packet!!!");
					
					Iterator<UserConnInfo> iter = ServerInterface.getUserConnInfoList().iterator();
					
					while( iter.hasNext() ) {
						UserConnInfo userInfo = (UserConnInfo) iter.next();
						
						if(userInfo.getId() == msg.getUserID())
							continue;
						
						PrintWriter out = userInfo.getOut();
						String sendMSG = "1/";
						sendMSG = sendMSG.concat(msg.getMsgContent());
						System.out.println("Sending MSG: " + sendMSG);
						out.println(sendMSG);
						out.flush();
					}
					
					
					break;
				case Message.RCV_ACK:
					System.out.println("Rcv Packet is Rcv ACK Packet!!!");
					break;
				default:
					break;
				}
			}
		}
	}
}
