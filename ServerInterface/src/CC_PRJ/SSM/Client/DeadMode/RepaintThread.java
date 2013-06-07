package CC_PRJ.SSM.Client.DeadMode;

import CC_PRJ.AnimateLogic.BallMover;

public class RepaintThread extends Thread{
	private int delayTime = 200;
	private BallMover ball;

	
	public RepaintThread(BallMover ball) {
		this.ball = ball;
	}

	public void run() {
		while(true) {
			try { Thread.sleep(delayTime); } catch (InterruptedException e) { System.out.println("Interrupted"); }
			ball.move(1);
			ball.redraw();
			ball.repaint();
		}
	}
}
