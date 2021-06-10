package gui_student;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
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
import entity.ExamStatus;
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
import javafx.scene.text.Text;
import logic.RequestToServer;

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
	private Label textTeacherName;

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

	private static StudentController studentController;
	private static ActiveExam newActiveExam;
	private AtomicInteger timeForTimer;
	private Timer timer;
	private ExamOfStudent examOfStudent;
	private static Boolean lockBecauseTeacher = false;
	private Boolean lockBecauseTime = false;
	private static int flag;

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

	@FXML
	void btnSubmit(ActionEvent event) {
		if (!lockBecauseTime && flag != 1) {
			Object[] options = { " Cancel ", " Submit " };
			JFrame frame = new JFrame("Submit Exam");
			int dialogResult = JOptionPane.showOptionDialog(frame,
					"Please Note!\nOnce you click Submit you can't edit exam egain.", null, JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null, // do not use a custom Icon
					options, // the titles of buttons
					null); // default button title
			if (dialogResult == 1) {
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
						RequestToServer req2 = new RequestToServer("StudentFinishManualExam");
						examOfStudent.setExamType("manual");
						examOfStudent
								.setTotalTime((newActiveExam.getTimeAllotedForTest() * 60 - timeForTimer.get()) / 60);
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
		} else {
			timer.cancel();
			examOfStudent.setExamType("manual");
			examOfStudent.setScore(0);
			examOfStudent.setTotalTime((newActiveExam.getTimeAllotedForTest() * 60 - timeForTimer.get()) / 60);
			examOfStudent.setReasonOfSubmit(ReasonOfSubmit.forced);
			if (timeForTimer.get() == 0) {
				// need to check that will not lock if is not the last
				//need to  check if the last (what yuval did)
				//need to add document (what yuval did)

			} else {
				examOfStudent.getActiveExam().getExam().setExamStatus(ExamStatus.inActive);
				RequestToServer req2 = new RequestToServer("lockActiveExam");
				req2.setRequestData(examOfStudent);
				ClientUI.cems.accept(req2);
			}
		}

	}

	@FXML
	void checkBoxShowTime(ActionEvent event) {
		textTimeLeft.setVisible(!textTimeLeft.visibleProperty().get());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		examOfStudent = new ExamOfStudent(newActiveExam, (Student) ClientUI.loggedInUser.getUser());
		// set the timer
		timeForTimer = new AtomicInteger(newActiveExam.getTimeAllotedForTest() * 60);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				int hours = timeForTimer.get() / 3600;
				int minutes = (timeForTimer.get() % 3600) / 60;
				int seconds = timeForTimer.get() % 60;
				String str = String.format("Time left: %02d:%02d:%02d", hours, minutes, seconds);
				Platform.runLater(() -> textTimeLeft.setText(str));
				timeForTimer.decrementAndGet();
				if (timeForTimer.get() == 0 || flag == 1) {
					if (timeForTimer.get() == 0)
						lockBecauseTime = true;
					Platform.runLater(() -> lockExam());
				}
			}
		}, 0, 1000);
	}

	private void lockExam() {
		btnSubmit.setDisable(true);
		popUp("The exam is locked!");
		btnSubmit(null);
	}

	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;

	}

	public static void setFlagToLockExam(int temp) {
		flag = temp;
	}

}
