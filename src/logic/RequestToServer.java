package logic;

public class RequestToServer {
	
	private String requestType; // for example: getUser, createNewExam, etc...
	private Object requestData = null;
	
	public RequestToServer(String requestType) {
		this.requestType = requestType;
	}

	public Object getRequestData() {
		return requestData;
	}

	public void setRequestData(Object requestData) {
		this.requestData = requestData;
	}

	public String getRequestType() {
		return requestType;
	}
	

}
