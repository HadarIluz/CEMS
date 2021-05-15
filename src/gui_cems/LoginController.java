package gui_cems;

import java.io.IOException;
import client.CEMSClient;
import client.ClientUI;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.RequestToServer;

public class LoginController {

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

	final String StudentMenu = "StudentController.fxml";
	final String PrincipalMenu = "PrincipalController.fxml";
	final String TeacherMenu = "TeacherController.fxml";
	public static User user;
	
	@FXML
	public void btnLogin(ActionEvent event) throws IOException {
		String userID = txtUserName.getText();
		String userPassword = txtPassword.getText();

		if (userID.trim().isEmpty()) {
			showMsg(reqFieldUserName, "User name is required field.");
		}
		if (userPassword.trim().isEmpty()) {
			showMsg(reqFieldPassword, "Password is required field.");
		}
		// in case fields not empty checks if exist in DB
		if (!userID.trim().isEmpty() && !userPassword.trim().isEmpty() && userID.length() == 9
				&& isOnlyDigits(userID)) {
			int id = Integer.parseInt(txtUserName.getText().trim());
			user = new User(id, userPassword);
			// set in 'Serializable' class my request from server.
			RequestToServer req = new RequestToServer("getUser");
			req.setRequestData(user);
			ClientUI.cems.accept(req); // sent server pk(id) to DB in order to checks if user exist or not.
			if (CEMSClient.statusMsg.getStatus().equals("USER NOT FOUND")) {
				System.out.println("press on login button and server returns: -->USER NOT FOUND");
				popUp("This user doesn`t exist in CEMS system.");
			}

			// handle case that user found and checks password && if the user already
			// login(now) by checking (isLogged()==1) && userType
			else {
				if (userPassword.equals(user.getPassword()) == false) {
					popUp("The password insert is incorrect. Please try again.");
				} else if (user.isLogged() == 1) {
					popUp("This user already login to CEMS system!.");
				} else {

					user.setLogged(1);
					Stage primaryStage = new Stage(); // CHECK
					GridPane root = new GridPane();
					Scene scene = new Scene(root, 988, 586); // define screens size
					primaryStage.setTitle("CEMS-Computerized Exam Management System");
					
					
					switch (user.getUserType().toString()) {
					case "Student": {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Main) window
						Pane mnueLeft = FXMLLoader.load(getClass().getResource(StudentMenu));
						root.add(mnueLeft, 0, 0);
						// mnueRight= //put here grey screen.
						// root.add(mnueRight, 1, 0);
						primaryStage.setScene(scene);
						primaryStage.show();
					}
						break;
					case "Teacher": {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Main) window
						Pane mnueLeft = FXMLLoader.load(getClass().getResource(TeacherMenu));
						root.add(mnueLeft, 0, 0);
						// mnueRight= //put here grey screen.
						// root.add(mnueRight, 1, 0);
						primaryStage.setScene(scene);
						primaryStage.show();

					}
						break;
					case "Principal": {
						((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Main) window
						Pane mnueLeft = FXMLLoader.load(getClass().getResource(PrincipalMenu));
						root.add(mnueLeft, 0, 0);
						// mnueRight= //put here grey screen.
						// root.add(mnueRight, 1, 0);
						primaryStage.setScene(scene);
						primaryStage.show();
					}
						break;

					}
					// print to console
					System.out.println(user.getId() + " login Successfully as: " + user.getUserType().toString());	
					
					// sent to server pk(id) in order to change the login status of this user.
					RequestToServer reqLoged = new RequestToServer("UpdateUserLoged");
					reqLoged.setRequestData(id);
					ClientUI.cems.accept(reqLoged); 
				}

			}
		}

		// handle case that one of the parameters is invalid.
		else {
			popUp("One or more of the parameters which insert are incorrect. Please try again.");
		}

	}

	// Display commend to user.
	private void showMsg(Label label, String msg) {
		label.setTextFill(Paint.valueOf("Red"));
		label.setText(msg);
	}

	// this method checks if the given string includes letters.
	// 'containsLetter' is true only if the String contains something that isn't a
	// digit.
	private boolean isOnlyDigits(String str) {
		// TODO Auto-generated method stub
		boolean containsLetter = true;
		for (char ch : str.toCharArray()) {
			if (!Character.isDigit(ch)) {
				containsLetter = false;
				System.out.println("id includ letter");
				break;
			}
		}
		System.out.println("isOnlyDigits returns:"+containsLetter); //message to console
		return containsLetter;
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

	// create a popUp with a message
	private void popUp(String msg) {
		final Stage dialog = new Stage();
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(msg);
		lbl.setPadding(new Insets(5));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
		dialog.setScene(dialogScene);
		dialog.show();
	}
	
	public void performLogput(User user) {
		
		// sent to server pk(id) in order to change the login status of this user.
		RequestToServer reqLoged = new RequestToServer("UpdateUserLoged");
		reqLoged.setRequestData(user);
		ClientUI.cems.accept(reqLoged); 
		
	}
	
	

}
