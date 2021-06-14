package gui_cems;

import client.CEMSClient;
import client.ClientUI;
import common.ICEMSClient;
import entity.User;
import logic.RequestToServer;

public class LoginLogic {
	
	private ICEMSClient cemsClient;
	
	public LoginLogic(ICEMSClient cemsClient) {
		this.cemsClient = cemsClient;
	}

	public User checkDetails(User user) {
		// TODO Auto-generated method stub
//		// create in 'Serializable' class my request from server.
		RequestToServer req = new RequestToServer("getUser");
		req.setRequestData(user);
		
		ClientUI.cems.accept(req); // send server pk(id) to DB in order to checks if user exist or not.

		if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("USER NOT FOUND")) {
			System.out.println("press on login button and server returns: -->USER NOT FOUND");
			GuiCommon.popUp("This user doesn`t exist in CEMS system.");
		}

		// handle case that user found and checks password
		else {
			User userFromDB = (User) CEMSClient.responseFromServer.getResponseData();
			if (user.getPassword().equals(userFromDB.getPassword()) == false) {
				GuiCommon.popUp("The password insert is incorrect. Please try again.");
			} else if (user.getId() != userFromDB.getId()) {
				GuiCommon.popUp("The id insert is incorrect. Please try again.");
			} else {

				// sent to server pk(id) in order to change the login status of this user.
				RequestToServer reqLogged = new RequestToServer("UpdateUserLoggedIn");
				reqLogged.setRequestData(user);
				ClientUI.cems.accept(reqLogged);
				return userFromDB;
			}
		}
		return null;

	}


	
	

}
