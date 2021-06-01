package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import entity.Profession;
import entity.Question;
import entity.QuestionRow;
import entity.Teacher;
import gui_cems.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.RequestToServer;

public class QuestionBankController extends TeacherController implements Initializable {

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

	@FXML
	void MouseC(MouseEvent event) {

		ObservableList<QuestionRow> Qlist;
		Qlist = tableQuestion.getSelectionModel().getSelectedItems();
		textQuestionID.setText(Qlist.get(0).getQuestionID());

	}

	@FXML
	void btnCreateNewQuestion(ActionEvent event) {

		try {

			AnchorPane newPaneRight = FXMLLoader.load(getClass().getResource("CreateQuestion.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	@FXML
	void btnDeleteQuestion(ActionEvent event) {

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
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("EditQuestion.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
initTableRows();

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

}
