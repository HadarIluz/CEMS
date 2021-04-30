package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StatusMsg implements Serializable {
	
	private String status;
	private String description = null;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
