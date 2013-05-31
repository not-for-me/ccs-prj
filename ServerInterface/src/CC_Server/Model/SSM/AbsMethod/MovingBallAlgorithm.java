package CC_Server.Model.SSM.AbsMethod;

public class MovingBallAlgorithm {
	
	public int firstOrderPolynomial(int time, int position, int vel) {
		return vel * time + position;
	}
	
	public int secondOrderPolynomial(int time, int position, int vel, int acc) {
		return (acc *time * time)/2 + vel * time  + position;
	}
}
