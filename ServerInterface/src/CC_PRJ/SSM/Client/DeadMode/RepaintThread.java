package CC_PRJ.SSM.Client.DeadMode;

/*
 * 매 프레임마다 공의 속도에 따라 위치를 변경(Prediction)해서 다시 화면을 그림
 */

import CC_PRJ.Interface.Component.BallMover;
import CC_PRJ.SSM.SharedMode;

public class RepaintThread extends Thread{

	private BallMover ball;

	public RepaintThread(BallMover ball) {
		this.ball = ball;
	}

	public void run() {
		while(true) {
			try { Thread.sleep(SharedMode.DELAY_TIME); } catch (InterruptedException e) { System.out.println("Interrupted"); }

			ball.move();
			ball.redraw();
		}
	}
}
