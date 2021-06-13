package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import entity.QuestionInExam;
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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import logic.RequestToServer;

/**
 * Class contains functionality for edit exam as part of 2 main steps. 
 * This screen describes the first stage in which the teacher sees the exam details
 * which are not available like examID. 
 * The teacher can edit the exam time and
 * comments. When clicking the BrowseQuestions button will take you to the next
 * screen to view the questions. And on this screen the teacher can save the
 * updated exam.
 * 
 * We reuse the screen to display any test details in the system that the
 * principal has chosen to see what the exam bank is. 
 * Therefore the screen distinguishes between 2 types of users: 
 * Manager - viewing permissions only.
 * Teacher - editing permissions as described.
 * 
 * @author Hadar Iluz
 *
 */
public class EditExamController extends GuiCommon implements Initializable {

	@FXML
	private Text texTtitleScreen;

	@FXML
	private ImageView imgStep1;

	@FXML
	private ImageView imgStep2;

	@FXML
	private TextField textExamID;

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

	@FXML
	private Button btnBrowseQuestions;

	public static Exam exam;
	private static Teacher teacher;
	private static User principal;
	private static String screenStatus;
	private static boolean displayPrincipalView = false;
	private static ArrayList<QuestionInExam> updatedQuestions;

	private String teacherComment;
	private String studentComment;
	private String timeAllocateForExam;
	private static Boolean backFromStep2=false;
	private static boolean emptyText=false;

	/**
	 * @param event that occurs When clicking the back button, will take you to back
	 *              to exam bank screen.
	 */
	@FXML
	void btnBack(ActionEvent event) {
		if (!displayPrincipalView) {
			displayNextScreen(teacher, "/gui_teacher/ExamBank.fxml");
		} else {
			displayNextScreen(principal, "/gui_teacher/ExamBank.fxml");
		}

	}

	/**
	 * @param event that occurs When clicking the BrowseQuestions button will take
	 *              you to the next screen to view questions by principal or also
	 *              update by teacher.
	 */
	@FXML
	void btnBrowseQuestions(ActionEvent event) {
		if (getExamDetailsANDcheckCOndition()) {
			// set the new parameters into editExam
			exam.setCommentForTeacher(teacherComment);
			exam.setCommentForStudents(studentComment);
			exam.setTimeOfExam(Integer.valueOf(timeAllocateForExam));

			// sent to next screen exam with data info.
			EditExam_questionsStep2Controller.setnextScreenData(exam, displayPrincipalView, updatedQuestions);
			if (!displayPrincipalView) {
				displayNextScreen(teacher, "/gui_teacher/EditExam_questionStep2.fxml");
			} else {
				displayNextScreen(principal, "/gui_teacher/EditExam_questionStep2.fxml");
			}
		}

	}

	/**
	 * @param event that occurs when the teacher makes updates to the test she has
	 *              chosen. The function checks that all the parameters are correct
	 *              and if so performs an update in DB by requesting from the
	 *              server, and displays a message that the update was successful.
	 *              Otherwise, displays the errors entered and does not update.
	 */
	@FXML
	void btnSaveEditeExam(ActionEvent event) {
		
		if (getExamDetailsANDcheckCOndition()) {
			// set the new parameters into editExam
			exam.setCommentForTeacher(teacherComment);
			if(emptyText) {
				exam.setCommentForStudents("");
				textStudentComment.setText("");
			}
			else {
				exam.setCommentForStudents(studentComment);
			}
			exam.setTimeOfExam(Integer.valueOf(timeAllocateForExam));

			// Request from server to update scores Of edited Exam.
			RequestToServer reqUpdate = new RequestToServer("updateScoresOfEditExam");
			reqUpdate.setRequestData(updatedQuestions);
			ClientUI.cems.accept(reqUpdate);

			if ((CEMSClient.responseFromServer.getResponseType()).equals("Edit Exam Scores Updated")) {
				// Request from server to update data of this exam.
				RequestToServer req = new RequestToServer("SaveEditExam");
				req.setRequestData(exam);
				ClientUI.cems.accept(req);

				if ((CEMSClient.responseFromServer.getResponseType()).equals("Edit Exam Saved")) {
					popUp("The exam you edit has been successfully created into the system !");
				} else {
					popUp("Update failed.");
				}
			}

		}

	}

	/**
	 * Check that all fields that must be filled are filled correctly.
	 * 
	 * @return true true if all fields are correctly filled, Otherwise returns
	 *         false.
	 */
	private boolean getExamDetailsANDcheckCOndition() {
		if(textTeacherComment.getText()==null)
			teacherComment = "";
		else if ((textTeacherComment.getText().trim()).isEmpty()) {
			teacherComment = "";
		} 
		else {
			teacherComment = textTeacherComment.getText().trim();
		}
		if(textStudentComment.getText()==null)
			studentComment = "";
		else if ((textStudentComment.getText().trim()).isEmpty()) {
			studentComment = "";
			emptyText=true;
		} 
		else {
			studentComment = textStudentComment.getText().trim();
		}
		
		timeAllocateForExam = textTimeAllocateForExam.getText().trim();
		// Check that all fields that must be filled are filled correctly.
		return checkConditionToStart(timeAllocateForExam);
	}

	/**
	 * @param teacherComment      input that the teacher can edit
	 * @param StudentComment      input that the teacher can edit
	 * @param timeAllocateForExam input that the teacher can edit
	 * @return true if all fields are correctly filled, Otherwise returns false
	 */
	private boolean checkConditionToStart(String timeAllocateForExam) {
		StringBuilder strBuilder = new StringBuilder();
		boolean flag = true;
		if (textTimeAllocateForExam.getText().isEmpty() == false) {
			int time = Integer.parseInt(timeAllocateForExam);

			if (timeAllocateForExam.matches("[0-9]+") == false || time <= 0) {
				strBuilder.append("Time allocate for exam must set in minuse.\n");
				textTimeAllocateForExam.clear();
				flag = false;
			}
			if (time <= 29) {
				strBuilder.append("Exam time too short.\n");
				flag = false;
			}
			if (time > 240) {
				strBuilder.append("Exam time too short.\n");
				flag = false;
			}
		}
		if (!flag) {
			popUp(strBuilder.toString());
		}
		return flag;
	}

	/**
	 * @param selectedExam with all inputs from Exam bank.
	 * @param status       of the logged user in order to display data according to
	 *                     user permissions.
	 */
	public static void setActiveExamState(Exam selectedExam, String status) {
		screenStatus = status;
		exam = selectedExam;
	}

	/**
	 * initialize function to prepare the screen after it is loaded. Tack user data
	 * according to screen status from the prev action.
	 *
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// load data of the selected exam for edit/view according to logged user..
		if(exam.getActiveExamType().equals("manual")) //matar
			btnBrowseQuestions.setVisible(false); //matar
		textExamID.setText(exam.getExamID());
		textTimeAllocateForExam.setText(Integer.toString(exam.getTimeOfExam()));
		textTeacherComment.setText(exam.getCommentForTeacher());
		textStudentComment.setText(exam.getCommentForStudents());

		if (screenStatus.equals(super.teacherStatusScreen)) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();
						
			//Forces the teacher to switch between the 2 screens of editing an exam.
			System.out.println(backFromStep2);
			if(backFromStep2) {
				btnSaveEditeExam.setDisable(false);
			}
			else {
				btnSaveEditeExam.setDisable(true);
			}		
			
		}

		if (screenStatus.equals(super.principalStatusScreen)) {
			principal = (User) ClientUI.loggedInUser.getUser();
			displayPrincipalView = true;
			// load data of the selected exam for view.
			textExamID.setText(exam.getExamID());
			textTimeAllocateForExam.setText(Integer.toString(exam.getTimeOfExam()));
			textTeacherComment.setText(exam.getCommentForTeacher());
			textStudentComment.setText(exam.getCommentForStudents());

			btnSaveEditeExam.setDisable(false);
			btnSaveEditeExam.setVisible(false);
			textNavigation.setVisible(true);
			texTtitleScreen.setText("Exams Details");

			textTimeAllocateForExam.setEditable(false);
			textTeacherComment.setEditable(false);
			textStudentComment.setEditable(false);
		}

	}

	/**
	 * @param examData              with all updated details.
	 * @param displayPrincipalView2 the current screen mode according to logged
	 *                              user.
	 * @param qlist                 list with all update score to be update in DB
	 *                              when teacher clicks on "save exam" button.
	 */
	public static void setDataFromStep2(Exam exam2, boolean displayPrincipalView2, ArrayList<QuestionInExam> qlist, boolean back) {
		exam = exam2;
		displayPrincipalView = displayPrincipalView2;
		updatedQuestions = qlist;
		backFromStep2=back;
	}

}
