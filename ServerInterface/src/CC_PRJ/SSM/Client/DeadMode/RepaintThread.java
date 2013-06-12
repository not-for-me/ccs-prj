package CC_PRJ.SSM.Client.DeadMode;

/*
 * �� �����Ӹ��� ���� �ӵ��� ���� ��ġ�� ����(Prediction)�ؼ� �ٽ� ȭ���� �׸�
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
