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

public class EditExamController {

    @FXML
    private ComboBox<?> selectQuestion;

    @FXML
    private Text texTtitleScreen; //change  teacher and principal 

    @FXML
    private TextField textExamID;

    @FXML
    private Button btnShowQuestion;

    @FXML
    private Button btnRemoveQuestion;

    @FXML
    private Label textTimeForExam;

    @FXML
    private TextArea textTeacherComment;

    @FXML
    private TextArea textStudentComment;

    @FXML
    private Button btnUpdate_studentComment;

    @FXML
    private Button btnUpdate_teacherComment;

    @FXML
    private Button btnSaveEditeExam;

    @FXML
    private Button btnBack;

    @FXML
    private Text textNavigation;

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnRemoveQuestion(MouseEvent event) {

    }

    @FXML
    void btnSaveEditeExam(ActionEvent event) {

    }

    @FXML
    void btnShowQuestion(ActionEvent event) {

    }

    @FXML
    void btnUpdate_studentComment(MouseEvent event) {

    }

    @FXML
    void btnUpdate_teacherComment(MouseEvent event) {

    }

    @FXML
    void selectQuestion(MouseEvent event) {

    }

}
