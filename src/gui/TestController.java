package gui;


import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TestController {

    @FXML
    private Button btnTest=null;

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
    
    

	// return the Exam ID
	private String getExamID() {
		return txtExamID.getId();
	}
	
	private String getTimeForTest() {
		return txtTimeForTest.getText();
	}
	

	/*
	 * private String getTimeForTest() { return txtTimeForTest.getId(); }
	 */

	/*
	 * the function active when user press on save button.
	 * 
	 */
	public void Save(ActionEvent event) throws Exception {
		String ExamID;
		String TimeForTest;
		FXMLLoader loader = new FXMLLoader();  //??think..
		//Clean
		ReqFiledMessage1.setText("");
		ReqFiledMessage2.setText("");
		statusMessage.setText("");

		// get the exam id`s number that user typed
		ExamID = getExamID();
		if (ExamID.trim().isEmpty()) {
			System.out.println("You must enter an  exam id number"); // message to console.
			//ReqFiled functionality.
			ReqFiledMessage1.setTextFill(Paint.valueOf("Red"));
			ReqFiledMessage1.setText("exanID is Req filed");
		}
		
		TimeForTest = getTimeForTest();
		if (TimeForTest.trim().isEmpty()) {
			System.out.println("You must enter an time number"); // message to console.
			//ReqFiled functionality.
			ReqFiledMessage1.setTextFill(Paint.valueOf("Red"));
			ReqFiledMessage1.setText("time is Req filed.");
		}
		// in case filed not empty checks if exist in DB
		else if(!ExamID.trim().isEmpty()) {
			ClientUI.chat.accept(ExamID);
			
			//in case Error return from server..
			if(ChatClient.s1.getId().equals("Error"))
			{
				System.out.println("Exam ID Not Found"); //message to console.
				//ReqFiled functionality.
				statusMessage.setTextFill(Paint.valueOf("Red"));
				statusMessage.setText("Exam ID Not Found.");
			}
			
			//Handle a case ExamID found,
			else {
				System.out.println("Exam ID Found"); //message to console.
				statusMessage.setTextFill(Paint.valueOf("Green"));
				statusMessage.setText("updated.");
				
				//func that send to server "update " + .toString().....
				

			}
		}//END else

	}
	
	
	
	/*
	
	//need to be on Table form....THINK.
	//the function refresh and update the table filed.
	private void refreshTable() {
		
	}
	*/
	

}
