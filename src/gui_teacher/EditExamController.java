package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Exam;
import entity.Profession;
import entity.Question;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class EditExamController implements Initializable{

    @FXML
    private ComboBox<String> selectQuestion;

    @FXML
    private Text texTtitleScreen; //change  teacher and principal 

    @FXML
    private TextField textExamID;

    @FXML
    private Button btnShowQuestion;

    @FXML
    private Button btnRemoveQuestion;

    @FXML
    private Label textTimeForExam;

    @FXML
    private TextArea textTeacherComment;

    @FXML
    private TextArea textStudentComment;

    @FXML
    private Button btnSaveEditExam;

    @FXML
    private Button btnBack;

    @FXML
    private Text textNavigation;
    
    private String commentForTeacher; // comment for teacher
    private String commentForStudent; // comment for student 
    private String examID; // exam ID
    private String TimeAllocatedToExam; // time allocate for exam
    public static Exam editedExam; // new exam - the edited exam
    private static HashMap<String, Question> questionsMap = null; // hash map for questions
    private Question selectedQuestion; //the question that is chosen to be seen from comboBox
    
    private ArrayList<Question> questions; // questions of exist exam- getting it from DB
    private HashMap<String,Integer> questionScores; // mapping the questionID(string) to score(integer)- getting it from DB
   
    //TODO: principal ???
    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnRemoveQuestion(MouseEvent event) {
    	questionsMap.remove(selectedQuestion);
    	//removing question is changing total score of exam-we need to let the user know and notice for it
    	popUp("Pay Attention: remove question is chanching eaxm total score");
    }

    @FXML
    void btnSaveEditExam(ActionEvent event) {

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
     					{editedExam=new Exam(examID); //examID is the only PK. There are FK (profession and course) that create together the PK.
     					
     					
     					}	    
     				//editedExam.setTeacher(currentTeacher);???
     				
     	    		RequestToServer req = new RequestToServer("SaveEditExam");
     	    		req.setRequestData(editedExam);
     	    		ClientUI.cems.accept(req);		
     			}
    }

    @FXML
    void btnShowQuestion(ActionEvent event) {
    	//TODO: load screen of edit question
    	
    	/*I don't agree to load screen of edit question- 
    	If we do- how the user will go back? the "back" in edit question screen
    	 won't go back to edit exam.*/
    	
    	popUp(selectedQuestion.getQuestion());	
    }

    @FXML
    void selectQuestion(MouseEvent event) {
    	if (questionsMap.containsKey(selectQuestion.getValue())) {
    		selectedQuestion = questionsMap.get(selectQuestion.getValue());
    	}

    }
    
    //load questions from hashMap to comboBox. In comboBox we will see questionID
    public void loadQuestionsToCombobox() {
    	selectQuestion.setItems(FXCollections.observableArrayList(questionsMap.keySet()));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedQuestion = null;
		
		
		//TODO:1. req from server data of the specific exam according to examID.
		RequestToServer req = new RequestToServer("getEditExamData");
 		req.setRequestData(editedExam);
 		ClientUI.cems.accept(req);
 		
 		//goal: edited exam is returning here with all exist exam info.	
		
		
		//2. response(  return Exam)  RequestToServer req = new RequestToServer("getEditExamData");
		//3. insert data to test filed by setText from object.
			
		
		loadQuestionsToCombobox();
		
		//ClientUI.loggedInUser..... המורה מחזירה את רשימתה קורסים שלה ואת השם.???
	}
    
	//set hashMap of questions 
    public static void setQestionArrayList(ArrayList<Question> questionsList) {
    	questionsMap = new HashMap<>();
		for (Question q:questionsList) {
			questionsMap.put(q.getQuestionID(), q);
		}
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
 		
 		//TODO: לחשוב על מקרה שבו המורה מוחקת שאלה , לשאלה הזאת יש ניקוד והציון של המבחן ישתנה. אנחנו רוצים לשמור ציון אפס
 		// לחשוב אם מספיק להקפיץ למורה הודעה 
 		//או
 		//להכריח אותה לבצע פעולה מסויימת לאיזון הציון.

}
