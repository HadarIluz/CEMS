package gui_teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BrowseQuestionController {

    @FXML
    private Button btnSelectQuestion;

    @FXML
    private TextField textQuestionScore;

    @FXML
    private TableView<?> tableQuestion;

    @FXML
    private TableColumn<?, ?> QuestionID;

    @FXML
    private TableColumn<?, ?> Question;

    @FXML
    private Text textMsg1;

    @FXML
    void MouseC(MouseEvent event) {

    }

    @FXML
    void selectQuestion(ActionEvent event) {

    }

}
