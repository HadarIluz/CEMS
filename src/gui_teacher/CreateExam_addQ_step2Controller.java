package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import entity.Question;
import entity.QuestionInExam;
import entity.QuestionInExamRow;
import entity.QuestionRow;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private ArrayList<QuestionInExamRow> selectedQuestionsRows;
    private ArrayList<QuestionInExam> selectedQuestions;

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
    	BrowseQuestionController.setAvailableQuestions(availableQuestions);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowseQuestions.fxml"));
        Scene newScene;
        try {
            newScene = new Scene(loader.load());
        } catch (IOException ex) {
            // TODO: handle error
            return;
        }

        Stage inputStage = new Stage();
        inputStage.initOwner(TeacherController.root.getScene().getWindow());
        inputStage.setScene(newScene);
        inputStage.showAndWait();

        QuestionInExam q = loader.<BrowseQuestionController>getController().getSelectedQuestion();
        selectedQuestions.add(q);
        selectedQuestionsRows.add(new QuestionInExamRow(q.getQuestion().getQuestionID(), q.getScore(), q.getQuestion().getQuestion()));
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

		tableAddedQuestions.setItems(FXCollections.observableArrayList(selectedQuestionsRows));

		tableAddedQuestions.getColumns().addAll(questionID, questionScore, question);

	}

}
