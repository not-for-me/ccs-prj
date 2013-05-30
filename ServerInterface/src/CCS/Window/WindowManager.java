package CCS.Window;

import CCS.Interface.ServerInterface;
import CCS.Window.Component.TopComponent;
import CCS.Window.Component.MiddleComponent;
import CCS.Window.Component.BottomComponent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WindowManager implements ActionListener{
	private final static int FRAME_WIDTH = 500;
	private final static int FRAME_HEIGHT = 220;
	private final static String FRAME_TITLE = "Manager Window";
	private final static String ABSOLUTE_STRING = "Absolute Consistency";
	private final static String FRQ_STRING = "Frequently Update";
	private final static String DEAD_STRING = "Dead Reckoning";

	private static WindowManager instance = new WindowManager();
	
	private JFrame frame;
	private TopComponent top;
	private MiddleComponent middle;
	private BottomComponent bottom;

	private WindowManager() {
	}
	
	public static WindowManager getInstance() {
		return instance;
	}

	public void drawWindow() {
		this.drawFrame();
		top = new TopComponent();
		middle = new MiddleComponent();
		bottom = new BottomComponent();

		frame.add(top.getTopPanel(), BorderLayout.NORTH);
		frame.add(middle.getMiddlePanel(), BorderLayout.CENTER);
		frame.add(bottom.getBottomPanel(), BorderLayout.SOUTH);
		frame.setVisible(true);
		
		this.connectListener();
	}

	private void drawFrame() {
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setTitle(FRAME_TITLE);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void connectListener() {
		top.getAbsBtn().addActionListener(this);
		top.getFrqBtn().addActionListener(this);
		top.getDeadBtn().addActionListener(this);

		bottom.getPortTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ServerInterface.getInstance().setPort( Integer.parseInt( bottom.getPortTextField().getText() ) );
				System.out.println("Port Number Input: " + ServerInterface.getInstance().getPort() );
			}
			public void changedUpdate(DocumentEvent arg0) {}
			public void removeUpdate(DocumentEvent arg0) {}
		});
		
		bottom.getStartBtn().addActionListener(this);
	}

	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if(source == top.getAbsBtn()) {
			middle.getModeTextField().setText(ABSOLUTE_STRING);
			ServerInterface.getInstance().setSharedMode(ServerInterface.ABS_MODE);
		}
		else if(source == top.getFrqBtn()) {
			middle.getModeTextField().setText(FRQ_STRING);
			ServerInterface.getInstance().setSharedMode(ServerInterface.FRQ_MODE);
		}
		else if(source == top.getDeadBtn()) {
			middle.getModeTextField().setText(DEAD_STRING);
			ServerInterface.getInstance().setSharedMode(ServerInterface.DEAD_MODE);
		}
		else if(source == bottom.getStartBtn()) {
			if(bottom.getStartBtn().getText().compareTo("Listen") == 0) {
				ServerInterface.getInstance().setListenFlag(ServerInterface.TRUE);
				bottom.getStartBtn().setText("Start");
			}
			else if(bottom.getStartBtn().getText().compareTo("Start") == 0) {
				ServerInterface.getInstance().setStartFlag(ServerInterface.TRUE);
				bottom.getStartBtn().setText("Start");
				top.getAbsBtn().setEnabled(false);
				top.getFrqBtn().setEnabled(false);
				top.getDeadBtn().setEnabled(false);
				bottom.getStartBtn().setEnabled(false);
			}
		}
	}

	public TopComponent getTop() {
		return top;
	}
	public void setTop(TopComponent top) {
		this.top = top;
	}
	public MiddleComponent getMiddle() {
		return middle;
	}
	public void setMiddle(MiddleComponent middle) {
		this.middle = middle;
	}
	public BottomComponent getBottom() {
		return bottom;
	}
	public void setBottom(BottomComponent bottom) {
		this.bottom = bottom;
	}
}
