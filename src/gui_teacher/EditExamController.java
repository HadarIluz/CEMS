package gui_teacher;

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
    private Button btnUpdate_studentComment;

    @FXML
    private Button btnUpdate_teacherComment;

    @FXML
    private Button btnSaveEditeExam;

    @FXML
    private Button btnBack;

    @FXML
    private Text textNavigation;
    
    private String commentForTeacher;
    private String commentForStudent;
    private String examID;
    private String TimeAllocatedToExam;
    public static Exam editedExam;
    private static HashMap<String, Question> questionsMap = null;
    private Question selectedQuestion;
   
    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnRemoveQuestion(MouseEvent event) {
    	questionsMap.remove(selectedQuestion);
    }

    @FXML
    void btnSaveEditeExam(ActionEvent event) {

    }

    @FXML
    void btnShowQuestion(ActionEvent event) {
    	popUp(selectedQuestion.getQuestion());
    }

    @FXML
    void btnUpdate_studentComment(MouseEvent event) {
    	commentForStudent=textStudentComment.getText();
    }

    @FXML
    void btnUpdate_teacherComment(MouseEvent event) {
    	commentForTeacher=textTeacherComment.getText();
    }

    @FXML
    void selectQuestion(MouseEvent event) {
    	if (questionsMap.containsKey(selectQuestion.getValue())) {
    		selectedQuestion = questionsMap.get(selectQuestion.getValue());
    	}

    }
    
    //load questions from hashMap to combobox. In combobox we will see questionID
    public void loadQuestionsToCombobox() {
    	selectQuestion.setItems(FXCollections.observableArrayList(questionsMap.keySet()));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedQuestion = null;
		loadQuestionsToCombobox();
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

}
