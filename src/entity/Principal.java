package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Principal extends User implements Serializable {
	private ArrayList<ExtensionRequest> extensionRequestsList;

	public Principal(int id, String password, String fullName, String lastName, String email, UserType userType,
			ArrayList<ExtensionRequest> extensionRequestsList) {
		super(id, password, fullName, lastName, email, userType);
		extensionRequestsList = new ArrayList<ExtensionRequest>();
	}
	
	public Principal(User userData, ArrayList<ExtensionRequest> extensionRequestsList) {
		super(userData.getId(), userData.getPassword(), userData.getFirstName(), userData.getLastName(), userData.getEmail(), userData.getUserType());
		this.extensionRequestsList = extensionRequestsList;
	}

	public ArrayList<ExtensionRequest> getExtensionRequestsList() {
		return extensionRequestsList;
	}
	
	public void setExtensionRequestsList(ArrayList<ExtensionRequest> extensionRequestsList) {
		this.extensionRequestsList = extensionRequestsList;
	}
}