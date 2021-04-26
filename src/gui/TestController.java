package gui;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
	private Button btnSave;

	@FXML
	private Font x3;

	// return the Exam ID
	private String getExamID() {
		return txtExamID.getId();
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
		FXMLLoader loader = new FXMLLoader();

		// get the exam id`s number that user typed
		ExamID = getExamID();
		if (ExamID.trim().isEmpty()) {
			System.out.println("You must enter an  exam id number"); // message to console.
			// add ReqFiled functionality !!

		}
		// in case filed not empty check if exist in DB
		else {
			ClientUI.chat.accept(ExamID);
			
			//in case Error return from server..
			if(ChatClient.s1.getId().equals("Error"))
			{
				System.out.println("Student ID Not Found"); //message to console.
				// add ReqFiled functionality !!
			}
			
			//Handle a case ExamID found,
			else {
				System.out.println("Student ID Found");
				
				
				
				
				//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				//Stage primaryStage = new Stage();
				//Pane root = loader.load(getClass().getResource("/gui/StudentForm.fxml").openStream());
				//StudentFormController studentFormController = loader.getController();		
				//studentFormController.loadStudent(ChatClient.s1);
			
				//Scene scene = new Scene(root);			
				//scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
				//primaryStage.setTitle("Student Managment Tool");
	
				//primaryStage.setScene(scene);		
				//primaryStage.show();
			}
		}//END else

	}
	
	//need to be on Table form....THINK.
	//the function refresh and update the table filed.
	private void refreshTable() {
		
	}
	
	

}
