package CC_PRJ.DataModel;

/*
 *  공의 위치, 속도 정보를 기록하는 클래스
 */

public class Ball {
	private int pos_x;
	private int pos_y;
	private int vel_x;
	private int vel_y;
	
	public Ball( ) {
		pos_x = 0;
		pos_y = 0;
		vel_x = 0;
		vel_y = 0;
	}
	
	public int getPos_x() {
		return pos_x;
	}
	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}
	public int getPos_y() {
		return pos_y;
	}
	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}
	public int getVel_x() {
		return vel_x;
	}
	public void setVel_x(int vel_x) {
		this.vel_x = vel_x;
	}
	public int getVel_y() {
		return vel_y;
	}
	public void setVel_y(int vel_y) {
		this.vel_y = vel_y;
	}
	
	public void printBallInfo() {
		System.out.println("Ball position of x: " + getPos_x());
		System.out.println("Ball position of y: " + getPos_y());
		System.out.println("Ball velocity of x: " + getVel_x());
		System.out.println("Ball velocity of y: " + getVel_y());
	}
	
	public String getBallInfoInString() {
		String info = Integer.toString( getPos_x() );
		info = info.concat(",");
		info = info.concat( Integer.toString( getPos_y() ) );
		info = info.concat(",");
		info = info.concat( Integer.toString( getVel_x() ) );
		info = info.concat(",");
		info = info.concat( Integer.toString( getVel_y() ) );
		info = info.concat("/");
		return info;
	}
}
