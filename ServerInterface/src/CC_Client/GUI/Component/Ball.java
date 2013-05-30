package CC_Client.GUI.Component;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Ball extends JPanel implements KeyListener{
	private static final long serialVersionUID = 2540605979276383444L;
	private int velocity_X;
	private int velocity_Y;
	private int diameter;
	boolean pressed_LeftKey = false;
	boolean pressed_RightKey = false;
	boolean pressed_UpKey = false;
	boolean pressed_DownKey = false;
	Color color;
	
	public Ball( ) {
		velocity_X = 0;
		velocity_Y = 0;
		color = Color.magenta;
		diameter = 10;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillOval(0, 0, diameter, diameter);
	}
	
	public void move() {
		int x = getX();
		int y = getY();

		if (x + velocity_X < 0 || x + diameter + velocity_X > getParent().getWidth()) {
			velocity_X *= -1;
		}
		if (y + velocity_Y < 0 || y + diameter + velocity_Y > getParent().getHeight()) {
			velocity_Y *= -1;
		}
		x += velocity_X;
		y += velocity_Y;

		setLocation(x, y);
	}
	
	public void keyPressed(KeyEvent e){
		int keycode = e.getKeyCode();
		System.out.println("Pressed: " + keycode);
		switch(keycode){
		case KeyEvent.VK_UP:
			velocity_Y += -1;
			break;
		case KeyEvent.VK_DOWN:
			velocity_Y += 1;
			break;
		case KeyEvent.VK_LEFT:
			velocity_X += -1;
			break;
		case KeyEvent.VK_RIGHT:
			velocity_X += 1;
			break;
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
