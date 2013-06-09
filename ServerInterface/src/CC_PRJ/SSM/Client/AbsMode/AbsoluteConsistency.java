package CC_PRJ.SSM.Client.AbsMode;

import CC_PRJ.AnimateLogic.BallMover;
import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Client.ClientInterface;
import CC_PRJ.Interface.Component.BallMoverWindow;
import CC_PRJ.SSM.SharedMode;

public class AbsoluteConsistency {
	UserConnInfo userInfo;
	
	public AbsoluteConsistency(UserConnInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public void run() {
		BallMoverWindow ballMoverWindow = new BallMoverWindow();
		ballMoverWindow.ballWindow();
		
		final BallMover ball = new BallMover(SharedMode.ABS_MODE);
		ballMoverWindow.getBallMoverFrame().add(ball);
		ballMoverWindow.getBallMoverFrame().addKeyListener(ball);
		//ball.setVisible(false);
		
		System.out.println("Require Default Info ");
		String df = "6/";
		df = df.concat( Integer.toString( userInfo.getId() ) );
		df = df.concat("/Default Info");
		System.out.println("Sending MSG: " + df);
		userInfo.getOut().println(df);
		userInfo.getOut().flush();
		
		
		while(ClientInterface.getInstance().getCloseFlag() == ClientInterface.FALSE) {
			if(ClientInterface.getMSGQueue().getQueue().isEmpty() != true) {
				String output = ClientInterface.getMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();
				
				switch(msg.getMsgType()) {
				case Message.UPDATE_SERVER:
					System.out.println("[Update Server Packet] is comming!!!");
					ball.setBall(msg.getBall());
					
					System.out.println("Send Rcv Ack Message in User " + userInfo.getId() );
					String sendMSG = "3/";
					sendMSG = sendMSG.concat( Integer.toString( userInfo.getId() ) );
					sendMSG = sendMSG.concat("/Acknowledgement");
					System.out.println("Sending MSG: " + sendMSG);
					userInfo.getOut().println(sendMSG);
					userInfo.getOut().flush();
					break;
				case Message.DEFAULT_INFO:
					System.out.println("[DEFAULT INFO Packet] is comming!!!");
					//ball.setVisible(false);
					try { Thread.sleep(100); } catch (InterruptedException e) { System.out.println("Interrupted"); }
					ball.setBall(msg.getBall());
					ball.redraw();
					//ball.setVisible(true);
					//ball.repaint();
					break;	
				case Message.ALLOW_VIEW:
					System.out.println("[[Allow View Packet] is comming!!!");
					ball.redraw();
					//ball.repaint();
					break;
				default:
					break;
				}
			}
			
			if( ball.isKeyPressed() ) {
				String sendMSG = "2/";
				sendMSG = sendMSG.concat( Integer.toString( userInfo.getId() ) );
				sendMSG = sendMSG.concat("/");
				sendMSG =  sendMSG.concat(ball.getBall().getBallInfoInString());
				System.out.println("Sending MSG: " + sendMSG);
				userInfo.getOut().println(sendMSG);
				userInfo.getOut().flush();
				ball.setKeyFlag(false);
				
			}
		}
	}
}
