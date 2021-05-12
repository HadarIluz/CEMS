package gui_teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ScoreApprovalController {

    @FXML
    private ComboBox<?> selectStudent;

    @FXML
    private TextField textCurrentGrade;

    @FXML
    private TextArea textGradeChangeReason;

    @FXML
    private TextField textnewGradeField;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField textExamID;

    @FXML
    private Button btnViewStudentExam;

    @FXML
    private Label textNewGradeReqField;

    @FXML
    void btnUpdate(MouseEvent event) {

    }

    @FXML
    void btnViewStudentExam(ActionEvent event) {

    }

    @FXML
    void selectStudent(MouseEvent event) {

    }

}
