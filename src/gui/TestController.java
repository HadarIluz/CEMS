package gui;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.StatusMsg;
import logic.TestRow;
import logic.UpdateDataRequest;

public class TestController {

	public UpdateDataRequest upDataReq;
	//public TestTableRequest test_Treq;
	public TestRow testR;
	public StatusMsg status;

    @FXML
    private Button btnTest;

    @FXML
    private Button btnTable;

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
    private Button pressUpdateTable;

    @FXML
    private Label statusMessage;

    @FXML
    private Font x3;


	/*
	 * the function active when user press on save button.
	 * 
	 */
	@FXML
	public void Save(ActionEvent event) throws Exception {
		String ExamID;
		String TimeForTest;
		String text;
		// Clean
		ReqFiledMessage1.setText("");
		ReqFiledMessage2.setText("");
		statusMessage.setText("");

		// get the exam id`s number that user typed
		ExamID = getExamID();
		if (ExamID.trim().isEmpty()) {
			System.out.println("You must enter an  exam id number"); // message to console.
			// ReqFiled functionality.
			ReqFiledMessage1.setTextFill(Paint.valueOf("Red"));
			text=ChatClient.s.getDescription().toString();
			ReqFiledMessage1.setText(text);
			//ReqFiledMessage1.setText("exanID is Req filed");
		}

		TimeForTest = getTimeForTest();
		if (TimeForTest.trim().isEmpty()) {
			System.out.println("You must enter an time number"); // message to console.
			// ReqFiled functionality.
			ReqFiledMessage2.setTextFill(Paint.valueOf("Red"));
			text=ChatClient.s.getDescription().toString();
			ReqFiledMessage2.setText(text);
			//ReqFiledMessage1.setText("time is Req filed.");
		}
		// in case filed not empty checks if exist in DB
		else if (!ExamID.trim().isEmpty()) {

			// in case Error return from server..
			if (ChatClient.s.getStatus().toString().equals("SUCCESS")) { 
				System.out.println("Exam ID Not Found"); // message to console.
				// ReqFiled functionality.
				text=ChatClient.s.getDescription().toString();
				statusMessage.setTextFill(Paint.valueOf("Red"));
				statusMessage.setText(text);
				//statusMessage.setText("Exam ID Not Found.");
			}

			// Handle a case ExamID found,
			else {
				System.out.println("Exam ID Found"); // message to console.
				statusMessage.setTextFill(Paint.valueOf("Green"));
				statusMessage.setText("updated.");

				upDataReq.getExamID();
				upDataReq.getTimeAllotedForTest();

				System.out.println(upDataReq.toString()); // message for console
			}
		} // END else

	}
	
	// return the Exam ID
	private String getExamID() {
		return txtExamID.getId();
	}

	private String getTimeForTest() {
		return txtTimeForTest.getText();
	}	
	
	public void pressBtnTable(ActionEvent event) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		System.out.println("Table Fram Tool"); //message to console.

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary(Test) window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/TableForm.fxml").openStream());
		
//		TableController tableController = loader.getController();
//		tableController.setTable(ChatClient.testRow);
	
		// call clientUI.chat.accept() --- send request for table data
		//ClientUI.chat.accept(request table data???);

		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/TableForm.css").toExternalForm());
		primaryStage.setTitle("Table Fram");

		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
}
