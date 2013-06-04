package CC_PRJ.AnimateLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import CC_PRJ.DataModel.Ball;

public class BallMover extends JPanel implements KeyListener{
	private static final long serialVersionUID = -4575223266375770758L;
	private Ball ball;
	private int diameter;
	
	private boolean keyFlag = false;
	Color color;
	
	public BallMover( ) {
		ball = new Ball();
		color = Color.magenta;
		diameter = 10;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillOval(0, 0, diameter, diameter);
	}
	
	public void move(int time) {
		MovingBallAlgorithm alg = new MovingBallAlgorithm();
		
		int new_pos_x = alg.secondOrderPolynomial(time, getX(), ball.getVel_x(), ball.getAcc_x());
		int new_pos_y = alg.secondOrderPolynomial(time, getY(), ball.getVel_y(), ball.getAcc_y());

		if (new_pos_x < 0 || new_pos_x + diameter > getParent().getWidth()) {
			ball.setVel_x( ball.getVel_x() * (-1) );
			ball.setAcc_x( ball.getAcc_x() * (-1) );
			new_pos_x = alg.secondOrderPolynomial(time, getX(), ball.getVel_x(), ball.getAcc_x());
		}
		if (new_pos_y < 0 || new_pos_y + diameter > getParent().getHeight()) {
			ball.setVel_y( ball.getVel_y() * (-1) );
			ball.setAcc_y( ball.getAcc_y() * (-1) );
			new_pos_y = alg.secondOrderPolynomial(time, getY(), ball.getVel_y(), ball.getAcc_y());
		}

		ball.setPos_x(new_pos_x);
		ball.setPos_y(new_pos_y);
		//setLocation(new_pos_x, new_pos_y);
	}
	
	public void redraw() {
		ball.printBallInfo();
		setLocation(ball.getPos_x(), ball.getPos_y());
	}
	
	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		System.out.println("Pressed: " + keycode);
		keyFlag = true;
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