package gui;

import gui.TableController;

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
import logic.TestRow;

public class TableController {

	@FXML
	private Button pressBtnUpdateTesFiledtForm;

	@FXML
	private Button btnTable;

	@FXML
	private Font x1;

	@FXML
	private TextField txtExamID;

	@FXML
	private Button pressShowBtn;

	@FXML
	private Label txtReqFiledMessage;

	@FXML
	private Label txtTime;

	@FXML
	private Label txtCourse;

	@FXML
	private Label txtProfession;

	@FXML
	private Label txtPoints;

	@FXML
	private Font x3;

	@FXML
	// display the "TestForm" after pressing btnTest from Main.
	public void pressBtnUpdateTesFiledtForm(ActionEvent event) throws Exception {
		try {
			System.out.println("Test Fram Tool display"); // message to console.
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Test) window
			System.out.println("try");
			Parent root = FXMLLoader.load(getClass().getResource("TestForm.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			primaryStage.setTitle("");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	// Displays information for a requested test
	public void pressShowBtn(ActionEvent event) throws Exception {
		String examID;
		TestRow test;

		// Clean
		txtReqFiledMessage.setText("");
		txtProfession.setText("");
		txtCourse.setText("");
		txtTime.setText("");
		txtPoints.setText("");

		// get the exam id`s number that user typed
		examID = txtExamID.getText();
		try {
			// in case filed examID is empty
			if (examID.trim().isEmpty()|| examID.length()!=6 ) {
				System.out.println("You must enter an  exam id number"); // message to console.
				showMsg(txtReqFiledMessage, "Invalid number");
			} else {
				ClientUI.chat.accept("getRow " + examID);
				test = CEMSClient.testRow;
				if (test.getExamID().equals("DoesntExist") || test.getExamID().equals("ERROR")) // Check if the test exists
				{
					System.out.println("Exam ID Not Found"); // message to console.
					showMsg(txtReqFiledMessage, "Exam ID Not Found");
					// Handle a case ExamID found
				} else {
					System.out.println("Exam ID Found"); // message to console.
					txtProfession.setText(test.getProfession());
					txtCourse.setText(test.getCourse());
					txtTime.setText(test.getTimeAllotedForTest());
					txtPoints.setText(test.getPointsPerQuestion());
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} // Hadar note: need to DEBUG with server: > null null null null null

	}
	
	
	
	// show red commend to client.
	private void showMsg(Label label, String msg) {
		label.setTextFill(Paint.valueOf("Red"));
		label.setText(msg);
	}

}
