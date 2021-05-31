package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
//Entity class - define Extension Request in the CEMS system.
public class ExtensionRequest implements Serializable {
	private ActiveExam activeExam;
	private String additionalTime;
	private String reqReason;

	public ExtensionRequest(ActiveExam activeExam, String additionalTime, String reqReason) {
		this.activeExam = activeExam;
		this.additionalTime = additionalTime;
		this.reqReason = reqReason;
	}
	
	public ExtensionRequest(ActiveExam activeExam) {
		this.activeExam = activeExam;
	}

	public ActiveExam getActiveExam() {
		return activeExam;
	}

	public void setActiveExam(ActiveExam activeExam) {
		this.activeExam = activeExam;
	}

	public String getAdditionalTime() {
		return additionalTime;
	}

	public void setReason(String additionalTime) {
		this.additionalTime = additionalTime;
	}

	public String getReason() {
		return reqReason;
	}

	public void setAdditionalTime(String reqReason) {
		this.reqReason = reqReason;
	}
}
