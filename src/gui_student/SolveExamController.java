package gui_student;

import java.net.URL;
import java.util.ResourceBundle;

import entity.ActiveExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SolveExamController implements Initializable{

	@FXML
	private Button btnSubmitExam;

	@FXML
	private CheckBox checkBoxShowTime;

	@FXML
	private ImageView imgNext;

	@FXML
	private ImageView imgPrev;

	@FXML
	private Label lblTimeLeft;

	@FXML
	private RadioButton btnAnswer1;

	@FXML
	private RadioButton btnAnswer2;

	@FXML
	private RadioButton btnAnswer3;

	@FXML
	private RadioButton btnAnswer4;

	@FXML
	private Label lblQuestionNumber;

	@FXML
	private Label lblPoints;

	@FXML
	private TextArea txtQuestionDescription;

	@FXML
	private Label textQuestion;

	@FXML
	private Text txtnotification;

	@FXML
	private ImageView notificationIcon;

	@FXML
	private Label lblnotificationName;

	@FXML
	private Label lblnotificationMsg;
	
	
    private static StudentController studentController;
    private static ActiveExam newActiveExam;  //check if needed.

    
	@FXML
	void btnAnswer1(MouseEvent event) {

	}

	@FXML
	void btnAnswer2(MouseEvent event) {

	}

	@FXML
	void btnAnswer3(MouseEvent event) {

	}

	@FXML
	void btnAnswer4(MouseEvent event) {

	}

	@FXML
	void btnSubmitExam(ActionEvent event) {

	}

	@FXML
	void checkBoxShowTime(MouseEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;
	}

}
