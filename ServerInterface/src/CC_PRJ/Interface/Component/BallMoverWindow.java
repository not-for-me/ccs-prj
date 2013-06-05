package CC_PRJ.Interface.Component;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import CC_PRJ.Interface.Client.ClientInterface;

public class BallMoverWindow {
	final static int FRAME_WIDTH = 400;
	final static int FRAME_HEIGHT = 400;
	final static int DELAY = 10; // Milliseconds between timer ticks
	final static String FRAME_TITLE = "Object Trajectory Window";
	private JFrame frame;
	
	public void ballWindow() {
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle(FRAME_TITLE);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addWindowListener( new WindowAdapter() { 
			public void windowClosing(WindowEvent ev) {
				System.out.println("End");
				ClientInterface.getInstance().setCloseFlag(ClientInterface.TRUE);
			}
		});
	}
	
	public JFrame getBallMoverFrame() {
		return frame;
	}
}
