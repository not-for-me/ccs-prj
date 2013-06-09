package CC_PRJ.SSM.Server.AbsMode;

import java.io.PrintWriter;
import java.util.Iterator;

import CC_PRJ.AnimateLogic.BallMover;
import CC_PRJ.DataModel.Ball;
import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Component.BallMoverWindow;
import CC_PRJ.Interface.Server.ServerInterface;

public class AbsoluteConsistency {
	private Ball ballInformation;

	public AbsoluteConsistency(){
		ballInformation = new Ball();
		ballInformation.setPos_x( (BallMoverWindow.FRAME_WIDTH - BallMover.DIAMETER) / 2 );
		ballInformation.setPos_y( (BallMoverWindow.FRAME_HEIGHT - BallMover.DIAMETER) / 2 );
	}

	public void run(){
		System.out.println("Start Absolute Consistency Mode!!!");

		int tempUserNum = 0;

		/*
		Iterator<UserConnInfo> iter1 = ServerInterface.getUserConnInfoList().iterator();
		while( iter1.hasNext() ) {
			UserConnInfo userInfo = (UserConnInfo) iter1.next();

			PrintWriter out = userInfo.getOut();
			String sendMSG = "5/";
			sendMSG = sendMSG.concat(ballInformation.getBallInfoInString());
			System.out.println("Sending MSG: " + sendMSG);
			out.println(sendMSG);
			out.flush();
		}
		 */

		while(true) {

			if(ServerInterface.getUserMSGQueue().getQueue().isEmpty() != true) {
				String output = ServerInterface.getUserMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();

				switch(msg.getMsgType()) {
				case Message.UPDATE_USER:
					System.out.println("[[Update User Packet] is comming!!!");
					ballInformation = msg.getBall();

					if(ServerInterface.getUserConnInfoList().size() == 1) {
						System.out.println("Just One User!!!");
						PrintWriter out1 = ServerInterface.getUserConnInfoList().get(msg.getUserID()).getOut();
						String sendMSG1 = "4/OK";
						System.out.println("Sending MSG: " + sendMSG1);
						out1.println(sendMSG1);
						out1.flush();
					}
					else if(ServerInterface.getUserConnInfoList().size() > 1){
						System.out.println("Many User!!!");
						Iterator<UserConnInfo> iter = ServerInterface.getUserConnInfoList().iterator();
						while( iter.hasNext() ) {
							UserConnInfo userInfo = (UserConnInfo) iter.next();

							if(userInfo.getId() == msg.getUserID())
								continue;

							PrintWriter out = userInfo.getOut();
							String sendMSG = "1/";
							sendMSG = sendMSG.concat(ballInformation.getBallInfoInString());
							System.out.println("Sending MSG: " + sendMSG);
							out.println(sendMSG);
							out.flush();
						}
					}
					break;
				case Message.REQUIRE_INFO:
					System.out.println("[Require] is comming!!!");
					PrintWriter out =  ServerInterface.getUserConnInfoList().get(msg.getUserID()).getOut();
					String sendMSG = "5/";
					sendMSG = sendMSG.concat(ballInformation.getBallInfoInString());
					System.out.println("Sending MSG: " + sendMSG);
					out.println(sendMSG);
					out.flush();
					break;
				case Message.RCV_ACK:
					System.out.println("[[ACK Packet] is comming!!!");

					if(tempUserNum < (ServerInterface.getUserConnInfoList().size() - 1))
						tempUserNum++;

					System.out.println("Current RCV ACK NUM: " + tempUserNum);

					if(tempUserNum == (ServerInterface.getUserConnInfoList().size() - 1)) {
						System.out.println("RCV ACK NUM is fullfilled");
						tempUserNum = 0;

						Iterator<UserConnInfo> iter2 = ServerInterface.getUserConnInfoList().iterator();
						while( iter2.hasNext() ) {
							UserConnInfo userInfo = (UserConnInfo) iter2.next();
							PrintWriter out1 = userInfo.getOut();
							String sendMSG1 = "4/OK";
							System.out.println("Sending MSG: " + sendMSG1);
							out1.println(sendMSG1);
							out1.flush();
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
