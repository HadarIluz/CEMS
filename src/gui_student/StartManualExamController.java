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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class StartManualExamController implements Initializable{

    @FXML
    private Button btnDownload;

    @FXML
    private ImageView imgDownload;

    @FXML
    private Button btnSubmit;

    @FXML
    private Text txtUploadSucceed;

    @FXML
    private Text txtError3;

    @FXML
    private Text txtError2;

    @FXML
    private Text txtError1;

    @FXML
    private Text txtMessageFrom;

    @FXML
    private ImageView imgNotification;

    @FXML
    private Label textTeacherName;

    @FXML
    private Label textNotificationMsg;

    @FXML
    private CheckBox checkBoxShowTime;

    @FXML
    private Label textTimeLeft;
    
    private static StudentController studentController;
    private static ActiveExam newActiveExam;

    @FXML
    void btnDownload(ActionEvent event) {

    }

    @FXML
    void btnSubmit(ActionEvent event) {

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
