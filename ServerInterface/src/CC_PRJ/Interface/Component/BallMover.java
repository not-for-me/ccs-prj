package CC_PRJ.Interface.Component;

/*
 * 공의 움직임을 관리하는 클래스, 사용자의 입력에 따라 공의 움직임을 변경 함
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import CC_PRJ.DataModel.Ball;
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

	// 설정 모드에 따라 움짐임을 변경 함
	public void move() {
		int new_pos_x = ball.getPos_x();
		int new_pos_y = ball.getPos_y();

		switch(mode){
		case SharedMode.ABS_MODE:
			System.out.println("[Move]Here is Absolute Consistency Mode!");
			if (new_pos_x > 0 && new_pos_x + DIAMETER < BallMoverWindow.FRAME_WIDTH)
				new_pos_x = getX() + 1;
			
			if (new_pos_y < 0 && new_pos_y + DIAMETER < BallMoverWindow.FRAME_HEIGHT)
				new_pos_y = getY() + 1;
			break;
			
		case SharedMode.FRQ_MODE:
			System.out.println("[Move]Here is Frequently State Update Mode!");
			break;
			
		case SharedMode.DEAD_MODE:
			new_pos_x = ball.getPos_x() + ball.getVel_x();
			new_pos_y = ball.getPos_y() + ball.getVel_y();
			
			if (new_pos_x < 0 || new_pos_x + DIAMETER * 2 > BallMoverWindow.FRAME_WIDTH) {
				ball.setVel_x( ball.getVel_x() * (-1) );
				new_pos_x = ball.getPos_x() + ball.getVel_x();
			}
			
			if (new_pos_y < 0 || new_pos_y + DIAMETER * 3 > BallMoverWindow.FRAME_HEIGHT){
				ball.setVel_y( ball.getVel_y() * (-1) );
				new_pos_y = ball.getPos_y() + ball.getVel_y();
			}
			
			break;
		default:
			break;
		}
		ball.setPos_x( new_pos_x );
		ball.setPos_y( new_pos_y );
	}

	//화면에 현재 공 위치 정보에 따라 공을 다시 그림
	public void redraw() {
		this.setLocation(ball.getPos_x(), ball.getPos_y());
		this.repaint();
	}
	
	// 사용자의 새로운 키 입력 시 키 정보에 따른 값을 지정 함
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