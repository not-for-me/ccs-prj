package CC_PRJ.Interface.Component;

/*
 * 공이 움직이는 화면 생성 클래스
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import CC_PRJ.Interface.Client.ClientInterface;

public class BallMoverWindow {
	public final static int FRAME_WIDTH = 300;
	public final static int FRAME_HEIGHT = 300;
	final static String FRAME_TITLE = "Object Trajectory Window";
	private JFrame frame;
	
	public void ballWindow() {
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle(FRAME_TITLE);
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
