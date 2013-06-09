package CC_PRJ.AnimateLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import CC_PRJ.DataModel.Ball;
import CC_PRJ.Interface.Component.BallMoverWindow;
import CC_PRJ.SSM.SharedMode;

public class BallMover extends JPanel implements KeyListener{
	private static final long serialVersionUID = -4575223266375770758L;
	public final static int DIAMETER = 10;
	private Ball ball;
	
	private int mode;

	private boolean keyFlag = false;
	Color color;

	public BallMover(int mode) {
		super();
		this.ball = new Ball();
		this.color = Color.magenta;
		this.mode = mode;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillOval(0, 0, DIAMETER, DIAMETER);
	}

	public void move(int time) {
		MovingBallAlgorithm alg = new MovingBallAlgorithm();
		int new_pos_x = ball.getPos_x();
		int new_pos_y = ball.getPos_y();

		switch(mode){
		case SharedMode.ABS_MODE:
			System.out.println("[Move]Here is Absolute Consistency Mode!");
			if (new_pos_x > 0 && new_pos_x + DIAMETER < BallMoverWindow.FRAME_WIDTH)
				new_pos_x = alg.firstOrderPolynomial(time, getX(), 1);

			if (new_pos_y < 0 && new_pos_y + DIAMETER < BallMoverWindow.FRAME_HEIGHT)
				new_pos_y = alg.firstOrderPolynomial(time, getY(), 1);
			break;
		case SharedMode.FRQ_MODE:
			System.out.println("[Move]Here is Frequently State Update Mode!");
			break;
		case SharedMode.DEAD_MODE:
			//System.out.println("[Move]Here is Dead Reckoning Mode!");
			/*
			System.out.println("Time Value: " + time);
			System.out.println("Before. Pos X: " + new_pos_x + " Pos Y: " + new_pos_y);
			System.out.println("Before. Vel X: " +  ball.getVel_x() + " Vel Y: " +  ball.getVel_y());
			System.out.println("Before. Acc X: " +  ball.getAcc_x() + " Acc Y: " +  ball.getAcc_y());
			*/
			new_pos_x = alg.firstOrderPolynomial(1, ball.getPos_x(), ball.getVel_x());
			new_pos_y = alg.firstOrderPolynomial(1, ball.getPos_y(), ball.getVel_y());
			
			if (new_pos_x < 0 || new_pos_x + DIAMETER * 2 > BallMoverWindow.FRAME_WIDTH) {
				//System.out.println("Over Pace Pos X: " + new_pos_x);
				ball.setVel_x( ball.getVel_x() * (-1) );
				//ball.setAcc_x( ball.getAcc_x() * (-1) );
				new_pos_x = alg.firstOrderPolynomial(1, ball.getPos_x(), ball.getVel_x());
			}
			
			if (new_pos_y < 0 || new_pos_y + DIAMETER * 3 > BallMoverWindow.FRAME_HEIGHT){
				//System.out.println("Over Pace Pos Y: " + new_pos_y);
				ball.setVel_y( ball.getVel_y() * (-1) );
				//ball.setAcc_y( ball.getAcc_y() * (-1) );
				new_pos_y = alg.firstOrderPolynomial(1, ball.getPos_y(), ball.getVel_y());
			}
			
			//System.out.println("Cal. Pos X: " + new_pos_x + " Pos Y: " + new_pos_y);
			break;
		default:
			break;
		}
		ball.setPos_x( new_pos_x );
		ball.setPos_y( new_pos_y );
	}

	public void redraw() {
		//System.out.println("====Redraw Info====");
		//ball.printBallInfo();
		this.setLocation(ball.getPos_x(), ball.getPos_y());
		this.repaint();
	}
	
	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		System.out.println("Pressed: " + keycode);
		keyFlag = true;

		switch(mode){
		case SharedMode.ABS_MODE:
			switch(keycode){
			case KeyEvent.VK_UP:
				if(ball.getPos_y() >= 0)
					ball.setPos_y( ball.getPos_y() - 1 );
				break;
			case KeyEvent.VK_DOWN:
				if(ball.getPos_y() + DIAMETER <= getParent().getHeight())
					ball.setPos_y( ball.getPos_y() + 1 );
				break;
			case KeyEvent.VK_LEFT:
				if(ball.getPos_x() >= 0)
					ball.setPos_x( ball.getPos_x() - 1 );
				break;
			case KeyEvent.VK_RIGHT:
				if(ball.getPos_x() + DIAMETER <= getParent().getWidth())
					ball.setPos_x( ball.getPos_x() + 1 );
				break;
			}
			break;
		case SharedMode.FRQ_MODE:
		case SharedMode.DEAD_MODE:
			switch(keycode){
			case KeyEvent.VK_UP:
				ball.setVel_y( ball.getVel_y() - 1 );
				break;
			case KeyEvent.VK_DOWN:
				ball.setVel_y( ball.getVel_y() + 1 );
				break;
			case KeyEvent.VK_LEFT:
				ball.setVel_x( ball.getVel_x() - 1 );
				break;
			case KeyEvent.VK_RIGHT:
				ball.setVel_x( ball.getVel_x() + 1 );
				break;
				/*
			case KeyEvent.VK_HOME:
				ball.setAcc_x( ball.getAcc_x() + 1 );
				break;
			case KeyEvent.VK_END:
				ball.setAcc_x( ball.getAcc_x() - 1 );
				break;
			case KeyEvent.VK_PAGE_UP:
				ball.setAcc_y( ball.getAcc_y() + 1 );
				break;
			case KeyEvent.VK_PAGE_DOWN:
				ball.setAcc_y( ball.getAcc_y() - 1 );
				break;
				*/
			}
			break;
		default:
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

	public boolean isKeyPressed() {
		return keyFlag;
	}

	public void setKeyFlag(boolean keyFlag) {
		this.keyFlag = keyFlag;
	}

	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
}