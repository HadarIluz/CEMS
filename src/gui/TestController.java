package gui;

//import java.io.IOException;

import gui.TableController;
import gui.TestController;

import client.ChatClient;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.UpdateDataRequest;

public class TestController {

	public UpdateDataRequest upDataReq;
	// public TestTableRequest test_Treq;
	//public StatusMsg status;

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
	 * 
	 */
	@FXML
	public void pressUpdateTableReq(ActionEvent event) throws Exception {
		String ExamID;
		String TimeForTest;
		//String text;
		// Clean
		ReqFiledMessage1.setText("");
		ReqFiledMessage2.setText("");
		statusMessage.setText("");

		// get the exam id`s number that user typed
		ExamID = txtExamID.getText();
		System.out.println(ExamID); // test
		if (ExamID.trim().isEmpty()) {
			System.out.println("You must enter an exam id number\n"); // message to console.
			// ReqFiled functionality.

			ReqFiledMessage1.setTextFill(Paint.valueOf("Red"));
			//DEBUG: message from server not working...
			//text = ChatClient.statusMsg.getDescription().toString();
			//System.out.println(text);
			//ReqFiledMessage1.setText(text);
			ReqFiledMessage1.setText("examID is Req filed");
		}

		TimeForTest = txtTimeForTest.getText();
		System.out.println(TimeForTest); // test
		if (TimeForTest.trim().isEmpty()) {
			System.out.println("You must enter an time number\n"); // message to console.
			// ReqFiled functionality.
			ReqFiledMessage2.setTextFill(Paint.valueOf("Red"));
			//text = ChatClient.statusMsg.getDescription().toString();
			//ReqFiledMessage2.setText(text);
			ReqFiledMessage2.setText("time is Req filed.");
		}
		// in case filed not empty checks if exist in DB
		else if (!ExamID.trim().isEmpty()) {

			// in case Error return from server..
			String msg = ChatClient.statusMsg.getStatus(); //DEBUG
			System.out.println(msg);
			
			if (msg.equals("ERROR")) {
				System.out.println("Exam ID Not Found"); // message to console.
				// ReqFiled functionality.
				//text = ChatClient.statusMsg.getDescription().toString();
				statusMessage.setTextFill(Paint.valueOf("Red"));
				//statusMessage.setText(text);
				statusMessage.setText("Exam ID Not Found.");
			}

			// Handle a case ExamID found,
			else {
				System.out.println("Exam ID Found"); // message to console.
				statusMessage.setTextFill(Paint.valueOf("Green"));
				statusMessage.setText("updated.");
				
				upDataReq.setExamID(ExamID);
				upDataReq.setTimeAllotedForTest(TimeForTest);
				ClientUI.chat.accept("getRow "+upDataReq.toString());

				System.out.println(upDataReq.toString()); // message for console				
			}
		} // END else
		
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
