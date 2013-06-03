package CC_Client.Model.SSM;

import CC_Client.GUI.BallMoverWindow;
import CC_Client.GUI.Component.BallMover;
import CC_Client.Interface.ClientInterface;
import CC_Server.Model.Message;
import CC_Server.Model.MessageParser;

public class AbsoluteConsistency {
	ClientInterface userInfo;
	
	public AbsoluteConsistency(ClientInterface userInfo) {
		this.userInfo = userInfo;
	}
	
	public void run() {
		BallMoverWindow ballMoverWindow = new BallMoverWindow();
		ballMoverWindow.ballWindow();
		
		final BallMover ball = new BallMover();
		ballMoverWindow.getBallMoverFrame().add(ball);
		ballMoverWindow.getBallMoverFrame().addKeyListener(ball);
		
		while(true) {
			/*
			System.out.print("Input Message: ");
			inputString =ClientInterface.stdin.readLine();
			if(inputString.compareTo("q") == 0)
				break;
			*/
			if(ClientInterface.getMSGQueue().getQueue().isEmpty() != true) {
				String output = ClientInterface.getMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();
				
				switch(msg.getMsgType()) {
				case Message.UPDATE_SERVER:
					System.out.println("Rcv Packet is Update Server Packet!!!");
					ball.setBall(msg.getBall());
					System.out.println("Repaint!!!");
					ball.repaint();
					break;
				case Message.RCV_ACK:
					break;
				default:
					break;
				}
			}
			
			if( ball.isKeyPressed() ) {
				ball.move(1);
				String sendMSG = "2/";
				sendMSG = sendMSG.concat( Integer.toString( userInfo.getUserID() ) );
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
