package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UpdateDataRequest implements Serializable {
	
	private String examID;
	private String timeAllotedForTest;
	
	public String getExamID() {
		return examID;
	}
	public void setExamID(String examID) {
		this.examID = examID;
	}
	public String getTimeAllotedForTest() {
		return timeAllotedForTest;
	}
	public void setTimeAllotedForTest(String timeAllotedForTest) {
		this.timeAllotedForTest = timeAllotedForTest;
	}
	
	@Override
	public String toString(){
		return String.format("%s %s \n",examID,timeAllotedForTest);
	}
	

	

}
