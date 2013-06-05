package CC_PRJ.SSM.Server;

import java.io.PrintWriter;
import java.util.Iterator;

import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Server.ServerInterface;

public class AbsoluteConsistency {
	private int userNum;
	//private boolean[] updateFlagArr; 

	public AbsoluteConsistency(){
		//ball = new Ball();
	}

	public void run(){
		System.out.println("Start Abs Mode");
		userNum = ServerInterface.getUserConnInfoList().size();
		//updateFlagArr = new boolean[userNum];
		int tempUserNum = 0;
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
					/*

					while(tempUserNum < (userNum - 1)){
						if(ServerInterface.getUserMSGQueue().getQueue().isEmpty() != true) {


							tempUserNum++;
						}
					}
					 */

					break;
				case Message.RCV_ACK:
					System.out.println("Rcv Packet is Rcv ACK Packet\n User: " + tempUserNum);

					if(tempUserNum < (userNum - 1)) {
						//updateFlagArr[msg.getUserID()] = true;
						tempUserNum++;
					}

					if(tempUserNum == (userNum - 1)) {
						tempUserNum = 0;
						
						Iterator<UserConnInfo> iter2 = ServerInterface.getUserConnInfoList().iterator();
						while( iter2.hasNext() ) {
							UserConnInfo userInfo = (UserConnInfo) iter2.next();
							PrintWriter out = userInfo.getOut();
							String sendMSG = "4/OK";
							System.out.println("Sending MSG: " + sendMSG);
							out.println(sendMSG);
							out.flush();
						}
					}
					break;
				default:
					break;
				}// Close Switch Statement
			}// Close If Statement (Queue Info)
		}// Close While Statement
	}// Close Run Method
}
