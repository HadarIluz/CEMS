package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import entity.Question;
import entity.QuestionInExam;
import entity.QuestionInExamRow;
import entity.QuestionRow;
import entity.Teacher;
import entity.User;
import gui_cems.GuiCommon;
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

/**
 * @author Hadar Iluz
 *
 */
public class EditExam_questionsStep2Controller extends GuiCommon implements Initializable {

    @FXML
    private Button btnCreateNewQuestion;
    
    @FXML
    private Text textNavigation;

    @FXML
    private Text textTotal; 

    @FXML
    private Text textTitalScreen_step2;

    @FXML
    private ImageView imgStep1;

    @FXML
    private ImageView imgStep2;

    @FXML
    private Button btnBack;

    @FXML
    private Text textTotalScore;

    @FXML
    private Label textErrorMsg;

	@FXML
	private TableView<QuestionInExamRow> tableQuestion;

	@FXML
	private TableColumn<QuestionInExamRow, String> questionID;

	@FXML
	private TableColumn<QuestionInExamRow, String> questionScore;

	@FXML
	private TableColumn<QuestionInExamRow, String> question;

    @FXML
    private Text textQid;

    @FXML
    private Text ChosenQuestionID;

    @FXML
    private TextField txtChangeScore;

    @FXML
    private Button btnUpdateScore;

	@FXML
	private Button btnDelete;

//	private static TeacherController teacherController;
	public static Exam exam;
	private static Teacher teacher;
	private static User principal;
	private static boolean displayPrincipalView = false;

	//
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
    	EditExamController.setprevScreenData(exam, displayPrincipalView);
    	if (!displayPrincipalView) {
			displayNextScreen(teacher, "/gui_teacher/EditExam.fxml");
		} else {
			displayNextScreen(principal, "/gui_teacher/EditExam.fxml");
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
		q.setExam(exam);
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
			btnBack.setDisable(false);
		} else {
			textErrorMsg.setVisible(true);
			btnBack.setDisable(true);
		}

	}

	@FXML
	void btnCreateNewQuestion(ActionEvent event) {
		// Cancel this button!!!!
	}

	/**
	 * method set text of questionID when user select a question row from table
	 * 
	 * @param event occurs when User press on a selected row from table
	 */
	@FXML
	void chooseQ(MouseEvent event) {
		Qlist = tableQuestion.getSelectionModel().getSelectedItems();
		txtChangeScore.setText(String.valueOf(Qlist.get(0).getScore()));
		ChosenQuestionID.setText(Qlist.get(0).getQuestionID());

	}
	
	//ASK YUVAL
	public static void setExamState(Exam newExamInProgress) {
		exam = newExamInProgress;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (ClientUI.loggedInUser.getUser() instanceof Teacher) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();
			txtChangeScore.setText("0");
			//initTableRows_getFronmServer(); //TODO: NOT WORKING!!
			
			//initTableCols(); //ASK YUVAL
			
			if (exam.getExamQuestionsWithScores() != null) {
				for (QuestionInExam q : exam.getExamQuestionsWithScores()) {
					availableQuestions.remove(q.getQuestion());
					insertRow(q);
				}
			}
			
			
		} else if (ClientUI.loggedInUser.getUser() instanceof User) {
			// setUp before load screen.
			principal = (User) ClientUI.loggedInUser.getUser();
			displayPrincipalView = true;
			
			btnDelete.setDisable(false);
			btnDelete.setVisible(false);
			btnUpdateScore.setDisable(false);
			btnUpdateScore.setVisible(false);
			btnCreateNewQuestion.setDisable(false);
			btnCreateNewQuestion.setVisible(false);
			txtChangeScore.setDisable(false);
			txtChangeScore.setVisible(false);
			textErrorMsg.setDisable(false);
			textErrorMsg.setVisible(false);
			textTitalScreen_step2.setText("Exams Details");
			textTotal.setDisable(false);
			textTotal.setVisible(false);
			textTotalScore.setDisable(false);
			textTotalScore.setVisible(false);
			textQid.setDisable(false);
			textQid.setVisible(false);
			ChosenQuestionID.setDisable(false);
			ChosenQuestionID.setVisible(false);
			textNavigation.setVisible(true);

		}

	}
	
	private void insertRow(QuestionInExam q) {
		selectedQuestionsRows.add(new QuestionInExamRow(q.getQuestion().getQuestionID(), q.getScore(), q.getQuestion().getQuestion(), q));
		tableQuestion.refresh();
        availableQuestions.remove(q.getQuestion());
        updateTotalScore();	
	}

	public void initTableCols() {
		tableQuestion.getColumns().clear();
		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		questionScore.setCellValueFactory(new PropertyValueFactory<>("score"));
		question.setCellValueFactory(new PropertyValueFactory<>("question"));

		tableQuestion.setItems(selectedQuestionsRows);
		tableQuestion.getColumns().addAll(questionID, questionScore, question);
	}
	


	
	public static void setExamData(Exam examData) {
		
	}


	
	
	//TODO : !!!!!!!!!!
	public void initTableRows_getFronmServer() {
		// get Questions for the examID that teacher selected in the Exam Back.
		RequestToServer req = new RequestToServer("getQuestionsByIDForEditExam");
		req.setRequestData(exam.getExamID());
		HashMap<String, Question> allQuestionInExam = new HashMap<String, Question>();

		ClientUI.cems.accept(req);
		allQuestionInExam = ((HashMap<String, Question>) CEMSClient.responseFromServer.getResponseData());

//
		ArrayList<QuestionInExam> finaleQusetionOfExamList = new ArrayList();
		for (QuestionInExamRow q : selectedQuestionsRows) {
			//allQuestionInExam.getOrDefault(finaleQusetionOfExamList, null)
			
			finaleQusetionOfExamList.add(q.getQuestionObject());
		}
		exam.setExamQuestionsWithScores(finaleQusetionOfExamList);
	}

	public static void setnextScreenData(Exam examData, boolean displayPrincipalView2) {
		exam = examData;
		displayPrincipalView=displayPrincipalView2;
	}

}