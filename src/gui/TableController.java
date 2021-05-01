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

	
	// display the "TestForm" after pressing btnTest from Main.
	@FXML
	public void pressBtnUpdateTesFiledtForm(ActionEvent event) throws Exception {
		try {
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

	
	// Displays information for a requested test
	@FXML
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
		// in case filed examID is empty
		if (examID.trim().isEmpty() || examID.length() != 6) {
			showMsg(txtReqFiledMessage, "Invalid number");
		} else {
			ClientUI.cems.accept("getRow " + examID);
			test = CEMSClient.testRow;
			if (test.getExamID().equals("DoesntExist") || test.getExamID().equals("ERROR")) // Check if the test exists
			{
				showMsg(txtReqFiledMessage, "Exam ID Not Found");
			// Handle a case ExamID found
			} else {
				txtProfession.setText(test.getProfession());
				txtCourse.setText(test.getCourse());
				txtTime.setText(test.getTimeAllotedForTest());
				txtPoints.setText(test.getPointsPerQuestion());
			}
		}

	}

	// show red commend to client.
	private void showMsg(Label label, String msg) {
		label.setTextFill(Paint.valueOf("Red"));
		label.setText(msg);
	}

}
