package gui;


import gui.TestController;
import client.CEMSClient;
import client.ClientUI;
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
	// public TestTableRequest test_Treq;
	// public StatusMsg status;

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
	 * the function active when user press on save button.
	 */
	@FXML
	public void pressUpdateTableReq(ActionEvent event) throws Exception {
		//String text;
		// Clean
		ReqFiledMessage1.setText("");
		ReqFiledMessage2.setText("");
		statusMessage.setText("");

		// get the exam id`s number that user typed
		String ExamID = txtExamID.getText();
		if (ExamID.trim().isEmpty()) {
			System.out.println("You must enter an exam id number"); // message to console.
			showMsg(ReqFiledMessage1, "ExamID is Req field.");
			//text = ChatClient.statusMsg.getDescription();
			//System.out.println(text); //test
			//showMsg(ReqFiledMessage1, text);
		}
		// try {
		String TimeForTest = txtTimeForTest.getText();
		if (TimeForTest.trim().isEmpty()) {
			System.out.println("You must enter an time number"); // message to console.
			showMsg(ReqFiledMessage2, "time is Req field.");
			// text = ChatClient.statusMsg.getDescription();
			// showMsg(ReqFiledMessage2, text);
		}
		// in case fields not empty checks if exist in DB
		else if (!ExamID.trim().isEmpty()) {
			try {
				upDataReq.setExamID(ExamID);
				upDataReq.setTimeAllotedForTest(TimeForTest);
				ClientUI.chat.accept(upDataReq);

				if (CEMSClient.statusMsg.getStatus().equals("ERROR")) {
					System.out.println("Exam ID Not Found"); // message to console.
					showMsg(statusMessage, "Exam ID Not Found.");
					// text = ChatClient.statusMsg.getDescription();
					// showMsg(statusMessage, text);
				}
				// try {
				// Handle a case ExamID found,
				else {
					try {
						System.out.println("Exam ID Found"); // message to console.
						upDataReq.setExamID(ExamID);
						upDataReq.setTimeAllotedForTest(TimeForTest);
						ClientUI.chat.accept(upDataReq);

						statusMessage.setTextFill(Paint.valueOf("Green"));
						statusMessage.setText("updated.");

						System.out.println(upDataReq.toString()); // message for console
					} catch (Exception e) {
						e.printStackTrace();
					}

				} // END else1
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // END else

	}

	// show red commend to client.
	private void showMsg(Label label, String msg) {
		label.setTextFill(Paint.valueOf("Red"));
		label.setText(msg);
	}

	@FXML
	public void pressBtnTableForm(ActionEvent event) throws Exception {
		try {
			System.out.println("Table Fram Tool display"); // message to console.
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
