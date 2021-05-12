package guiControllers;

import java.io.IOException;

import client.CEMSClient;
import client.ClientUI;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginController {
	public User user;

	@FXML
	private ImageView background;

	@FXML
	private TextField txtUserName;

	@FXML
	private Label reqFieldPassword;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblShow;

	@FXML
	private CheckBox checkBoxRememberMe;

	@FXML
	private Button btnLogin;

	@FXML
	private ImageView logo;

	@FXML
	private ImageView phone;

	@FXML
	private ImageView emil;

	@FXML
	private Label reqFieldUserName;

	@FXML
	private Font x3;

	@FXML
	private Color x4;

	@FXML
    void btnLogin(ActionEvent event) {
		String userID = txtUserName.getText();
		String userPassword = txtPassword.getText();
		
		if(userID.trim().isEmpty() ) {	
			showMsg(reqFieldUserName, "User name is required field.");
		}
		if(userPassword.trim().isEmpty() ) {	
			showMsg(reqFieldPassword, "Password is required field.");
		}
		// in case fields not empty checks if exist in DB
		if (!userID.trim().isEmpty() &&  !userPassword.trim().isEmpty() && userID.length()==9 ) {
			int id = Integer.parseInt(txtUserName.getText().trim());
			user = new User(id, userPassword);
			//ClientUI.cems.accept(userName);  
			//Hadar`s Note:
			//i think the user name need to be id
			// if there is 2 Hadar Iluz with the same password.. so which on of them can enter into the system??
			//so i implement in the server for now the id unless it is not true.
			ClientUI.cems.accept(userID); //here he types int id
			if (CEMSClient.statusMsg.getStatus().equals("USER NOT FOUND")) {
				showMsg(reqFieldUserName, "User name not exist.");
			}
			//checks password, userType, open controllers according to type and more..... tomorrow.
			//if()
		}
		

		
		
		
		
    }

	// Display HomePage after client connection.
	public void start(Stage primaryStage) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

			Scene scene = new Scene(root);
			// message to primaryStage
			primaryStage.setTitle("CEMS-Computerized Exam Management System");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Display commend to user.
	private void showMsg(Label label, String msg) {
		label.setTextFill(Paint.valueOf("Red"));
		label.setText(msg);
	}

}
