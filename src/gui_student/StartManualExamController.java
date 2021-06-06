package gui_student;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.ClientUI;
import common.MyFile;
import entity.ActiveExam;
import entity.ExamOfStudent;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class StartManualExamController implements Initializable {

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

	private static StudentController studentController; // why ??
	private static ActiveExam newActiveExam;

	private Student student; // MAYBE NOT NEED BECAUSE STUDENTCONTROLLER ??
	private ExamOfStudent examOfStudent;

	@FXML
	void btnDownload(ActionEvent event) {
		// Download the exam from the database to the student's computer
		RequestToServer req = new RequestToServer("downloadManualExam");
		req.setRequestData(examOfStudent);
		ClientUI.cems.accept(req);
		btnDownload.setDisable(true);
		btnSubmit.setDisable(false);
	}

	@FXML
	void btnSubmit(ActionEvent event) {
		
		Object[] options = { " Cancel ", " Submit " };
		JFrame frame = new JFrame("Submit Exam");
		int dialogResult = JOptionPane.showOptionDialog(frame,
				"Please Note!\nOnce you click Submit you can't edit exam egain.", null,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, // do not use a custom Icon
				options, // the titles of buttons
				null); // default button title
		if (dialogResult == 1) {
			String fileName = examOfStudent.getActiveExam().getExam().getExamID() + "_" + examOfStudent.getStudent().getId()
					+ ".docx";
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
				RequestToServer req = new RequestToServer("submitManualExam");
				req.setRequestData(submitExam);
				ClientUI.cems.accept(req);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			btnSubmit.setDisable(true);
		}
	}

	@FXML
	void checkBoxShowTime(ActionEvent event) {
		// show time left
		if (checkBoxShowTime.isSelected()) {
			textTimeLeft.setDisable(false);
			textTimeLeft.setOpacity(1.0);
		} else { // Do not show time left
			textTimeLeft.setDisable(true);
			textTimeLeft.setOpacity(0.0);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		student = (Student) ClientUI.loggedInUser.getUser();
		examOfStudent = new ExamOfStudent(newActiveExam, student);
	}

	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;
	}

	/**
	 * this method create a popup with a message.
	 * 
	 * @param str
	 */
	public void popUp(String str) {
		final Stage dialog = new Stage();
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(str);
		lbl.setPadding(new Insets(5));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
		dialog.setScene(dialogScene);
		dialog.show();
	}
}
