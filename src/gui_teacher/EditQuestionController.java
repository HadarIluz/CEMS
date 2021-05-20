package gui_teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class EditQuestionController {

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
    private ComboBox<?> selectProfession;

    @FXML
    private TextField textAnswer1;

    @FXML
    private TextField textAnswer2;

    @FXML
    private TextField textAnswer3;

    @FXML
    private TextField textAnswer4;

    @FXML
    private ComboBox<?> selectCorrectAnswer;

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnSaveEditQuestion(ActionEvent event) {

    }

    @FXML
    void selectCorrectAnswer(MouseEvent event) {

    }

    @FXML
    void selectProfession(MouseEvent event) {

    }

}
