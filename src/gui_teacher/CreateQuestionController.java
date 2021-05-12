package gui_teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CreateQuestionController {

    @FXML
    private TextField textTheQuestion;

    @FXML
    private TextArea textDescription;

    @FXML
    private Button btnSaveQuestion;

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
    private Text textCorrectAnswerIndex;

    @FXML
    private ImageView imgQuestionHead;

    @FXML
    private Text textNavigation;

    @FXML
    private Button btnBack;

    @FXML
    private Text textProfession;

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnSaveQuestion(ActionEvent event) {

    }

    @FXML
    void selectCorrectAnswer(MouseEvent event) {

    }

    @FXML
    void selectProfession(MouseEvent event) {

    }

}
