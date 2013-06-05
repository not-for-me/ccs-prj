package CC_PRJ.SSM;

public class SharedMode {
	public final static int ABS_MODE = 1;
	public final static int FRQ_MODE = 2;
	public final static int DEAD_MODE = 3;
	
	private static SharedMode instance = new SharedMode();
	private int sharedMode = 0;

	public SharedMode() {
		
	}
	
	public static SharedMode getInstance() {
		return instance;
	}
	
	public int getSharedMode() {
		return sharedMode;
	}
	public void setSharedMode(int sharedMode) {
		this.sharedMode = sharedMode;
	}
}
