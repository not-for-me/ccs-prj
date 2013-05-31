package CC_Server.Model;

public class Ball {
	private int vel_x;
	private int vel_y;
	private int acc_x;
	private int acc_y;
	
	public Ball( ) {
		vel_x = 0;
		vel_y = 0;
		acc_x = 0;
		acc_y = 0;
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
	public int getAcc_x() {
		return acc_x;
	}
	public void setAcc_x(int acc_x) {
		this.acc_x = acc_x;
	}
	public int getAcc_y() {
		return acc_y;
	}
	public void setAcc_y(int acc_y) {
		this.acc_y = acc_y;
	}
}
