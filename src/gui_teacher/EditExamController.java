package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Exam;
import entity.Profession;
import entity.Question;
import entity.Teacher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class EditExamController implements Initializable{

	  @FXML
	    private Text texTtitleScreen;

	    @FXML
	    private TextField textExamID;

	    @FXML
	    private Button btnBrowseExamQuestions;

	    @FXML
	    private Label textTimeForExam;

	    @FXML
	    private TextArea textTeacherComment;

	    @FXML
	    private TextArea textStudentComment;

	    @FXML
	    private Button btnSaveEditeExam;

	    @FXML
	    private Button btnBack;

	    @FXML
	    private Text textNavigation;

	    @FXML
	    private TextField textTimeAllocateForExam;
    
    private String commentForTeacher;
    private String commentForStudent;
    private String examID;
    private String TimeAllocatedToExam;
    public static Exam editedExam;
    private Teacher teacher;
    
    
    private static TeacherController teacherController; //we will use it for load the prev/next screen ! (using root).
 
    @FXML
    void btnBack(ActionEvent event) {

    }


    @FXML
    void btnSaveEditeExam(ActionEvent event) {

    	commentForTeacher=textTeacherComment.getText();
        commentForStudent=textStudentComment.getText();;
        examID=textExamID.getText();
        TimeAllocatedToExam=textTimeForExam.getText();
        
     // Check that all fields that must be filled are filled.
     		if (textExamID.getText().trim().isEmpty()) {
     			popUp("Please fill the ExamID Field");
     		} else if (textTimeForExam.getText().trim().isEmpty()) {
     			popUp("Please fill the Time Allocated For Exam Field");
     			} else {
     				if (examID.length() == 6 && isOnlyDigits(examID)&& isOnlyDigits(TimeAllocatedToExam))
     				{
     	    		RequestToServer req = new RequestToServer("SaveEditExam");
     	    		req.setRequestData(editedExam);
     	    		ClientUI.cems.accept(req);
     				}
     			}
    }

    @FXML
    void btnBrowseExamQuestions(ActionEvent event) {
    
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		teacher = (Teacher) ClientUI.loggedInUser.getUser();
		
	}
 
  // create a popUp with a message
 		public void popUp(String txt) {
 			final Stage dialog = new Stage();
 			VBox dialogVbox = new VBox(20);
 			Label lbl = new Label(txt);
 			lbl.setPadding(new Insets(5));
 			lbl.setAlignment(Pos.CENTER);
 			lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
 			dialogVbox.getChildren().add(lbl);
 			Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
 			dialog.setScene(dialogScene);
 			dialog.show();
 		}
 		
 		/**
 		 * this method checks if the given string includes letters.
 		 * 
 		 * @param str
 		 * @return true if the String contains only digits.
 		 */
 		private boolean isOnlyDigits(String str) {
 			boolean onlyDigits = true;
 			for (char ch : str.toCharArray()) {
 				if (!Character.isDigit(ch)) {
 					onlyDigits = false;
 					System.out.println("The string contains a character that he does not digit");
 					break;
 				}
 			}
 			System.out.println("isOnlyDigits returns:" + onlyDigits); // message to console
 			return onlyDigits;
 		}

		public static void setActiveExamState(Exam selectedExam) {
			editedExam =selectedExam;
			
		}
 		
 		//TODO: לחשוב על מקרה שבו המורה מוחקת שאלה , לשאלה הזאת יש ניקוד והציון של המבחן ישתנה. אנחנו רוצים לשמור ציון אפס
 		// לחשוב אם מספיק להקפיץ למורה הודעה 
 		//או
 		//להכריח אותה לבצע פעולה מסויימת לאיזון הציון.

}
