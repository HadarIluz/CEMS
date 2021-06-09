package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Profession;
import entity.Question;
import entity.Teacher;
import entity.User;
import gui_cems.GuiCommon;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.RequestToServer;

/**
 * @author Yuval Hayan
 * @author Hadar Iluz
 *
 */
public class CreateQuestionController extends GuiCommon implements Initializable {

	@FXML
	private TextField textTheQuestion;

	@FXML
	private TextArea textDescription;

	@FXML
	private Button btnSaveQuestion;

	@FXML
	private ComboBox<String> selectProfession;

	@FXML
	private TextField textAnswer1;

	@FXML
	private TextField textAnswer2;

	@FXML
	private TextField textAnswer3;

	@FXML
	private TextField textAnswer4;

	@FXML
	private ComboBox<Integer> selectCorrectAnswer;

	@FXML
	private Text textCorrectAnswerIndex;

	@FXML
	private ImageView imgQuestionHead;

	@FXML
	private Text textNavigation;

	@FXML
	private Button btnBack;

	@FXML
	private Text textProfession;

	private HashMap<String, Profession> professionsMap = null;
	private Integer[] answerNumbers = { 1, 2, 3, 4 };
	private Integer selectedIndex;
	private Profession selectedProfession;

	private static Teacher teacher;
	private static User principal;
	private static String screenStatus;
	private static boolean displayPrincipalView = false;

	private static QuestionBankController questionBankController; // will be needed for btnBack button (for root, to
																	// dispaly the prev screen)
	private static String questionID;

	@FXML
	void btnBack(ActionEvent event) {
		if (!displayPrincipalView) {
			displayNextScreen(teacher, "/gui_teacher/QuestionBank.fxml");
		} else {
			displayNextScreen(principal, "/gui_teacher/QuestionBank.fxml");
		}
	}

	@FXML
	void btnSaveQuestion(ActionEvent event) {
		// go through all inputs
		if (textTheQuestion.getText().trim().length() == 0) {
			popUp("Please fill the Question Field");
		} else if (selectedProfession == null) {
			popUp("Please choose a profession");
		} else if (textAnswer1.getText().trim().length() == 0 || textAnswer2.getText().trim().length() == 0
				|| textAnswer3.getText().trim().length() == 0 || textAnswer4.getText().trim().length() == 0) {
			popUp("Please fill all answers");
		} else if (selectedIndex == null) {
			popUp("Please choose a correct answer");
		} else {
			Question newQuestion = new Question();
			newQuestion.setCorrectAnswerIndex(selectedIndex);
			newQuestion.setProfession(selectedProfession);
			String[] answers = new String[4];
			answers[0] = textAnswer1.getText().trim();
			answers[1] = textAnswer2.getText().trim();
			answers[2] = textAnswer3.getText().trim();
			answers[3] = textAnswer4.getText().trim();
			newQuestion.setAnswers(answers);
			newQuestion.setQuestion(textTheQuestion.getText().trim());
			if (textDescription.getText().trim().length() > 0) {
				newQuestion.setDescription(textDescription.getText().trim());
			}
			newQuestion.setTeacher((Teacher) ClientUI.loggedInUser.getUser());
			// newQuestion.setTeacher(currentTeacher);
			RequestToServer req = new RequestToServer("createNewQuestion");
			req.setRequestData(newQuestion);
			ClientUI.cems.accept(req);
			if (CEMSClient.responseFromServer.getResponseType().equals("SuccessCreateNewQuestion")) {
				popUp("Created new question succesfully");
				// go back to question bank page
				try {

					Pane newPaneRight = FXMLLoader.load(getClass().getResource("QuestionBank.fxml"));
					TeacherController.root.add(newPaneRight, 1, 0);

				} catch (IOException e) {
					System.out.println("Couldn't load!");
					e.printStackTrace();
				}
			}

			else {
				popUp("Failed to create new question, please try again later.");
			}
		}

	}

	@FXML
	void selectCorrectAnswer(ActionEvent event) {
		selectedIndex = selectCorrectAnswer.getValue();
	}

	@FXML
	void selectProfession(ActionEvent event) {
		if (professionsMap.containsKey(selectProfession.getValue())) {
			selectedProfession = professionsMap.get(selectProfession.getValue());
		}
	}

	private void loadProfessionsToCombobox() {
		selectProfession.setItems(FXCollections.observableArrayList(professionsMap.keySet()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedIndex = null;
		selectedProfession = null;
		if (!displayPrincipalView) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();

			professionsMap = TeacherController.getProfessionsMap();
			loadProfessionsToCombobox();
			selectCorrectAnswer.setItems(FXCollections.observableArrayList(answerNumbers));
		}

		else {
			principal = (User) ClientUI.loggedInUser.getUser();
			displayPrincipalView = true;
			// setUp
			btnSaveQuestion.setDisable(false);
			btnSaveQuestion.setVisible(false);
			selectProfession.setDisable(false);
			selectProfession.setVisible(false);
			selectCorrectAnswer.setDisable(false);
			selectCorrectAnswer.setVisible(false);

			textCorrectAnswerIndex.setVisible(true);
			textProfession.setVisible(true);
			textNavigation.setVisible(true);
			loadSelectedQuestionDataToView();
		}

	}

	public void setData_From_QuestionBankController(QuestionBankController questionBankController) {
		this.questionBankController = questionBankController;
	}

	public static void setNextScreenData(String questionIDselected, boolean displayPrincipalView2) {
		questionID = questionIDselected;
		displayPrincipalView = displayPrincipalView2;
	}

	private void loadSelectedQuestionDataToView() {
		RequestToServer req = new RequestToServer("getQuestionDataBy_questionID");
		req.setRequestData(questionID);
		ClientUI.cems.accept(req);

		Question questionForView = (Question) CEMSClient.responseFromServer.getResponseData();
		// questionForView
		// textAnswer1 4 textCorrectAnswerIndex

		textTheQuestion.setText(questionForView.getQuestion());
		textProfession.setText(questionForView.getProfession().getProfessionID());
		String ans[] = questionForView.getAnswers();
		textAnswer1.setText(ans[0]);
		textAnswer2.setText(ans[1]);
		textAnswer3.setText(ans[2]);
		textAnswer4.setText(ans[3]);
		int index = questionForView.getCorrectAnswerIndex();
		textCorrectAnswerIndex.setText(ans[index]);
		textDescription.setText(questionForView.getDescription());
		
		textTheQuestion.setEditable(false);
		textAnswer1.setEditable(false);
		textAnswer2.setEditable(false);
		textAnswer3.setEditable(false);
		textAnswer4.setEditable(false);
		textDescription.setEditable(false);
	}

}
