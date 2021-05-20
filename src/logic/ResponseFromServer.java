package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResponseFromServer implements Serializable{
	private String responseType; // for example: userData, examID, etc...
	private Object responseData = null; // here you'll set an object to pass the client in case you have one.
										// for example: passing Exam object if the client needs it.
	private StatusMsg statusMsg = null;
	
	public ResponseFromServer(String responseType) {
		this.responseType = responseType;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public StatusMsg getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(StatusMsg statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getResponseType() {
		return responseType;
	}
	
	@Override
	public String toString() {
		return "ResponseFromServer [responseType=" + responseType + "]";
	}
}
