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
import logic.RequestToServer;

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
	void btnBack(ActionEvent event) {
		displayNextScreen(teacher, "/gui_teacher/ExamBank.fxml");
	}

	// message of lior: still not working.
	// need to understand how to save it- method in DB controller is not good & if i
	// need to save changes in screen that open with btn browse question
	@FXML
	void btnSaveEditeExam(ActionEvent event) {
		String teacherComment= textTeacherComment.getText().trim();
		String studentComment= textStudentComment.getText().trim();
		String timeAllocateForExam = textTimeAllocateForExam.getText().trim();
		// Check that all fields that must be filled are filled correctly.
		boolean condition = checkConditionToStart(teacherComment, studentComment, timeAllocateForExam);
		if (condition) {
			
			RequestToServer req = new RequestToServer("SaveEditExam");
			req.setRequestData(exam);
			ClientUI.cems.accept(req);

		}
	}
	
	private boolean checkConditionToStart( String teacherComment, String StudentComment, String timeAllocateForExam) {
		StringBuilder strBuilder = new StringBuilder();
		boolean flag= true;
		if(teacherComment.length()==0 || StudentComment.length()==0 ) {
			strBuilder.append("All fields must be filled !\n");
			flag= true;
		}
		//return true if the String contains only digits.
		if(isOnlyDigits(timeAllocateForExam)) {
			strBuilder.append("Exam time must contains only digits.\n");
			flag= true;
		}
		if(timeAllocateForExam.matches("[0-9]+")) {
			int t=Integer.valueOf(timeAllocateForExam);
//			if(! (t instanceof Integer)) {
//				strBuilder.append("Time allocate for exam must set in minuse.\n");
//				flag= true;
//			}
		}
		
		if (!flag) {
			popUp(strBuilder.toString());
		}
		
		return flag;

	}

	/**
	 * TODO:
	 * 
	 * @param event that occurs when the teacher clicks on a BrowseQuestions button,
	 *              she transfer the next screen, that shows her all the questions of
	 *              the specific exam she select.
	 */
	@FXML
	void btnBrowseQuestions(ActionEvent event) {
		if (!checkForLegalID(textExamID.getText()) || textExamID.getText().isEmpty()) {
			return;
		}
		displayNextScreen(teacher, "/gui_teacher/QuestionBank.fxml");
	}

	/**
	 * @param event that occurs when pressing on back button, the user return to
	 *              'examBank' screen.
	 */
	@FXML
	void btnShowQuestion(ActionEvent event) {
		displayNextScreen(teacher, "/gui_teacher/ExamBank.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// tack user data according to screen status from the prev action.
		if (screenStatus.equals(super.teacherStatusScreen)) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();
			// load data of the selected exam for edit.
			textExamID.setText(exam.getExamID());
			textTimeAllocateForExam.setText(Integer.toString(exam.getTimeOfExam()));
			textTeacherComment.setText(exam.getCommentForTeacher());
			textStudentComment.setText(exam.getCommentForStudents());
		}

		if (screenStatus.equals(super.principalStatusScreen)) {
			principal = (User) ClientUI.loggedInUser.getUser();
			// load data of the selected exam for view.
			textExamID.setText(exam.getExamID());
			// textTimeAllocateForExam.setText(!!!!SQL);
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
	
	
	private boolean checkForLegalID(String ExamID) {
		if (ExamID.length() != 6) {
			popUp("Exam ID Must be 6 digits.");
			return false;
		}
		for (int i = 0; i < ExamID.length(); i++)
			if (!Character.isDigit(ExamID.charAt(i))) {
				popUp("Exam ID Must Contains only digits.");
				return false;
			}
		return true;
	}
	
	
	

}
