package CC_Server.Model.SSM.AbsMethod;

import CC_Server.Interface.ServerInterface;

public class AbsoluteConsistency {
	private int userNum;
	private int[] updateFlagArr; 
			
	public AbsoluteConsistency(){
		userNum = ServerInterface.getUserConnInfoList().size();
		updateFlagArr = new int[userNum];
		
		while(true) {
			
			
			
			
			
		}
	}
}
