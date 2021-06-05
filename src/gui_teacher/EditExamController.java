package gui_teacher;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Exam;
import entity.Teacher;
import entity.User;
import gui_cems.GuiCommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditExamController extends GuiCommon implements Initializable {

	@FXML
	private Text texTtitleScreen;

	@FXML
	private TextField textExamID;

	@FXML
	private Button btnShowQuestion;

	@FXML
	private Label textTimeForExam;

	@FXML
	private TextArea textTeacherComment;

	@FXML
	private TextArea textStudentComment;

	@FXML
	private Button btnSaveEditeExam;

	@FXML
	private Button btnBackPrincipal;

	@FXML
	private Text textNavigation;

	@FXML
	private TextField textTimeAllocateForExam;

	public static Exam exam;
	private static Teacher teacher;
	private static User principal;
	private static String screenStatus;

	@FXML
	void btnBackPrincipal(ActionEvent event) {

	}

	@FXML
	void btnSaveEditeExam(ActionEvent event) {

	}

	@FXML
	void btnShowQuestion(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//tack user data according to screen status from the prev action.
		if (screenStatus.equals(super.teacherStatusScreen)) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();
		}
		
		if (screenStatus.equals(super.principalStatusScreen)) {
			principal = (User) ClientUI.loggedInUser.getUser();
			//load data of the selected exam for view.
			textExamID.setText(exam.getExamID());
			//textTimeAllocateForExam.setText(!!!!SQL);
			textTeacherComment.setText(exam.getCommentForTeacher());
			textStudentComment.setText(exam.getCommentForStudents());
			
			btnSaveEditeExam.setDisable(false);
			btnSaveEditeExam.setVisible(false);
		}
		
	}

	public static void setActiveExamState(Exam selectedExam, String status) {
		screenStatus = status;
		exam = selectedExam;
	}

}
