package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Profession;
import entity.Question;
import entity.Teacher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class CreateQuestionController extends QuestionBankController implements Initializable{

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
    
    private static HashMap<String, Profession> professionsMap = null;
    private Integer[] answerNumbers = {1, 2, 3, 4};
    private Integer selectedIndex;
    private Profession selectedProfession;

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnSaveQuestion(ActionEvent event) {
    	// go through all inputs
    	if (textTheQuestion.getText().trim().length() == 0) {
    		popUp("Please fill the Question Field");
    	}
    	else if (selectedProfession == null) {
    		popUp("Please choose a profession");
    	}
    	else if (textAnswer1.getText().trim().length() == 0 || textAnswer2.getText().trim().length() == 0 || textAnswer3.getText().trim().length() == 0 || textAnswer4.getText().trim().length() == 0) {
    		popUp("Please fill all answers");
    	}
    	else if (selectedIndex == null) {
    		popUp("Please choose a correct answer");
    	}
    	else {
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
    		//newQuestion.setTeacher(currentTeacher);
    		RequestToServer req = new RequestToServer("createNewQuestion");
    		req.setRequestData(newQuestion);
    		ClientUI.cems.accept(req);
    	}
    	
    }

    @FXML
    void selectCorrectAnswer(MouseEvent event) {
    	selectedIndex = selectCorrectAnswer.getValue();
    }

    @FXML
    void selectProfession(MouseEvent event) {
    	if (professionsMap.containsKey(selectProfession.getValue())) {
    		selectedProfession = professionsMap.get(selectProfession.getValue());
    	}
    }
    
    public void loadProfessionsToCombobox() {
   // 	selectProfession.setItems(FXCollections.observableArrayList(professionsMap.keySet()));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedIndex = null;
		selectedProfession = null;
		loadProfessionsToCombobox();
		selectCorrectAnswer.setItems(FXCollections.observableArrayList(answerNumbers));
		
	}
	
	public static void setProfessionMap(ArrayList<Profession> professionsList) {
		professionsMap = new HashMap<>();
		for (Profession p: professionsList) {
			professionsMap.put(p.getProfessionName(), p);
		}
	}
    
	// create a popup with a message
		public void popUp(String txt) {
			final Stage dialog = new Stage();
			VBox dialogVbox = new VBox(20);
			Label lbl = new Label(txt);
			lbl.setPadding(new Insets(5));
			lbl.setAlignment(Pos.CENTER);
			lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			dialogVbox.getChildren().add(lbl);
			Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
			dialog.setScene(dialogScene);
			dialog.show();
		}

}
