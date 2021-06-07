package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Question;
import entity.QuestionInExam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BrowseQuestionController implements Initializable{

    @FXML
    private Button btnSelectQuestion;

    @FXML
    private TextField textQuestionScore;

    @FXML
    private TableView<Question> tableQuestion;

    @FXML
    private TableColumn<Question, String> QuestionID;

    @FXML
    private TableColumn<Question, String> Question;

    @FXML
    private Text textMsg1;
    
    private QuestionInExam selectedQ = null;
    private static ArrayList<Question> availableQuestions;
    private ObservableList<Question> Qlist;


    @FXML
    void clickOnTableRow(MouseEvent event) {
    	
		Qlist = tableQuestion.getSelectionModel().getSelectedItems();
    }

    @FXML
    void selectQuestion(ActionEvent event) {
    	if (textQuestionScore.getText().trim().length() == 0) {
    		// handle with popup
    	}
    	else {
    		selectedQ = new QuestionInExam(Integer.parseInt(textQuestionScore.getText()), Qlist.get(0), null);
    		btnSelectQuestion.getScene().getWindow().hide();
    	}
    	

    }

	public QuestionInExam getSelectedQuestion() {
		
		return selectedQ;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableQuestion.getColumns().clear();
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		Question.setCellValueFactory(new PropertyValueFactory<>("question"));

		tableQuestion.setItems(FXCollections.observableArrayList(availableQuestions));

		tableQuestion.getColumns().addAll(QuestionID, Question);
				
	}
	
	public static void setAvailableQuestions(ArrayList<Question> availableQ) {
		availableQuestions = availableQ;
	}

}
