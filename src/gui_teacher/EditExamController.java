package gui_teacher;

import java.net.URL;
import java.security.Principal;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Exam;
import entity.Teacher;
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

	public static Exam editedExam;
	private Teacher teacher;
	private Principal principal;
	private static TeacherController teacherController; // we will use it for load the prev/next screen ! (using root).
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

		teacher = (Teacher) ClientUI.loggedInUser.getUser();

	}

	public static void setActiveExamState(Exam selectedExam, String status) {
		screenStatus = status;
		editedExam = selectedExam;
	}

}
