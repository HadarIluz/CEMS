package gui_teacher;

import java.io.IOException
;
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
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Pane;
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
    private ObservableList<QuestionInExamRow> selectedQuestionsRows = FXCollections.observableArrayList();
    private ObservableList<QuestionInExamRow> Qlist;

    @FXML
    void DeleteFromExam(ActionEvent event) {
    	selectedQuestionsRows.remove(Qlist.get(0));
    	updateTotalScore();
    }

    @FXML
    void UpdateScore(ActionEvent event) {
    	Qlist.get(0).setScore(Integer.valueOf(txtChangeScore.getText().trim()));
        updateTotalScore();

    }

    @FXML
    void btnBack(ActionEvent event) {
    	try {
    		setQuestionsInNewExam();
    		CreateExam_step1Controller.setExamState(newExam);
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_step1.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			TeacherController.root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

    }

    @FXML
    void btnBrowseQuestions(ActionEvent event) {
    	BrowseQuestionController.setAvailableQuestions(availableQuestions);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowseQuestions.fxml"));
        Scene newScene;
        try {
            newScene = new Scene(loader.load());
        } catch (IOException ex) {
            return;
        }

        Stage inputStage = new Stage();
        inputStage.initOwner(TeacherController.root.getScene().getWindow());
        inputStage.setScene(newScene);
        inputStage.showAndWait();

        QuestionInExam q = loader.<BrowseQuestionController>getController().getSelectedQuestion();
        q.setExam(newExam);
        insertRow(q);
    }

    private void updateTotalScore() {
		int sum = 0;
		for (QuestionInExamRow q : selectedQuestionsRows) {
			sum += q.getScore();
		}
		
		textTotalScore.setText(String.valueOf(sum));
		if (sum == 100) {
			textErrorMsg.setVisible(false);
			btnNext.setDisable(false);
		}
		else {
			textErrorMsg.setVisible(true);
			btnNext.setDisable(true);
		}
		
		
	}

	@FXML
    void btnCreateNewQuestion(ActionEvent event) {

    }

    @FXML
    void btnNext(ActionEvent event) {
    	setQuestionsInNewExam();
    	CreateNewExam_step3Controller.setExamState(newExam);
    	try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateNewExam_step3Controller.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			TeacherController.root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

    }

	private void setQuestionsInNewExam() {
		ArrayList<QuestionInExam> finaleQusetionList = new ArrayList();
    	for (QuestionInExamRow q : selectedQuestionsRows) {
    		finaleQusetionList.add(q.getQuestionObject());
    	}
    	newExam.setExamQuestionsWithScores(finaleQusetionList);
	}

    @FXML
    void chooseQ(MouseEvent event) {
    	 Qlist = tableAddedQuestions.getSelectionModel().getSelectedItems();
    	 txtChangeScore.setText(String.valueOf(Qlist.get(0).getScore()));
    	 ChosenQuestionID.setText(Qlist.get(0).getQuestionID());
    	 
    }

	public static void setExamState(Exam newExamInProgress) {
		newExam = newExamInProgress;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtChangeScore.setText("0");
		initTableCols();
		
		if (newExam.getExamQuestionsWithScores() != null) {
			for (QuestionInExam q : newExam.getExamQuestionsWithScores()) {
				availableQuestions.remove(q.getQuestion());
				insertRow(q);
			}
		}
		
	}
	
	private void insertRow(QuestionInExam q) {
		selectedQuestionsRows.add(new QuestionInExamRow(q.getQuestion().getQuestionID(), q.getScore(), q.getQuestion().getQuestion(), q));
        tableAddedQuestions.refresh();
        availableQuestions.remove(q.getQuestion());
        updateTotalScore();
		
	}

	public static void loadAvailableQuestions(ArrayList<Question> questionBank) {
		availableQuestions = questionBank;
	}
	
	public void initTableCols() {

		tableAddedQuestions.getColumns().clear();
		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		questionScore.setCellValueFactory(new PropertyValueFactory<>("score"));
		question.setCellValueFactory(new PropertyValueFactory<>("question"));

		tableAddedQuestions.setItems(selectedQuestionsRows);
		tableAddedQuestions.getColumns().addAll(questionID, questionScore, question);

	}

}
