package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Question;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

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
	private Text textMsg1;

	@FXML
	private Text textMsg2;

	@FXML
	private Button btnCreateNewQuestion;

	@FXML
	private Button btnOpenQuestionInfo;

	@FXML
	private Text textNavigation;

	@FXML
	private TableColumn<QuestionRow, String> QuestionID;

	@FXML
	private TableColumn<QuestionRow, String> Proffesion;

	@FXML
	private TableColumn<QuestionRow, String> Question;

	private ObservableList<QuestionRow> data;

	private static Teacher teacher;
	private static User principal;

	@FXML
	void MouseC(MouseEvent event) {

		ObservableList<QuestionRow> Qlist;
		Qlist = tableQuestion.getSelectionModel().getSelectedItems();
		textQuestionID.setText(Qlist.get(0).getQuestionID());

	}

	/**
	 * @param event 
	 */
	@FXML
	void btnCreateNewQuestion(ActionEvent event) {
		if (!checkForLegalID(textQuestionID.getText()))
			return;
		displayNextScreen(teacher, "CreateQuestion.fxml");
	}

	@FXML
	void btnDeleteQuestion(ActionEvent event) {
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

	@FXML
	void btnEditQuestion(ActionEvent event) {
		if (!checkForLegalID(textQuestionID.getText()))
			return;
		displayNextScreen(teacher, "EditQuestion.fxml");

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (ClientUI.loggedInUser.getUser() instanceof Teacher) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();
			initTableRows();
		}
		else if (ClientUI.loggedInUser.getUser() instanceof User) {
			//setUp before load screen.
			principal = (User) ClientUI.loggedInUser.getUser();
			btnEditQuestion.setDisable(false);
			btnEditQuestion.setVisible(false);
			btnCreateNewQuestion.setDisable(false);
			btnCreateNewQuestion.setVisible(false);
			btnDeleteQuestion.setDisable(false);
			btnDeleteQuestion.setVisible(false);
			textNavigation.setVisible(true);
			textMsg1.setVisible(false);
			textMsg2.setVisible(false);
			initTableRowsForPrincipalView();
		}

	}


	@SuppressWarnings("unchecked")
	public void initTableRows() {
		textQuestionID.setEditable(true);

		RequestToServer req = new RequestToServer("getQuestions");

		req.setRequestData(ClientUI.loggedInUser.getUser().getId());

		ArrayList<QuestionRow> examsOfTeacher = new ArrayList<QuestionRow>();

		ClientUI.cems.accept(req);

		examsOfTeacher = (ArrayList<QuestionRow>) CEMSClient.responseFromServer.getResponseData();

		data = FXCollections.observableArrayList(examsOfTeacher);

		tableQuestion.getColumns().clear();
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("QuestionID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("profession"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Question"));

		tableQuestion.setItems(data);

		tableQuestion.getColumns().addAll(QuestionID, Proffesion, Question);

	}
	
	private void initTableRowsForPrincipalView() {
		textQuestionID.setEditable(true);
		RequestToServer req = new RequestToServer("getQuestions_ofSpecificExam");

		req.setRequestData(ClientUI.loggedInUser.getUser().getId());

		ArrayList<QuestionRow> examsOfTeacher = new ArrayList<QuestionRow>();

		ClientUI.cems.accept(req);

		examsOfTeacher = (ArrayList<QuestionRow>) CEMSClient.responseFromServer.getResponseData();

		data = FXCollections.observableArrayList(examsOfTeacher);

		tableQuestion.getColumns().clear();
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("QuestionID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("profession"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Question"));

		tableQuestion.setItems(data);

		tableQuestion.getColumns().addAll(QuestionID, Proffesion, Question);
		
		
	}
	
	

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
	
	
	//TODO: open window to see the question.
    @FXML
    void btnOpenQuestionInfo(ActionEvent event) {
    	
    }
	

}
