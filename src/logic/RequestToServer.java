package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RequestToServer implements Serializable{
	
	private String requestType; // for example: getUser, createNewExam, etc...
	private Object requestData = null; // here you'll set an object to pass the server in case you have one.
										// for example: passing Exam object if you're creating a new exam.
	
	
	
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
