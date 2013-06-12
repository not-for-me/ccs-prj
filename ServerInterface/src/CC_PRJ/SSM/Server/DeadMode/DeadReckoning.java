package CC_PRJ.SSM.Server.DeadMode;

import java.io.PrintWriter;
import java.util.Iterator;

import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Component.BallMover;
import CC_PRJ.Interface.Component.BallMoverWindow;
import CC_PRJ.Interface.Component.WindowManager;
import CC_PRJ.Interface.Server.ServerInterface;
import CC_PRJ.SSM.SharedMode;

public class DeadReckoning {
	private int frameCount = 0;
	private int pktCount = 0;
	
	public DeadReckoning(){
	}
	
	public void run(){
		System.out.println("Start Dead Reckoning Mode!!!");
		final BallMover ball = new BallMover(SharedMode.DEAD_MODE);
		ball.getBall().setPos_x((BallMoverWindow.FRAME_WIDTH - BallMover.DIAMETER) / 2);
		ball.getBall().setPos_y((BallMoverWindow.FRAME_HEIGHT - BallMover.DIAMETER) / 2);
		
		while(true) {
			try { Thread.sleep(SharedMode.DELAY_TIME); } catch (InterruptedException e) { System.out.println("Interrupted"); };
			
			while(ServerInterface.getUserMSGQueue().getQueue().isEmpty() != true) {
				String output = ServerInterface.getUserMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();
				
				switch(msg.getMsgType()) {
				case Message.UPDATE_USER:
					System.out.println("[[Update User Packet] is comming!!!");
					ball.setBall(msg.getBall());
					pktCount++;
					WindowManager.getInstance().getBottom().getPktNumTextField().setText(Integer.toString(pktCount));
					break;
				case Message.REQUIRE_INFO:
					System.out.println("[Require] is comming!!!");
					PrintWriter out =  ServerInterface.getUserConnInfoList().get(msg.getUserID()).getOut();
					String sendMSG = "5/";
					sendMSG = sendMSG.concat(ball.getBall().getBallInfoInString());
					System.out.println("Sending MSG: " + sendMSG);
					out.println(sendMSG);
					out.flush();
					pktCount++;pktCount++;
					WindowManager.getInstance().getBottom().getPktNumTextField().setText(Integer.toString(pktCount));
					break;
				default:
					break;
				}// Close Switch Statement
				//frameFlag = true;
			}// Close While Statement (Queue Info)
			ball.move();
			frameCount++;
			
			if(frameCount == SharedMode.FRAME_COUNT * SharedMode.UPDATE_THRESHOLD_SEC) {
				System.out.println("Frame Count Full " + frameCount);
				Iterator<UserConnInfo> iter = ServerInterface.getUserConnInfoList().iterator();
				while( iter.hasNext() ) {
					UserConnInfo userInfo = (UserConnInfo) iter.next();

					PrintWriter out = userInfo.getOut();
					String sendMSG = "1/";
					sendMSG = sendMSG.concat(ball.getBall().getBallInfoInString());
					System.out.println("Sending MSG: " + sendMSG);
					out.println(sendMSG);
					out.flush();
					pktCount++;
					WindowManager.getInstance().getBottom().getPktNumTextField().setText(Integer.toString(pktCount));
				}
				frameCount = 0;
			}
			
		}// Close While Statement
	}// Close Run Method
}
