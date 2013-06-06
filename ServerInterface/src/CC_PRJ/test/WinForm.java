package CC_PRJ.test;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class WinFormSub extends Frame implements Runnable,ActionListener{
	private TextArea ta = new TextArea();
	private Label lb = new Label("대화 : ",Label.RIGHT);
	private TextField tf = new TextField();
	private Button bt =new Button("전송");
	private Button bt1 = new Button("파일 전송");
	private Button bt2 = new Button("끝내기");
	private Thread current;
	
	private DatagramPacket dp; // 받는 것
	private MulticastSocket ms; // 받는것
	
	private DatagramPacket dp1; // 보내는 것
	private MulticastSocket ms1; //보내는 것
	
	public WinFormSub(String str){
		super(str);
		this.init();
		this.start();
		this.setSize(300,400);
		this.setVisible(true);
	}
	public void init(){
		this.setLayout(new BorderLayout());
		Panel p1 = new Panel(new BorderLayout()	);
		p1.add("Center",ta);
		Panel p2 = new Panel(new BorderLayout());
		p2.add("West",lb);
		p2.add("Center",tf);
		p2.add("East",bt);
		p1.add("South",p2);
		this.add("Center",p1);
		Panel p = new Panel(new GridLayout(1,2));
		p.add(bt1);
		p.add(bt2);
		this.add("South",p);
		ta.setEditable(false);
		tf.requestFocus();
	}
	public void start(){
		// 현재 Class를 thread로 만듬
		current = new Thread(this);
		current.start();
		tf.addActionListener(this);
		bt.addActionListener(this);
	}
	/*
	 * Thread로 대기하고 있다가 대화를 받아서 찍어줌
	 */
	public void run() {
		ta.setText("-------대화 방에 입장하셨습니다.----- \n\n");
		try{
			ms = new MulticastSocket(33333);
			ms.joinGroup(InetAddress.getByName("239.2.3.4"));
		}catch(IOException ie){
			ie.printStackTrace();
		}
		// 무한 루프 돌면서 데이터 받음
		while(true){
			try{
				dp = new DatagramPacket(new byte[65508],65508);
				ms.receive(dp);
				ta.append(dp.getAddress() + ">>" + new String(dp.getData()));
				ta.append("\n");
			}catch(IOException ie){}
		}
	}
	/*
	 * 데이터 보내는 부분
	 */
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == tf || e.getSource()== bt){
				String str = tf.getText().trim();
				if(str == null || str.length() == 0){
					tf.setText("");
					tf.requestFocus();
					return;
				}
				try{
					dp1 = new DatagramPacket(str.getBytes(),
							str.getBytes().length,
							InetAddress.getByName("239.2.3.4"),33333);
					ms1 = new MulticastSocket();
					ms1.setTimeToLive((byte)1);
					ms1.send(dp1);
					ms1.close();
				}catch(IOException ie){
					ie.printStackTrace();
				}
				tf.setText("");
				tf.requestFocus();
			}
	}
}

public class WinForm {
	public static void main(String[] ar){
		WinFormSub ex = new WinFormSub("메신저");
	
	}

}

