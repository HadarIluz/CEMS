package guiControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SolveExamController {

	@FXML
	private Button btnSubmit;

	@FXML
	private CheckBox checkBoxShowTime;

	@FXML
	private ImageView nextIcon;

	@FXML
	private ImageView prevIcon;

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
	private Text txtSolveExam;

	@FXML
	private Text txtQuestionDescription;

	@FXML
	private Label lblQuestion;

	@FXML
	private Text txtnotification;

	@FXML
	private ImageView notificationIcon;

	@FXML
	private Label lblnotificationName;

	@FXML
	private Text txtQuestion;

	@FXML
	private Label lblnotificationMsg;
	

}