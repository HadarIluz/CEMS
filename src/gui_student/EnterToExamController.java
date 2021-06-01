package gui_student;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.ActiveExam;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.RequestToServer;

/**
 * @author Hadar Iluz
 *
 */
public class EnterToExamController extends StudentController implements Initializable {

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

	@FXML
	private Label textConfirm;

	@FXML
	private ComboBox<String> selectActiveExamFromCB;

	private static HashMap<String, ActiveExam> activeExamtMap = new HashMap<String, ActiveExam>();
	private static ArrayList<ActiveExam> activeExamtList = new ArrayList<ActiveExam>();
	private ArrayList<String> examIdList = new ArrayList<String>();
	private ActiveExam activeExam_selection;
	private Student student;

	/**
	 * The method checks that all the conditions for starting the exam have been
	 * approved and verifies the details entered by the student. Verifies that there
	 * is an existing exam right now and moves the student to the test solution
	 * screen according to the exam`s type.
	 * 
	 * @param event event that occurs when clicking on 'start exam' button.
	 */
	@FXML
	void btnStart(ActionEvent event) {
		String examCode = textExamCode.getText().trim();
		String studentID = textStudentID.getText().trim();

		boolean condition = checkConditionToStart(examCode, studentID);
		if (condition) {

//			if (activeExam_selection == null) {
//				popUp("Please choose your exam.");
//			} 
//			else 
//			{
//TODO: Should this activeExam be "assigned" to a student somehow? (new table or something else?)..
//TODO:ASK TEAM.-->do i need to do insert row into table of exam of student?? 

				/*
				 * Gets current time that student try to insert into his active exam The student
				 * has a half-hour range in which he can enter and begin construction from the
				 * exam`s start time.
				 */
				long now = System.currentTimeMillis();
				Time sqlTime = new Time(now);
				// now add half an hour, 1 800 000 miliseconds = 30 minutes
				long halfAnHourLater = (long) now + 1800000;
				Time sqlEndRangeTimeToTakeExam = new Time(halfAnHourLater);
				System.out.println(sqlTime);
				System.out.println(sqlEndRangeTimeToTakeExam);

				ActiveExam activeExam = new ActiveExam(sqlTime, sqlEndRangeTimeToTakeExam, examCode);
				// Request to server to return an examID for this examCode if exist.
				// if not return null.
				RequestToServer req = new RequestToServer("isActiveExamExist");
				req.setRequestData(activeExam);
				ClientUI.cems.accept(req);

				if ((CEMSClient.responseFromServer.getResponseType()).equals("ACTIVE EXAM EXIST")) {
					// At this point we found exam so we can be sure an object has arrived in this
					// response.
					activeExam = (ActiveExam) CEMSClient.responseFromServer.getResponseData();
					String existExamID = activeExam.getExam().getExamID();
					String ActiveExamType = activeExam.getActiveExamType();
					// message in console
					System.out.println("Respont: there is active examID: " + existExamID + " type: " + ActiveExamType); // PRINT

					// The student has entered all the given details and transfer to exam screen
					// - computerized or manual
					// ((Pane) event.getSource()).getScene().getWindow().hide(); // hiding right
					// pane window
					switch (ActiveExamType) {
					case "manual": {
						// load manual start exam fxml
						try {
							Pane newPaneRight = FXMLLoader.load(getClass().getResource("StartManualExam.fxml"));
							newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
							StudentController.root.add(newPaneRight, 1, 0);
							SolveExamController.setActiveExamState(activeExam);
						} catch (IOException e) {
							System.out.println("Couldn't load!");
							e.printStackTrace();
						}
					}
						break;
					case "computerized": {
						// load computerized start exam fxml
						try {
							Pane newPaneRight = FXMLLoader.load(getClass().getResource("SolveExam.fxml"));
							newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
							StudentController.root.add(newPaneRight, 1, 0);
							SolveExamController.setActiveExamState(activeExam);
						} catch (IOException e) {
							System.out.println("Couldn't load!");
							e.printStackTrace();
						}
					}
						break;
					}

				} else {
					popUp("There is no active exam for exam code number: " + examCode);
				}

			}
		//}
	}

	/**
	 * Checks whether the student has filled all the required fields, if not display
	 * popUp message.
	 * 
	 * @param examCode
	 * @param studentID
	 * @return Returns true if all fields are filled currently, otherwise returns
	 *         false.
	 */
	private boolean checkConditionToStart(String examCode, String studentID) {
		boolean approve1 = ApprovalInsrtuctions.isSelected();
		boolean approve2 = CommitPreformByMyself.isSelected();
		textConfirm.setVisible(false);

		if (examCode.length() == 0 || studentID.length() == 0 || examCode.length() != 4) {
			popUp("One or more of the parameters which insert are incorrect. Please try again.");
			return false;
		} else if (!checkStudentID(studentID)) {
			return false;
		} else if (!approve1 || !approve2) {
			textConfirm.setVisible(true);
			return false;
		}

		return true;
	}

	/**
	 * @param studentID
	 * @return true if the id that student insert match to this logged student AND
	 *         if id includes only digits.
	 */
	private boolean checkStudentID(String studentID) {
		if (studentID.equals(String.valueOf(student.getId())) == false && isOnlyDigits(studentID)) {
			popUp("The ID you entered does not match your profile information.\n"
					+ "You can not take this exam. try again");
			return false;
		}
		return true;
	}

	/**
	 * this method checks if the given string includes letters.
	 * 
	 * @param str
	 * @return true only if the String contains something that isn't a digit.
	 */
	private boolean isOnlyDigits(String str) {
		boolean containsLetter = true;
		for (char ch : str.toCharArray()) {
			if (!Character.isDigit(ch)) {
				containsLetter = false;
				System.out.println("id include letter");
				break;
			}
		}
		return containsLetter;
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
		lbl.setPadding(new Insets(15));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
		dialog.setScene(dialogScene);
		dialog.show();
	}

	/**
	 * initialize function to prepare the screen after it is loaded.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		student = (Student) ClientUI.loggedInUser.getUser();
		textConfirm.setVisible(false);
		activeExam_selection = null;
		// implement load active exam into Combo box.
		loadActiveExamToCombobox();

	}

	private void loadActiveExamToCombobox() {
		setActiveExamtMap(activeExamtList);
		for (ActiveExam ae : activeExamtList) {
			examIdList.add(ae.getExam().getExamID());
		}
		//	not working here
		
		selectActiveExamFromCB.setItems(FXCollections.observableArrayList(examIdList));
		//selectActiveExamFromCB.setItems(FXCollections.observableList(examIdList));
		//selectActiveExamFromCB.setItems(FXCollections.observableArrayList(activeExamtMap.keySet()));
		selectActiveExamFromCB.setDisable(false);
	}
	
	
	public static void setActiveExamtMap(ArrayList<ActiveExam> activeExamtList) {
		 for (ActiveExam ae : activeExamtList) {
			 activeExamtMap.put(ae.getExam().getExamID(), ae);
		}
	}


	@FXML
	void selectActiveExam(ActionEvent event) {
		if (activeExamtMap.containsKey(selectActiveExamFromCB.getValue())) {
			activeExam_selection = activeExamtMap.get(selectActiveExamFromCB.getValue());
			System.out.println(activeExam_selection.getExam().getExamID()); //DEBUG
		}

	}

	public static void setAllActiveExamBeforEnter2Exam(ArrayList<ActiveExam> activeExamListFromDB) {
		activeExamtList = activeExamListFromDB;
	}

}
