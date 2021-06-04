package gui_teacher;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

import com.sun.tools.javac.parser.ReferenceParser.ParseException;

import client.CEMSClient;
import client.ClientUI;
import entity.ActiveExam;
import entity.Exam;
import entity.Teacher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.RequestToServer;

public class CreateActiveExamController implements Initializable {

	@FXML
	private Button btnSaveActiveExam;

	@FXML
	private TextField textExamCode;

	@FXML
	private ComboBox<String> selectTime;

	@FXML
	private ImageView imgClock;

	@FXML
	private TextField textExamID;

	@FXML
	private TextField textCourse;

	@FXML
	private TextField textProfession;

	@FXML
	private RadioButton selectManual;

	@FXML
	private RadioButton selectComputerized;

	// private static TeacherController teacherController; // check if not needed.
	private static Exam exam;
	private Teacher teacher;
	private static String activeExamType;

//	private static HashMap<String, Time> startTimeForNewActiveExamtMap = new HashMap<String, Time>();
//	private static ArrayList<Time> startTimeList = new ArrayList<Time>();
//	private ArrayList<String> examIdList = new ArrayList<String>();

	private String[] startTimeArr = { "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00",
			"11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00",
			"15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00" };

	private static boolean toggleFlag = false;
	// private Date date = null; //we need to add to table the date of the exam
	private Time selectedTime;

	@FXML
	void btnSaveActiveExam(ActionEvent event) {
		String examCode = textExamCode.getText().trim();

		if (checkConditionToSaveActiveExam(examCode)) {

			// exam.setAuthor(teacher); // TODO: think with team if need to delete from DB
			exam.setAuthor(teacher);
			ActiveExam newActiveExam = new ActiveExam(selectedTime, exam, examCode , activeExamType);

			// before we create new active exam, Request from server to check that
			// the same examID at the same time not already exist.

			boolean isAllowed = isActiveExamExist(newActiveExam);

			if (isAllowed) {
				RequestToServer req = new RequestToServer("createNewActiveExam");
				req.setRequestData(newActiveExam);
				ClientUI.cems.accept(req);

				// TODO: check SQL in DB for this :)

				if (CEMSClient.responseFromServer.getStatusMsg().getStatus()
						.equals("New active exam created successfully")) {
					popUp("New active exam:" + newActiveExam.getExam().getExamID()
							+ " has been successfully created in the system.");
				}

			} else {
				popUp("This exam: " + newActiveExam.getExam().getExamID()
						+ " was created as active in the same start time.\n" + "This exam can be set to active again "
						+ newActiveExam.getTimeAllotedForTest() + " after " + newActiveExam.getStartTime());

			}

		}

	}

	/**
	 * @param examCode
	 * @return Returns true if all editable and selectable details are correct.
	 *         Otherwise, displays a message and returns a false.
	 */
	private boolean checkConditionToSaveActiveExam(String examCode) {
		boolean selectCompExam = selectComputerized.isSelected();
		boolean selectManualExam = selectManual.isSelected();

		boolean flag = true;
		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append("Error:\n");
		if (examCode.length() != 4 || textExamCode.getText().isEmpty()) {
			strBuilder.append("Exam code must include 4 characters and digits.\n");
			flag = false;
		}
		if (examCode.matches("[a-zA-Z]+") || examCode.matches("[0-9]+")) {
			strBuilder.append("Exam code must include letters and digits.\n");
			flag = false;
		}
		if (selectedTime == null) {
			strBuilder.append("Please choose start time for this exam.\n");
			flag = false;
		}
		if (!selectCompExam && !selectManualExam) {
			strBuilder.append("You need to select exam type.\n");
			flag = false;
		}

		if (!flag) {
			popUp(strBuilder.toString());
		}
		return flag;
	}

	// in order to avoid from create the same Active Exam in the same time!!
	private boolean isActiveExamExist(ActiveExam activeExam) {
		RequestToServer req = new RequestToServer("CheckIfActiveExamAlreadyExists");
		req.setRequestData(activeExam);
		ClientUI.cems.accept(req);

		ActiveExam isActiveExamExists = (ActiveExam) CEMSClient.responseFromServer.getResponseData();

		System.out.println(isActiveExamExists.toString());// DEBUG !!

		if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("Create action is allowed")
				&& isActiveExamExists != null) {
			return true;
		}
		return false;
	}

	/**
	 * @param event that occurs when the teacher chooses an exam type.
	 */
	@FXML
	void selectComputerized(MouseEvent event) {
		activeExamType= "computerized";
		selectManual.setDisable(toggleFlagStatus());

	}

	/**
	 * @param event that occurs when the teacher chooses an exam type.
	 */
	@FXML
	void selectManual(MouseEvent event) {
		activeExamType="manual";
		selectComputerized.setDisable(toggleFlagStatus());

	}

	/**
	 * //Allows you to select a single type of exam
	 * 
	 * @return Returns the opposite value of the boolean variable that was.
	 */
	private boolean toggleFlagStatus() {
		if (toggleFlag == false)
			return toggleFlag = true;
		else
			return toggleFlag = false;
	}

	/**
	 * Receive the selected Exam from previous screen.
	 * 
	 * @param selectedExam
	 */
	public static void setActiveExamState(Exam selectedExam) {
		exam = selectedExam;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		teacher = (Teacher) ClientUI.loggedInUser.getUser();
		selectedTime = null;
		// TODO: get proffe..Name and set into examID label teacher.getProfessions()
		// the same for Course.
		textExamID.setText(exam.getExamID());
		textCourse.setText(exam.getCourse().getCourseID());
		textProfession.setText(exam.getProfession().getProfessionID());
		selectTime.setItems(FXCollections.observableArrayList(startTimeArr)); //load time to combo Box.

	}


	@FXML
	void selectTime(ActionEvent event) {
			
		selectedTime=  java.sql.Time.valueOf(selectTime.getValue());
		
//		String strTime=selectTime.getValue();
//		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
//	    try {
//			Date d = dateFormat.parse(strTime);
//		} catch (java.text.ParseException e) {
//			e.printStackTrace();
//		}
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

}
