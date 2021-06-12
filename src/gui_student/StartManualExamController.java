package gui_student;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.CEMSClient;
import client.ClientUI;
import common.MyFile;
import entity.ActiveExam;
import gui_cems.GuiCommon;
import entity.ExamOfStudent;
import entity.ReasonOfSubmit;
import entity.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import logic.RequestToServer;

/**
 * FIXME: ADD JAVADOC HERE
 * 
 * @author Matar Asaf
 *
 */

public class StartManualExamController extends GuiCommon implements Initializable {

	@FXML
	private Button btnDownload;

	@FXML
	private ImageView imgDownload;

	@FXML
	private Button btnSubmit;

	@FXML
	private Text txtUploadSucceed;

	@FXML
	private Text txtError3;

	@FXML
	private Text txtError2;

	@FXML
	private Text txtError1;

	@FXML
	private Text txtMessageFrom;

	@FXML
	private ImageView imgNotification;

	@FXML
	private Label textNotificationMsg;

	@FXML
	private CheckBox checkBoxShowTime;

	@FXML
	private Label textTimeLeft;

	@FXML
	private TextField textLocalFilePath;

	@FXML
	private TextField textFileName;

	@FXML
	private Text txtDownloadSucceed;

	private static ActiveExam newActiveExam;
	private static Boolean lockBecauseTeacher;
	private Boolean lockBecauseTime;
	private static int addTime;
	private AtomicInteger timeForTimer;
	private Timer timer;
	private ExamOfStudent examOfStudent;
	private int timeLeft;

	/**
	 * The method downloads the test form to the Downloads folder on the student's
	 * computer.
	 * 
	 * @param event that occurs when clicking on 'Download' button.
	 */
	@FXML
	void btnDownload(ActionEvent event) {
		// Download the exam from the database to the student's computer
		RequestToServer req = new RequestToServer("downloadManualExam");
		req.setRequestData(examOfStudent);
		ClientUI.cems.accept(req);
		btnDownload.setDisable(true);
		btnSubmit.setDisable(false);
		txtDownloadSucceed.setVisible(true);
	}

	/**
	 * If the student press Submit the method saves the test that the student solved
	 * in the files folder in the system. The file name consists of examID and
	 * studentID. else, the exam locked and the test is not submitted.
	 * 
	 * @param event that occurs when clicking on 'Submit' button.
	 */
	@FXML
	void btnSubmit(ActionEvent event) {
		// When the student clicks Submit
		if (!lockBecauseTime && !lockBecauseTeacher) {
			Object[] options = { " Cancel ", " Submit " };
			JFrame frame = new JFrame("Submit Exam");
			int dialogResult = JOptionPane.showOptionDialog(frame,
					"Please Note!\nOnce you click Submit you can't edit exam egain.", null, JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null, // do not use a custom Icon
					options, // the titles of buttons
					null); // default button title
			// When the student clicks Submit. the test is saved in the files folder in the
			// system
			if (dialogResult == 1) {
				if (((newActiveExam.getTimeAllotedForTest() - timeForTimer.get()) / 60) < 30)
					popUp("The test was not submitted!\nA test can only be submitted after half an hour has passed since you started it.");
				else {
					String fileName = examOfStudent.getActiveExam().getExam().getExamID() + "_"
							+ examOfStudent.getStudent().getId() + ".docx";
					String home = System.getProperty("user.home");
					String LocalfilePath = home + "/Downloads/" + examOfStudent.getActiveExam().getExam().getExamID()
							+ "_exam.docx";
					MyFile submitExam = new MyFile(fileName);
					try {
						File newFile = new File(LocalfilePath);
						byte[] mybytearray = new byte[(int) newFile.length()];
						FileInputStream fis = new FileInputStream(newFile);
						BufferedInputStream bis = new BufferedInputStream(fis);
						submitExam.initArray(mybytearray.length);
						submitExam.setSize(mybytearray.length);
						bis.read(submitExam.getMybytearray(), 0, mybytearray.length);
						fis.close();
						RequestToServer req = new RequestToServer("submitManualExam");
						req.setRequestData(submitExam);
						ClientUI.cems.accept(req);
						if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("SUBMIT EXAM")) {
							timer.cancel();
							examOfStudent.setScore(0);
							examOfStudent.setReasonOfSubmit(ReasonOfSubmit.initiated);
							txtUploadSucceed.setVisible(true);
							btnSubmit.setDisable(true);
							// Update the details in the exam_of_student table in DB
							RequestToServer req2 = new RequestToServer("StudentFinishManualExam");
							examOfStudent.setExamType("manual");
							examOfStudent.setTotalTime(
									((newActiveExam.getTimeAllotedForTest() + newActiveExam.getExtraTime()) * 60
											- timeForTimer.get()) / 60);
							req2.setRequestData(examOfStudent);
							ClientUI.cems.accept(req2);
						}
					} catch (Exception ex) {
						txtError1.setVisible(true);
						txtError2.setVisible(true);
						txtError3.setVisible(true);
						ex.printStackTrace();
					}
				}
			}
		} else { // When the exam is locked
			timer.cancel();
			examOfStudent.setExamType("manual");
			examOfStudent.setScore(0);
			examOfStudent.setTotalTime(
					((newActiveExam.getTimeAllotedForTest() + newActiveExam.getExtraTime()) * 60 - timeForTimer.get())
							/ 60);
			examOfStudent.setReasonOfSubmit(ReasonOfSubmit.forced);
			RequestToServer req = new RequestToServer("StudentFinishManualExam");
			req.setRequestData(examOfStudent);
			ClientUI.cems.accept(req);
		}
	}

	/**
	 * if the checkBox selected show the timer of time left to solve the exam.
	 * 
	 * @param event that occurs when clicking on 'checkBoxShowTime' checkBox
	 */
	@FXML
	void checkBoxShowTime(ActionEvent event) {
		textTimeLeft.setVisible(!textTimeLeft.visibleProperty().get());
	}

	/**
	 * Clicking on the notification disappears it from the screen.
	 *
	 * @param event that occurs when clicking on 'imgNotification' ImageView
	 */
	@FXML
	void clickImgNotification(MouseEvent event) {
		imgNotification.setVisible(false);
		txtMessageFrom.setVisible(false);
		textNotificationMsg.setVisible(false);
	}

	/**
	 * initialize function to prepare the screen after it is loaded. Runs a timer
	 * that shows the time left to solve the test. When receiving test lock
	 * information stops the timer, notifies the student and executes the btnSubmit
	 * method. When receives information about extra time, updates the timer and
	 * informs the student.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		examOfStudent = new ExamOfStudent(newActiveExam, (Student) ClientUI.loggedInUser.getUser());
		lockBecauseTeacher = false;
		lockBecauseTime = false;
		addTime = 0;
		// set the timer
		LocalTime currentTime = (new Time(System.currentTimeMillis())).toLocalTime();
		int timeToDeduct = (currentTime.toSecondOfDay() - newActiveExam.getStartTime().toLocalTime().toSecondOfDay())
				/ 60;
		int timeForStudent = (newActiveExam.getTimeAllotedForTest() - timeToDeduct)*60;
		timeForTimer = new AtomicInteger(timeForStudent);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() { // When extra time was received
				// When extra time is received updates the timer and notifies the student
				if (addTime != 0) {
					newActiveExam.setExtraTime(addTime);
					timeLeft = timeForTimer.get() + (addTime * 60);
					timeForTimer.set(timeLeft);
					Platform.runLater(() -> textNotificationMsg.setText("Please note, the exam time\nwas extended by "
							+ newActiveExam.getExtraTime() + " minutes."));
					imgNotification.setVisible(true);
					txtMessageFrom.setVisible(true);
					textNotificationMsg.setVisible(true);
					addTime = 0;
				}
				int hours = timeForTimer.get() / 3600;
				int minutes = (timeForTimer.get() % 3600) / 60;
				int seconds = timeForTimer.get() % 60;
				String str = String.format("Time left: %02d:%02d:%02d", hours, minutes, seconds);
				Platform.runLater(() -> textTimeLeft.setText(str));
				timeForTimer.decrementAndGet();
				if (timeForTimer.get() == 0 || lockBecauseTeacher) {
					if (timeForTimer.get() == 0)
						lockBecauseTime = true;
					Platform.runLater(() -> lockExam());
				}
			}
		}, 0, 1000);

	}

	private void lockExam() {
		// btnDownload.setDisable(true);
		btnSubmit.setDisable(true);
		popUp("The exam is locked!");
		btnSubmit(null);
	}

	/**
	 * Receive ActiveExam from the previous screen.
	 * 
	 * @param newActiveExamInProgress
	 */
	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;
	}

	/**
	 * Receive temp = true from the server when a teacher locks up the test
	 * 
	 * @param Boolean temp
	 */
	public static void setFlagToLockExam(Boolean temp) {
		lockBecauseTeacher = temp;
	}

	/**
	 * Receives from the server the time the teacher added to the test
	 * 
	 * @param time
	 */
	public static void addTimeToExam(int time) {
		addTime = time;
	}

}
