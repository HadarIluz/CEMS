package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import entity.Question;
import entity.QuestionRow;
import entity.Teacher;
import entity.User;
import gui_cems.GuiCommon;
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
import logic.RequestToServer;

/**
 * 
 * @author Nadav Dery
 * @author Yadin Amsalem
 * @version 1.0 build 07/07/2021
 * 
 */
public class QuestionBankController extends GuiCommon implements Initializable {

    @FXML
    private Button btnEditQuestion;

    @FXML
    private Button btnDeleteQuestion;

    @FXML
    private TextField textQuestionID;

    @FXML
    private TableView<QuestionRow> tableQuestion;

	@FXML
	private TableColumn<QuestionRow, String> QuestionID;

	@FXML
	private TableColumn<QuestionRow, String> Proffesion;

	@FXML
	private TableColumn<QuestionRow, String> Question;

    @FXML
    private Text textMsg1;

    @FXML
    private Text textMsg2;

    @FXML
    private Button btnCreateNewQuestion;

    @FXML
    private Text textNavigation;

    @FXML
    private Button btnOpenQuestionInfo;

    
	private ObservableList<QuestionRow> data;
	private static boolean displayPrincipalView = false;
	private static Teacher teacher;
	private static User principal;
	private static String screenStatus;

	/**
	 * method set text of questionID when user select a question row from table
	 * 
	 * @param event occurs when User press on a selected row from table
	 */

	@FXML
	void MouseC(MouseEvent event) {
		ObservableList<QuestionRow> Qlist;
		Qlist = tableQuestion.getSelectionModel().getSelectedItems();
		textQuestionID.setText(Qlist.get(0).getQuestionID());

	}

	/**
	 * method move user to Create new Question screen
	 * 
	 * @param event occurs when User press "Create new Question"
	 */

	@FXML
	void btnCreateNewQuestion(ActionEvent event) {
		if ((textQuestionID.getText().isEmpty())) {
			btnCreateNewQuestion.setDisable(true);
		} else {
			displayNextScreen(teacher, "CreateQuestion.fxml");
		}
	}

	/**
	 * Method use to delete data of question from the teacher's question bank
	 * 
	 * @param event occurs when User press On Delete
	 */
	@FXML
	void btnDeleteQuestion(ActionEvent event) {
		if ((textQuestionID.getText().isEmpty())) {
			btnDeleteQuestion.setDisable(true);
		} else {

			if (!checkForLegalID(textQuestionID.getText()))
				return;

			ObservableList<QuestionRow> Qlist;
			Question questionToDelete = new Question();
			questionToDelete.setQuestionID(textQuestionID.getText());
			questionToDelete.setTeacher(new Teacher(ClientUI.loggedInUser.getUser(), null));
			Qlist = tableQuestion.getSelectionModel().getSelectedItems();
			RequestToServer req = new RequestToServer("DeleteQuestion");
			req.setRequestData(questionToDelete);
			ClientUI.cems.accept(req);

			if (CEMSClient.responseFromServer.getResponseData().equals("FALSE"))
				System.out.println("failed to delete question");
			else
				data.removeAll(Qlist);
			initTableRows();
		}
	}

	/**
	 * Method use to edit data of question from the teacher's question bank
	 * 
	 * @param event occurs when User press On Edit
	 * 
	 */
	@FXML
	void btnEditQuestion(ActionEvent event) {
		if ((textQuestionID.getText().isEmpty())) {
			btnCreateNewQuestion.setDisable(true);

		} else {
			if (!checkForLegalID(textQuestionID.getText()))
				return;
			displayNextScreen(teacher, "EditQuestion.fxml");
		}
	}

	@FXML
	void btnOpenQuestionInfo(ActionEvent event) {
		if (displayPrincipalView) {
			if ((textQuestionID.getText().isEmpty())) {
				btnCreateNewQuestion.setDisable(true);
			} else {
				CreateQuestionController.setNextScreenData(textQuestionID.getText(), displayPrincipalView);
				displayNextScreen(principal, "CreateQuestion.fxml");
			}
		}
	}

	/**
	 * Method initialize for user screen of question bank
	 * 
	 * @param location  for Url location
	 * @param resources of type ResourceBundle
	 * 
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textQuestionID.setEditable(false);
		if (ClientUI.loggedInUser.getUser() instanceof Teacher) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();
			initTableRows();
		}

		if (ClientUI.loggedInUser.getUser() instanceof User) {
			principal = (User) ClientUI.loggedInUser.getUser();
			displayPrincipalView = true;

			btnOpenQuestionInfo.setDisable(false);
			btnOpenQuestionInfo.setVisible(true);
			
			textMsg1.setVisible(false);
			textMsg2.setVisible(false);
			btnCreateNewQuestion.setVisible(false);
			btnCreateNewQuestion.setVisible(false);
			btnEditQuestion.setVisible(false);
			btnEditQuestion.setVisible(false);
			btnDeleteQuestion.setVisible(false);
			btnDeleteQuestion.setVisible(false);
			textNavigation.setVisible(true);
			textQuestionID.setEditable(false);//CHECK!!!
			fillTableForPrincipal_ALLQuestionsInSystem(); // set all exams in cems system into the table
			
		}

	}


	@SuppressWarnings("unchecked")
	private void fillTableForPrincipal_ALLQuestionsInSystem() {
		RequestToServer req = new RequestToServer("getAllQuestionsStoredInSystem");
		ArrayList<QuestionRow> questionList = new ArrayList<QuestionRow>();
		ClientUI.cems.accept(req);

		questionList = (ArrayList<QuestionRow>) CEMSClient.responseFromServer.getResponseData();
		data = FXCollections.observableArrayList(questionList);

		tableQuestion.getColumns().clear();
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("QuestionID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("profession"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Question"));

		tableQuestion.setItems(data);
		tableQuestion.getColumns().addAll(QuestionID, Proffesion, Question);
	}

	/**
	 * initTableRows get from server all exams of the logged teacher and insert into
	 * the table.
	 */

	@SuppressWarnings("unchecked")
	public void initTableRows() {
		textQuestionID.setEditable(true);

		RequestToServer req = new RequestToServer("getQuestions");

		req.setRequestData(ClientUI.loggedInUser.getUser().getId());

		ArrayList<QuestionRow> questionList = new ArrayList<QuestionRow>();

		ClientUI.cems.accept(req);

		questionList = (ArrayList<QuestionRow>) CEMSClient.responseFromServer.getResponseData();

		data = FXCollections.observableArrayList(questionList);

		tableQuestion.getColumns().clear();
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("QuestionID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("profession"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Question"));

		tableQuestion.setItems(data);

		tableQuestion.getColumns().addAll(QuestionID, Proffesion, Question);
		
	}

	/**
	 * Method that check if the givenQuestion ID is legal
	 * 
	 * @param QuestionID send to method to check if legal
	 * @return true if legal, else false
	 */

	public boolean checkForLegalID(String QuestionID) {

		if (QuestionID.length() != 5) {
			popUp("Question ID Must be 5 digits.");
			return false;
		}
		for (int i = 0; i < QuestionID.length(); i++)
			if (!Character.isDigit(QuestionID.charAt(i))) {
				popUp("Question ID Must Contains only digits.");
				return false;
			}
		return true;
	}

}
