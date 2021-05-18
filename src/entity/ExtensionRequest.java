package entity;

import java.io.Serializable;

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

	public ActiveExam getExam() {
		return activeExam;
	}

	public void setExam(ActiveExam activeExam) {
		this.activeExam = activeExam;
	}

	public String getAdditionalTime() {
		return additionalTime;
	}

	public void setReason(String additionalTime) {
		this.additionalTime = additionalTime;
	}

	public String getsetReason() {
		return reqReason;
	}

	public void setAdditionalTime(String reqReason) {
		this.reqReason = reqReason;
	}
}
