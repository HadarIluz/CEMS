package gui_student;

import java.util.ArrayList;

import client.CEMSClient;
import client.ClientUI;
import entity.User;
import gui_cems.GuiCommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import logic.RequestToServer;

public class ViewExamController extends GuiCommon {

	@FXML
	private TextField txtExamID;

	@FXML
	private Button btnGetCopyOfExam;

	@FXML
	private Button btnViewGrade;

	@FXML
	private Text txtReqField;

	@FXML
	private Label lblCourse;

	@FXML
	private Label lblGrade;

	@FXML
	private Label textCourseName;

	@FXML
	private Label textGrade;

	@FXML
	private Label lblProfession;

	@FXML
	private Label textProfessionName;

	@FXML
	private Label lblDownload;

	@FXML
	private ImageView imgDownload;

	@FXML
	private Label lblExamIDNotFound;

	@FXML
	private ImageView imgRefresh;

	private static StudentController studentController;

	private static User student = (User) ClientUI.loggedInUser.getUser();

	@FXML
	void btnGetCopyOfExam(ActionEvent event) {

	}

	@FXML
	void btnViewGrade(ActionEvent event) {

		if (!checkForLegalID(txtExamID.getText())) {

			return;

		}

		RequestToServer req = new RequestToServer("StudentView grade");
		String[] StudentData = { txtExamID.getText(), String.valueOf(student.getId()) };

		req.setRequestData(StudentData);
		ClientUI.cems.accept(req);
		@SuppressWarnings("unchecked")
		ArrayList<String> Details = (ArrayList<String>) CEMSClient.responseFromServer.getResponseData();
		lblCourse.setVisible(true);
		lblProfession.setVisible(true);
		lblGrade.setVisible(true);
		textGrade.setVisible(true);
		textProfessionName.setVisible(true);
		textCourseName.setVisible(true);
		try {
		textGrade.setText(Details.get(0));
		textProfessionName.setText(Details.get(1));
		textCourseName.setText(Details.get(2));
		}catch(IndexOutOfBoundsException e) {
			lblCourse.setVisible(false);
			lblProfession.setVisible(false);
			lblGrade.setVisible(false);
			textGrade.setVisible(false);
			textProfessionName.setVisible(false);
			textCourseName.setVisible(false);
			popUp("Worng Exam ID!");
			
		}

	}

	@FXML
	void RefreshPage(MouseEvent event) {

		this.displayNextScreen(student, "ViewExam.fxml");

	}

}
