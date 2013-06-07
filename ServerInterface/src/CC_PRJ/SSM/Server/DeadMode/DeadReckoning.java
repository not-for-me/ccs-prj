package CC_PRJ.SSM.Server.DeadMode;

import java.io.PrintWriter;
import java.util.Iterator;

import CC_PRJ.AnimateLogic.BallMover;
import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Server.ServerInterface;
import CC_PRJ.SSM.SharedMode;

public class DeadReckoning {
	private int delayTime = 200;
	private boolean newInfoFlag = false;
	
	public DeadReckoning(){
	}
	
	public void run(){
		System.out.println("Start Dead Reckoning Mode!!!");
		
		SendThread sendThread = new SendThread();
		sendThread.run();
		final BallMover ball = new BallMover(SharedMode.DEAD_MODE);
		
		while(true) {
			try { Thread.sleep(delayTime); } catch (InterruptedException e) { System.out.println("Interrupted"); };
			
			while(ServerInterface.getUserMSGQueue().getQueue().isEmpty() != true) {
				String output = ServerInterface.getUserMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();

				
				switch(msg.getMsgType()) {
				case Message.UPDATE_USER:
					System.out.println("[[Update User Packet] is comming!!!");
					ball.setBall(msg.getBall());
					break;
				default:
					break;
				}// Close Switch Statement
				newInfoFlag = true;
			}// Close While Statement (Queue Info)

			ball.move(1);
						
			if(newInfoFlag == true) {
				Iterator<UserConnInfo> iter = ServerInterface.getUserConnInfoList().iterator();
				while( iter.hasNext() ) {
					UserConnInfo userInfo = (UserConnInfo) iter.next();

					PrintWriter out = userInfo.getOut();
					String sendMSG = "1/";
					sendMSG = sendMSG.concat(ball.getBall().getBallInfoInString());
					System.out.println("Sending MSG: " + sendMSG);
					out.println(sendMSG);
					out.flush();
				}
				newInfoFlag = false;
			}
			
			
		}// Close While Statement
	}// Close Run Method
}
