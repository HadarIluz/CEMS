package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import entity.Question;
import entity.QuestionInExamRow;
import entity.QuestionRow;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import logic.RequestToServer;

public class CreateExam_addQ_step2Controller implements Initializable{

    @FXML
    private Button btnCreateNewQuestion;

    @FXML
    private ImageView imgComputer;

    @FXML
    private Label text100;

    @FXML
    private ImageView imgStep1;

    @FXML
    private ImageView imgStep2;

    @FXML
    private ImageView imgStep3;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnNext;

    @FXML
    private Text textTotalScore;

    @FXML
    private Label textErrorMsg;

    @FXML
    private TableView<QuestionInExamRow> tableAddedQuestions;

    @FXML
    private TableColumn<QuestionInExamRow, String> questionID;

    @FXML
    private TableColumn<QuestionInExamRow, Integer> questionScore;

    @FXML
    private TableColumn<QuestionInExamRow, String> question;

    @FXML
    private Button btnBrowseQuestions;

    @FXML
    private Text ChosenQuestionID;

    @FXML
    private TextField txtChangeScore;

    @FXML
    private Button btnUpdateScore;

    @FXML
    private Button btnDelete;
    
    private static Exam newExam;
    
    private static ArrayList<Question> availableQuestions;
    private ArrayList<QuestionInExamRow> selectedQuestions;

    @FXML
    void DeleteFromExam(ActionEvent event) {

    }

    @FXML
    void UpdateScore(ActionEvent event) {

    }

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnBrowseQuestions(ActionEvent event) {

    }

    @FXML
    void btnCreateNewQuestion(ActionEvent event) {

    }

    @FXML
    void btnNext(ActionEvent event) {

    }

    @FXML
    void chooseQ(MouseEvent event) {

    }

	public static void setExamState(Exam newExamInProgress) {
		newExam = newExamInProgress;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedQuestions = new ArrayList<>();
		
	}
	
	public static void loadAvailableQuestions(ArrayList<Question> questionBank) {
		availableQuestions = questionBank;
	}
	
	public void initTableRows() {

		

		tableAddedQuestions.getColumns().clear();
		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		questionScore.setCellValueFactory(new PropertyValueFactory<>("score"));
		question.setCellValueFactory(new PropertyValueFactory<>("question"));

		tableAddedQuestions.setItems(FXCollections.observableArrayList(selectedQuestions));

		tableAddedQuestions.getColumns().addAll(questionID, questionScore, question);

	}

}
