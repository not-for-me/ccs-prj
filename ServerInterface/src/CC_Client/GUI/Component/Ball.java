package CC_Client.GUI.Component;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import CC_Server.Model.SSM.AbsMethod.MovingBallAlgorithm;

public class Ball extends JPanel implements KeyListener{
	private int vel_x;
	private int vel_y;
	private int acc_x;
	private int acc_y;
	private int diameter;
	Color color;
	
	public Ball( ) {
		vel_x = 0;
		vel_y = 0;
		acc_x = 0;
		acc_y = 0;
		color = Color.magenta;
		diameter = 5;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillOval(0, 0, diameter, diameter);
	}
	
	public void move(int time) {
		MovingBallAlgorithm alg = new MovingBallAlgorithm();
		int pos_x = getX();
		int pos_y = getY();
		
		int new_pos_x = alg.secondOrderPolynomial(time, pos_x, vel_x, acc_x);
		int new_pos_y = alg.secondOrderPolynomial(time, pos_y, vel_y, acc_y);

		if (new_pos_x < 0 || new_pos_x + diameter > getParent().getWidth()) {
			vel_x *= -1;
			acc_x *= -1;
			new_pos_x = alg.secondOrderPolynomial(time, pos_x, vel_x, acc_x);
		}
		if (new_pos_y < 0 || new_pos_y + diameter > getParent().getHeight()) {
			vel_y *= -1;
			acc_y *= -1;
			new_pos_y = alg.secondOrderPolynomial(time, pos_y, vel_y, acc_y);
		}

		setLocation(new_pos_x, new_pos_y);
	}
	
	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		System.out.println("Pressed: " + keycode);
		switch(keycode){
		case KeyEvent.VK_UP:
			vel_y -= 1;
			break;
		case KeyEvent.VK_DOWN:
			vel_y += 1;
			break;
		case KeyEvent.VK_LEFT:
			vel_x -= 1;
			break;
		case KeyEvent.VK_RIGHT:
			vel_x += 1;
			break;
		case KeyEvent.VK_HOME:
			acc_x += 1;
		case KeyEvent.VK_END:
			acc_x -= 1;
		case KeyEvent.VK_PAGE_UP:
			acc_y += 1;
		case KeyEvent.VK_PAGE_DOWN:
			acc_y -= 1;
		}

	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}



/*
 * KeyBoard Input Version
 * 
public class Ball extends JPanel implements KeyListener{
	private static final long serialVersionUID = 2540605979276383444L;
	private int xPos;
	private int yPos;
	private int diameter;
	boolean pressed_LeftKey = false;
	boolean pressed_RightKey = false;
	boolean pressed_UpKey = false;
	boolean pressed_DownKey = false;
	Color color;
	
	public Ball( ) {
		xPos = 0;
		yPos = 0;
		color = Color.magenta;
		diameter = 10;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillOval(0, 0, diameter, diameter);
	}
	
	public void move() {
		if ( !( xPos < 0 || diameter + xPos > getParent().getWidth() 
				|| yPos < 0 ||diameter + yPos > getParent().getHeight() ) )
			setLocation(xPos, yPos);
	}
	
	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		System.out.println("Pressed: " + keycode);
		switch(keycode){
		case KeyEvent.VK_UP:
			yPos += -1;
			break;
		case KeyEvent.VK_DOWN:
			yPos += 1;
			break;
		case KeyEvent.VK_LEFT:
			xPos += -1;
			break;
		case KeyEvent.VK_RIGHT:
			xPos += 1;
			break;
		}
		this.move();
		this.repaint();
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
*/
