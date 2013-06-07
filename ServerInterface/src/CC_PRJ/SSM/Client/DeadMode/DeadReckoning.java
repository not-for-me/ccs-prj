package CC_PRJ.SSM.Client.DeadMode;

import CC_PRJ.AnimateLogic.BallMover;
import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.UserConnInfo;
import CC_PRJ.Interface.Client.ClientInterface;
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
		ball.redraw(200,200);
		RepaintThread repaintThread = new RepaintThread(ball);
		repaintThread.start();
		
		while(ClientInterface.getInstance().getCloseFlag() == ClientInterface.FALSE) {
			
			while(ClientInterface.getMSGQueue().getQueue().isEmpty() != true) {
				String output = ClientInterface.getMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();

				switch(msg.getMsgType()) {
				case Message.UPDATE_SERVER:
					System.out.println("[[Update Server Packet] is comming!!!");
					ball.setBall(msg.getBall());
					ball.redraw();
					ball.repaint();
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
