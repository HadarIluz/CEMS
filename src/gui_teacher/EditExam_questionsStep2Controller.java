package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import entity.QuestionInExam;
import entity.QuestionInExamRow;
import entity.Teacher;
import entity.User;
import gui_cems.GuiCommon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * @author Hadar Iluz
 *
 */
public class EditExam_questionsStep2Controller extends GuiCommon implements Initializable {


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
	private TableView<QuestionInExam> tableQuestion;

	@FXML
	private TableColumn<QuestionInExam, String> questionID;

	@FXML
	private TableColumn<QuestionInExam, String> questionScore;

	@FXML
	private TableColumn<QuestionInExam, String> question;

    @FXML
    private Text textQid;

    @FXML
    private Text ChosenQuestionID;

    @FXML
    private TextField txtChangeScore;

    @FXML
    private Button btnUpdateScore;

    @FXML
    private Text textNavigation;


	public static Exam exam;
	private static Teacher teacher;
	private static User principal;
	private static boolean displayPrincipalView;

	private static ArrayList<QuestionInExam> existsQuestions;
	private ObservableList<QuestionInExam> selectedQuestionsRows = FXCollections.observableArrayList();
	private ObservableList<QuestionInExam> Qlist;

	@FXML
	void UpdateScore(ActionEvent event) {
		if (!Qlist.isEmpty()) {
			Qlist.get(0).setScore(Integer.valueOf(txtChangeScore.getText().trim()));
			updateTotalScore();
		}
	}

	@FXML
	void btnBack(ActionEvent event) {
		//bring to the prev screen all the existsQuestions if the new  after update!.
		//when teacher will press on the save edit exam the data will saved in the DB by server.
		EditExamController.setprevScreenData(exam, displayPrincipalView, existsQuestions );
		if (!displayPrincipalView) {
			displayNextScreen(teacher, "/gui_teacher/EditExam.fxml");
					
			
		} else {
			displayNextScreen(principal, "/gui_teacher/EditExam.fxml");
		}
	}
	

	private void updateTotalScore() {
		int sum = 0;
		for (QuestionInExam q : selectedQuestionsRows) {
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
	
	private String calcTotalScore() {
		int sum = 0;
		for (QuestionInExam q : existsQuestions) {
			sum += q.getScore();
		}
		return Integer.toString(sum);
	}


	/**
	 * method set text of questionID when user select a question row from table
	 * 
	 * @param event occurs when User press on a selected row from table
	 */
	@FXML
	void chooseQ(MouseEvent event) {
		Qlist = tableQuestion.getSelectionModel().getSelectedItems();
		if (Qlist.isEmpty()) {
			return;
		}
		txtChangeScore.setText(String.valueOf(Qlist.get(0).getScore()));
		ChosenQuestionID.setText(Qlist.get(0).getQuestionID());

	}

	public static void setExamState(Exam newExamInProgress) {
		exam = newExamInProgress;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bring all exam details (also questions and scores)

		RequestToServer req = new RequestToServer("getFullExamDetails");
		req.setRequestData(exam);
		ClientUI.cems.accept(req);

		exam = (Exam) CEMSClient.responseFromServer.getResponseData();
		existsQuestions = exam.getExamQuestionsWithScores(); // Return ArrayList<QuestionInExam>

		if (!displayPrincipalView) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();		

			//verify teacher not delete all of this questions before she edit this exam!
			if (exam.getExamQuestionsWithScores() != null) {
				initTable(); 
			}
			// when exam open at first for edit the total score is 100 !.
			textTotalScore.setText(calcTotalScore());
			System.out.println(calcTotalScore());
			textErrorMsg.setVisible(false); 


		} else if (ClientUI.loggedInUser.getUser() instanceof User) {
			// setUp before load screen.
			principal = (User) ClientUI.loggedInUser.getUser();
			displayPrincipalView = true;

			btnUpdateScore.setDisable(false);
			btnUpdateScore.setVisible(false);
			txtChangeScore.setDisable(false);
			txtChangeScore.setVisible(false);
			textErrorMsg.setDisable(false);
			textErrorMsg.setVisible(false);
			textTitalScreen_step2.setText("Exams Details");
			textTotalScore.setDisable(false);
			textTotalScore.setVisible(false);
			textTotalScore.setDisable(false);
			textTotalScore.setVisible(false);
			textQid.setDisable(false);
			textQid.setVisible(false);
			ChosenQuestionID.setDisable(false);
			ChosenQuestionID.setVisible(false);
			textNavigation.setVisible(true);

		}

	}


	@SuppressWarnings("unchecked")
	public void initTable() {
		for(QuestionInExam curr:existsQuestions) { 
			curr.setQuestionID();
			curr.setQuestionDescription();
		}
		Qlist = FXCollections.observableArrayList(existsQuestions);
		
		tableQuestion.getColumns().clear(); //DEBUG
		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		questionScore.setCellValueFactory(new PropertyValueFactory<>("score"));
		question.setCellValueFactory(new PropertyValueFactory<>("questionDescription"));
		
		tableQuestion.setItems(Qlist);
		tableQuestion.getColumns().addAll(questionID, questionScore, question);

	}

	public static void setnextScreenData(Exam examData, boolean displayPrincipalView2) {
		exam = examData;
		displayPrincipalView = displayPrincipalView2;
	}

}