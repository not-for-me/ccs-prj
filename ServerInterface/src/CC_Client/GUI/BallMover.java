package CC_Client.GUI;

import CC_Client.GUI.Component.Ball;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class BallMover {
	final static int FRAME_WIDTH = 400;
	final static int FRAME_HEIGHT = 400;
	final static int DELAY = 10; // Milliseconds between timer ticks
	final static String FRAME_TITLE = "Object Trajectory Window";

	public void ballWindow() {
		/*
		while(true) {
			try {Thread.sleep(DELAY);} catch (InterruptedException e) { }
			ball.move();
			ball.repaint();
		}
		 */

		JFrame frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle(FRAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Ball ball = new Ball();
		frame.add(ball);
		frame.addKeyListener(ball);
		frame.setVisible(true);

		class TimerListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				ball.move(1);
				ball.repaint();
			}
		}
		ActionListener listener = new TimerListener();
		Timer t = new Timer(DELAY, listener);
		t.start();     
	}
}
