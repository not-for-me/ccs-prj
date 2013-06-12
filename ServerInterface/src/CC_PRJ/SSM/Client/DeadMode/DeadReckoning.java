package CC_PRJ.SSM.Client.DeadMode;

import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Client.ClientInterface;
import CC_PRJ.Interface.Component.BallMover;
import CC_PRJ.Interface.Component.BallMoverWindow;
import CC_PRJ.SSM.SharedMode;

public class DeadReckoning {
	UserConnInfo userInfo;
	
	public DeadReckoning(UserConnInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void run() {
		BallMoverWindow ballMoverWindow = new BallMoverWindow();
		ballMoverWindow.ballWindow();

		final BallMover ball = new BallMover(SharedMode.DEAD_MODE);
		ballMoverWindow.getBallMoverFrame().add(ball);
		ballMoverWindow.getBallMoverFrame().addKeyListener(ball);
		
		System.out.println("Require Default Info ");
		String df = "6/";
		df = df.concat( Integer.toString( userInfo.getId() ) );
		df = df.concat("/Default Info");
		System.out.println("Sending MSG: " + df);
		userInfo.getOut().println(df);
		userInfo.getOut().flush();
		
		
		while(ClientInterface.getInstance().getCloseFlag() == ClientInterface.FALSE) {
			
			while(ClientInterface.getMSGQueue().getQueue().isEmpty() != true) {
				String output = ClientInterface.getMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();

				switch(msg.getMsgType()) {
				case Message.DEFAULT_INFO:
					System.out.println("[DEFAULT INFO Packet] is comming!!!");
					try { Thread.sleep(100); } catch (InterruptedException e) { System.out.println("Interrupted"); }
					ball.setBall(msg.getBall());
					ball.redraw();
					RepaintThread repaintThread = new RepaintThread(ball);
					repaintThread.start();
					break;	
				case Message.UPDATE_SERVER:
					System.out.println("[[Update Server Packet] is comming!!!");
					ball.setBall(msg.getBall());
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
