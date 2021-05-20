package gui_student;

import java.io.IOException;
import java.util.Calendar;
import client.CEMSClient;
import client.ClientUI;
import entity.ActiveExam;
import entity.Exam;
import entity.ExamOfStudent;
import entity.Student;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.RequestToServer;

/**
 * @author iluzh
 *
 */
public class EnterToExamController {

	@FXML
	private Button btnStart;

	@FXML
	private TextField textExamCode;

	@FXML
	private TextField textStudentID;

	@FXML
	private CheckBox ApprovalInsrtuctions;

	@FXML
	private CheckBox CommitPreformByMyself;

	private Student student;
	private Pane newPaneRight;

	@FXML
	void btnStart(ActionEvent event) {
		String examCode = textExamCode.getText().trim();
		String studentID = textStudentID.getText().trim();

		boolean condition = checkConditionToStart(examCode, studentID);

		if (condition) {
			Calendar date = Calendar.getInstance();
			System.out.println("The current date is : " + date.getTime());
			date.add(Calendar.DATE, 0);

			// Prepared student id
			int id = Integer.parseInt(studentID.trim());
			student.setId(id);

			ActiveExam activeExam = new ActiveExam(date, examCode);
			// create request to server to checks if examID for this examCode and date are exist.
			// if yes so return it examID. else return null.
			RequestToServer req = new RequestToServer("isActiveExamExist");
			req.setRequestData(activeExam);
			ClientUI.cems.accept(req);

			// verify if examID return or not.
			if (CEMSClient.responseFromServer.getResponseData() != null) {

				Exam exam = (Exam) CEMSClient.responseFromServer.getResponseData();
				String existExamID = exam.getExamID();

				// Request from server to return ActiveExamType of this exist active exam we found.
				ActiveExam reqActiveExamType = new ActiveExam(date, new Exam(existExamID), examCode);
				req = new RequestToServer("getActiveExamType");
				req.setRequestData(reqActiveExamType);
				ClientUI.cems.accept(req);

				// server returns examType
				String examType = (String) CEMSClient.responseFromServer.getResponseData();
				System.out.println(examType); // message to console for DEBUG.

				// The student has entered all the given details and transfer to exam screen
				// - computerized or manual
				((Pane) event.getSource()).getScene().getWindow().hide(); // hiding right pane window
				switch (examType) {
				case "manual": {
					// load manual start exam fxml
					try {
						this.newPaneRight = FXMLLoader
								.load(getClass().getResource("/gui_student/StartManualExam.fxml"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					break;
				case "computerized": {
					// load computerized start exam fxml
					try {
						this.newPaneRight = FXMLLoader.load(getClass().getResource("/gui_student/SolveExam.fxml"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					break;
				}//END switch

			} else {

				popUp("There is no active exam for exam code number: " + examCode);

			}

		}
	}

	/**
	 * Checks whether the student has filled all the required fields, if not open
	 * popUp message.
	 * 
	 * @param examCode
	 * @param studentID
	 * @return Returns true if all fields are filled otherwise returns false.
	 */
	private boolean checkConditionToStart(String examCode, String studentID) {
		boolean approve1 = ApprovalInsrtuctions.isSelected();
		boolean approve2 = CommitPreformByMyself.isSelected();

		if (examCode.length() == 0 || studentID.length() == 0 || examCode.length() != 4) {
			popUp("One or more of the parameters which insert are incorrect. Please try again.");
			return false;
		} else if (!approve1 && !approve2) {
			popUp("You must confirm all terms in orderto start the exam!");
			return false;
		}
		return true;
	}

	/**
	 * create a popUp with a given message.
	 * 
	 * @param msg
	 */
	private void popUp(String msg) {
		final Stage dialog = new Stage();
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(msg);
		lbl.setPadding(new Insets(5));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), 15);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	
	// Loading this fxml window while loading this controller from another location
	// Not sure it's need to be like that !!!
	
	/**
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			newPaneRight = FXMLLoader.load(getClass().getResource("/gui_student/EnterToExam.fxml"));
			GridPane root = null;
			root.add(newPaneRight, 1, 0);
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
		}

	}

}
