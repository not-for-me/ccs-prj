package CC_PRJ.SSM.Client.DeadMode;

import CC_PRJ.AnimateLogic.BallMover;
import CC_PRJ.DataModel.Ball;
import CC_PRJ.SSM.SharedMode;

public class RepaintThread extends Thread{
	private int convergenceFrame = 10;

	private BallMover ball;
	private Ball pastInfo = new Ball();
	private Ball newInfo = new Ball();

	public RepaintThread(BallMover ball) {
		this.ball = ball;
	}

	public void run() {
		while(true) {
			try { Thread.sleep(SharedMode.DELAY_TIME); } catch (InterruptedException e) { System.out.println("Interrupted"); }
			/*
			if(pastInfo.getVel_x() != ball.getBall().getVel_x() || pastInfo.getVel_y() != ball.getBall().getVel_y() ) {
				convergenceState();
			}
			else {
			*/
				ball.move(1);
				ball.redraw();
				pastInfo = ball.getBall();
			//}
		}
	}

	private void convergenceState() {
		System.out.println("Convergence!!!");
		newInfo = ball.getBall();

		int x_gap = 0;
		int y_gap = 0;

		for(int i = 0; i < convergenceFrame; i++){
			ball.move(1);
		}
		Ball futureInfo = ball.getBall();

		x_gap = futureInfo.getPos_x() - pastInfo.getPos_x();
		y_gap = futureInfo.getPos_y() - pastInfo.getPos_y();

		System.out.println("X gap: " + x_gap + "Y gap: " + y_gap);
		
		boolean xFlag = false;
		boolean yFlag = false;

		int calibrationNum = 0;
		ball.setBall(pastInfo);
		
		while( !(xFlag == true && yFlag == true) ){
			
			if(xFlag == false && x_gap > 0){
				ball.getBall().setPos_x( ball.getBall().getPos_x() +  calibrationNum);
				System.out.println("X Convergence Result: " + ball.getBall().getPos_x());
			}
			else{
				ball.getBall().setPos_x( ball.getBall().getPos_x() - calibrationNum);
				System.out.println("X Convergence Result: " + ball.getBall().getPos_x());
			}

			if(ball.getBall().getPos_x() >= futureInfo.getPos_x()){
				System.out.println("X Convergence OK!!!");
				ball.getBall().setPos_x(futureInfo.getPos_x());
				xFlag = true;
			}

			if(yFlag == false && y_gap > 0) {
				ball.getBall().setPos_y( ball.getBall().getPos_y() +  calibrationNum);
				System.out.println("Y Convergence Result: " + ball.getBall().getPos_y());
			}
			else {
				ball.getBall().setPos_y( ball.getBall().getPos_y() - calibrationNum);
				System.out.println("Y Convergence Result: " + ball.getBall().getPos_y());
			}

			if(ball.getBall().getPos_y() >= futureInfo.getPos_y()){
				System.out.println("Y Convergence OK!!!");
				ball.getBall().setPos_y(futureInfo.getPos_y());
				yFlag = true;
			}
			
			ball.redraw();
			calibrationNum++;
			try { Thread.sleep(SharedMode.DELAY_TIME); } catch (InterruptedException e) { System.out.println("Interrupted"); }
		}
		
		if(calibrationNum >= convergenceFrame){
			System.out.println("Shortage of Calibration");
		}
		else {
			System.out.println("Wait More Time");
			try { Thread.sleep(SharedMode.DELAY_TIME * (convergenceFrame - calibrationNum ) ); } catch (InterruptedException e) { System.out.println("Interrupted"); }
		}
		
		ball.getBall().setVel_x(newInfo.getVel_x());
		ball.getBall().setVel_y(newInfo.getVel_y());
	}
}
