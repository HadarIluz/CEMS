package gui_student;

import entity.User;
import gui_cems.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StudentController {

	@FXML
	private Label pressLogout;

	@FXML
	private ImageView imgStudent;

	@FXML
	private ImageView imgLogo;

	@FXML
	private Label textHelloStudent;

	@FXML
	private Label textStudentName;

	@FXML
	private Button btnStartManualExam;

	@FXML
	private ImageView imgPhone;

	@FXML
	private ImageView imgEmail;

	@FXML
	private Button btnViewExamInfo;

	@FXML
	private Button btnStartComputerizedExam;

	//
	public static LoginController loginController;
	public User user;

	@FXML
	void btnStartComputerizedExam(ActionEvent event) {

	}

	@FXML
	void btnStartManualExam(ActionEvent event) {

	}

	@FXML
	void btnViewExamInfo(ActionEvent event) {

	}

	// Event Logout that occur when clicking on logout at the left menu
	@FXML
	void pressLogout(MouseEvent event) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Logout");
		alert.setContentText("Are you Sure?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
				// ASK -Sure not like that, I currently do not know what it
				// will look like after learning about the singleton.
				loginController.performLogput(this.user);

			}
		});

	}

}
