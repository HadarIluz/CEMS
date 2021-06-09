package gui_teacher;

import java.net.URL;
import java.util.ResourceBundle;

import Server.CEMSserver;
import client.CEMSClient;
import client.ClientUI;
import entity.Profession;
import entity.Question;
import entity.Teacher;
import gui_cems.GuiCommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.RequestToServer;

public class EditQuestionController extends GuiCommon implements Initializable {

	@FXML
	private Text texTtitleScreen;

	@FXML
	private TextField textExamID;

	@FXML
	private Label textTimeForExam;

	@FXML
	private Button btnSaveEditQuestion;

	@FXML
	private Button btnBack;

	@FXML
	private Text textNavigation;

	@FXML
	private TextField textTheQuestion;

	@FXML
	private TextArea textDescription;

	@FXML
	private TextField textAnswer1;

	@FXML
	private TextField textAnswer2;

	@FXML
	private TextField textAnswer3;

	@FXML
	private TextField textAnswer4;

	@FXML
	private ComboBox<String> selectCorrectAnswer;

	 @FXML
	    private Label msgLabel;

	@FXML
	void btnBack(ActionEvent event) {
		displayNextScreen((Teacher) ClientUI.loggedInUser.getUser(), "QuestionBank.fxml");
	}

	@FXML
	void btnSaveEditQuestion(ActionEvent event) {
		if (!checkAllFieldsNotEmpty())
			return;
		Question editedQuestion= new Question();
		String[] answers= {textAnswer1.getText(),textAnswer2.getText(),textAnswer3.getText(),textAnswer4.getText()};
		editedQuestion.setTeacher((Teacher) ClientUI.loggedInUser.getUser());
		editedQuestion.setQuestionID(textExamID.getText());
		editedQuestion.setProfession(new Profession(textExamID.getText().substring(0,2)));
		editedQuestion.setQuestion(textTheQuestion.getText());
		editedQuestion.setAnswers(answers);
		editedQuestion.setCorrectAnswerIndex(selectCorrectAnswer.getSelectionModel().getSelectedIndex()+1);
		if(!textDescription.getText().isEmpty())
			editedQuestion.setDescription(textDescription.getText());
		RequestToServer req = new RequestToServer("EditQuestion");
		req.setRequestData(editedQuestion);
		ClientUI.cems.accept(req);
		String check = (String)CEMSClient.responseFromServer.getResponseData();
		if(check.equals("false")) {
			msgLabel.setTextFill(Color.RED);
			msgLabel.setVisible(true);
			msgLabel.setText("Edit Question Failed.");
		}
		else {
			msgLabel.setTextFill(Color.GREEN);
			msgLabel.setVisible(true);
			msgLabel.setText("Edit Question Succeed.");
		}
		
	}

	private boolean checkAllFieldsNotEmpty() {
		if (textTheQuestion.getText().isEmpty()) {
			popUp("Question Must Be written.");
			return false;
		}
		if (textAnswer1.getText().isEmpty()) {
			popUp("Please Insert The First Answer.");
			return false;
		}
		if (textAnswer2.getText().isEmpty()) {
			popUp("Please Insert The Second Answer.");
			return false;
		}
		if (textAnswer3.getText().isEmpty()) {
			popUp("Please Insert The Third Answer.");
			return false;
		}
		if (textAnswer4.getText().isEmpty()) {
			popUp("Please Insert The Fourth Answer.");
			return false;
		}
		if(selectCorrectAnswer.getSelectionModel().getSelectedIndex()==-1) {
			popUp("Please Select Correct Answer.");
			return false;			
		}
		return true;
	}

	@FXML
	void selectCorrectAnswer(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textExamID.setText(QuestionBankController.chosenQuestionID);
		RequestToServer req = new RequestToServer("getQuestionDataBy_questionID");
		req.setRequestData(textExamID.getText());
		ClientUI.cems.accept(req);
		Question question = (Question) CEMSClient.responseFromServer.getResponseData();
		textTheQuestion.setText(question.getQuestion());
		textAnswer1.setText(question.getAnswers()[0]);
		textAnswer2.setText(question.getAnswers()[1]);
		textAnswer3.setText(question.getAnswers()[2]);
		textAnswer4.setText(question.getAnswers()[3]);
		textDescription.setText(question.getDescription());
		selectCorrectAnswer.getItems().addAll("Answer 1", "Answer 2", "Answer 3", "Answer 4");	
	}

}
