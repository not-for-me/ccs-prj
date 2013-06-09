package CC_PRJ.SSM.Client.FrqMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import CC_PRJ.AnimateLogic.BallMover;
import CC_PRJ.DataModel.Message;
import CC_PRJ.DataModel.MessageParser;
import CC_PRJ.DataModel.StringQueue;
import CC_PRJ.Interface.Client.ClientInterface;
import CC_PRJ.Interface.Component.BallMoverWindow;
import CC_PRJ.SSM.SharedMode;

public class FrequentStateRegeneration {
	private boolean lockOwnFlag = false;
	private int lockTimer = 0;
	private int port = 25000;
	private int LOCK_MAX_TIMER = 10;
	private DatagramPacket dp1; // 보내는 것
	private MulticastSocket ms1; //보내는 것
	private static StringQueue msgQueue = new StringQueue();

	public static StringQueue getMSGQueue() {
		return msgQueue;
	}
	public FrequentStateRegeneration() {
	}

	public void run(){
		System.out.println("Start Frequently Update Mode!!!");
		BallMoverWindow ballMoverWindow = new BallMoverWindow();
		ballMoverWindow.ballWindow();

		final BallMover ball = new BallMover(SharedMode.ABS_MODE);
		//final BallMover ball = new BallMover(SharedMode.FRQ_MODE);
		ballMoverWindow.getBallMoverFrame().add(ball);
		ballMoverWindow.getBallMoverFrame().addKeyListener(ball);

		ReceiveThread communicationThread = new ReceiveThread();
		communicationThread.start();

		while(ClientInterface.getInstance().getCloseFlag() == ClientInterface.FALSE) {

			if(getMSGQueue().getQueue().isEmpty() != true) {
				String output = getMSGQueue().dequeueString();
				System.out.println("Dequeued msg: " + output);
				MessageParser parser = new MessageParser(output);
				Message msg = parser.parseMessage();

				switch(msg.getMsgType()) {
				case Message.UPDATE_USER:
					System.out.println("[[Update User Packet] is comming!!!");
					ball.setBall(msg.getBall());
					ball.redraw();
					ball.repaint();
					break;
				default:
					break;
				}
			}

			if( ball.isKeyPressed() ) {

				if(lockOwnFlag == false){
					try {
						Socket sock  = new Socket(ClientInterface.getInstance().getAddress(), ClientInterface.getInstance().getPort());
						PrintWriter out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );
						BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );

						out.println("LOCK_REQUEST");
						out.flush();

						String input = in.readLine();
						if(input.compareTo("ALLOW_LOCK") == 0) {
							System.out.println("ALLOWED LOCK");
							lockTimer = 0;
							lockOwnFlag = true;
						}
						else if(input.compareTo("LOCK_REJECT") == 0) {
							System.out.println("REJECTED LOCK");
						}
						sock.close();
						out.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}


				if(lockOwnFlag == true) {
					String sendMSG = "2/";
					sendMSG = sendMSG.concat( Integer.toString( ClientInterface.getInstance().getUserID() ) );
					sendMSG = sendMSG.concat("/");
					sendMSG =  sendMSG.concat(ball.getBall().getBallInfoInString());
					System.out.println("Sending MSG: " + sendMSG);

					try{
						dp1 = new DatagramPacket(sendMSG.getBytes(),
								sendMSG.getBytes().length,
								InetAddress.getByName("239.2.3.4"),33333);
						ms1 = new MulticastSocket();
						ms1.setTimeToLive((byte)1);
						ms1.send(dp1);
						lockTimer++;
						System.out.println("Msg Send and Lock Timer " + lockTimer);
						ms1.close();
					}catch(IOException ie){
						ie.printStackTrace();
					}

					/*
					try {
						DatagramSocket socket;

						socket = new DatagramSocket();

						socket.setBroadcast(true);
						InetAddress inetAddress = InetAddress.getByName("255.255.255.255");

						DatagramPacket datagramPacket = new DatagramPacket( sendMSG.getBytes() , sendMSG.getBytes().length, inetAddress, port );
						socket.send(datagramPacket);
						socket.close();
						lockTimer++;
						System.out.println("Msg Send and Lock Timer " + lockTimer);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 */

					if(lockTimer == LOCK_MAX_TIMER){
						try {
							Socket sock  = new Socket(ClientInterface.getInstance().getAddress(), ClientInterface.getInstance().getPort());
							PrintWriter out = new PrintWriter( new OutputStreamWriter( sock.getOutputStream() ) );
							BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );

							out.println("LOCK_RELEASE");
							out.flush();

							String input = in.readLine();
							if(input.compareTo("RELEASE_OK") == 0) {
								System.out.println("Release OK");
								lockTimer = 0;
								lockOwnFlag = false;
							}
							sock.close();
							out.close();
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}

				ball.setKeyFlag(false);
			}
		}
	}

}

