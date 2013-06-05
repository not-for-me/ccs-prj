package CC_PRJ.SSM.Client;

import CC_PRJ.AnimateLogic.BallMover;
import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.Interface.Client.ClientInterface;
import CC_PRJ.Interface.Component.BallMoverWindow;
import CC_PRJ.SSM.SharedMode;

public class FrequentUpdate {

	public FrequentUpdate() {
	}

	public void run(){
		BallMoverWindow ballMoverWindow = new BallMoverWindow();
		ballMoverWindow.ballWindow();

		final BallMover ball = new BallMover(SharedMode.ABS_MODE);
		ballMoverWindow.getBallMoverFrame().add(ball);
		ballMoverWindow.getBallMoverFrame().addKeyListener(ball);

			if(ClientInterface.getMSGQueue().getQueue().isEmpty() != true) {

			}

		if( ball.isKeyPressed() ) {

			ball.setKeyFlag(false);
		}
	}

}

