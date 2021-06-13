package gui_teacher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import common.MyFile;
import entity.Exam;
import gui_cems.GuiCommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import logic.RequestToServer;

public class EditManualExamStep2 extends GuiCommon implements Initializable {

	@FXML
	private Button btnBrowse;

	@FXML
	private TextField txtPath;

	@FXML
	private Button btnUpload;

	@FXML
	private Button btnBack;

	@FXML
	private Label msgLabel;

	@FXML
	private Label examIDlbls;

	@FXML
	private Label ExamIDLAbel;

	private static Exam newExam;
	private File selectedExamFile;

	public static Exam getNewExam() {
		return newExam;
	}

	public static void setNewExam(Exam exam) {
		newExam = exam;
	}

	@FXML
	void btnBack(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("EditExam.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			TeacherController.root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	@FXML
	void btnUploadPress(ActionEvent event) {

		MyFile uploadFile = new MyFile(newExam.getExamID() + "_exam.docx");
		File fileToDelete = new File(uploadFile.getFileName());
		fileToDelete.delete();

		try {
			byte[] bytes = new byte[(int) selectedExamFile.length()];
			FileInputStream fis = new FileInputStream(selectedExamFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			uploadFile.initArray(bytes.length);
			uploadFile.setSize(bytes.length);
			bis.read(uploadFile.getMybytearray(), 0, bytes.length);
			fis.close();
		} catch (IOException e) {
			popUp("Upload Failed.");
			return;
		}

		RequestToServer req2 = new RequestToServer("submitManualExam");
		req2.setRequestData(uploadFile);
		ClientUI.cems.accept(req2);
		if (CEMSClient.responseFromServer.getResponseType().equals("SUBMIT EXAM")) {
			msgLabel.setTextFill(Color.GREEN);
			msgLabel.setText("File Uploaded Successfully");
			msgLabel.setVisible(true);
			examIDlbls.setVisible(true);
			ExamIDLAbel.setText(newExam.getExamID());
			ExamIDLAbel.setVisible(true);
			btnBack.setDisable(true);
			btnBrowse.setDisable(true);
			btnUpload.setDisable(true);
		} else {
			msgLabel.setTextFill(Color.RED);
			msgLabel.setText("File Upload Failed");
			msgLabel.setVisible(true);
		}
	}

	@FXML
	void onClickBroswe(ActionEvent event) {
		FileChooser fc = new FileChooser();
		selectedExamFile = fc.showOpenDialog(null);
		if (selectedExamFile != null) {
			txtPath.setText(selectedExamFile.getAbsolutePath());

		} else
			popUp("File is not valid !");

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}