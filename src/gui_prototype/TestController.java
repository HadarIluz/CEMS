package gui_prototype;

import client.CEMSClient;
import client.ClientUI;
import gui_prototype.TestController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.UpdateDataRequest;

public class TestController {

	public UpdateDataRequest upDataReq = new UpdateDataRequest();

	@FXML
	private Button Btn_showTestForm;

	@FXML
	private Button pressBtnTableForm;

	@FXML
	private Font x1;

	@FXML
	private TextField txtExamID;

	@FXML
	private TextField txtTimeForTest;

	@FXML
	private Label ReqFiledMessage1;

	@FXML
	private Label ReqFiledMessage2;

	@FXML
	private Button pressUpdateTableReq;

	@FXML
	private Label statusMessage;

	@FXML
	private Font x3;

	/*
	 * The function active when user press on save button.
	 */
	@FXML
	public void pressUpdateTableReq(ActionEvent event) throws Exception {
		// Clean
		ReqFiledMessage1.setText("");
		ReqFiledMessage2.setText("");
		statusMessage.setText("");

		String ExamID = txtExamID.getText();
		String TimeForTest = txtTimeForTest.getText();
		if (ExamID.trim().isEmpty()) {
			showMsg(ReqFiledMessage1, "Exam ID is required field.");
		}
		if (TimeForTest.trim().isEmpty() || Integer.parseInt(TimeForTest) <= 0) {
			showMsg(ReqFiledMessage2, "Invalid Time.");
		}
		// in case fields not empty checks if exist in DB
		else if (!ExamID.trim().isEmpty() || ExamID.length() != 6) {
			upDataReq.setExamID(ExamID);
			upDataReq.setTimeAllotedForTest(TimeForTest);
			ClientUI.cems.accept(upDataReq);
			if (CEMSClient.statusMsg.getStatus().equals("ERROR")) {
				showMsg(statusMessage, "Incorrect Value.");
			}
			// Handle a case ExamID found
			else {
				upDataReq.setExamID(ExamID);
				upDataReq.setTimeAllotedForTest(TimeForTest);
				ClientUI.cems.accept(upDataReq);

				statusMessage.setTextFill(Paint.valueOf("Green"));
				statusMessage.setText("updated.");
			}
		}

	}

	// show red commend to client.
	private void showMsg(Label label, String msg) {
		label.setTextFill(Paint.valueOf("Red"));
		label.setText(msg);
	}

	@FXML
	public void pressBtnTableForm(ActionEvent event) throws Exception {
		try {
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Test) window
			Parent root = FXMLLoader.load(getClass().getResource("TableForm.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			primaryStage.setTitle("");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
