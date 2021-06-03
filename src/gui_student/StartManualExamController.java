package gui_student;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.ActiveExam;
import entity.ExamOfStudent;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import logic.RequestToServer;

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
    
    private static StudentController studentController; //why ??
    private static ActiveExam newActiveExam;
    
    private Student student; //MAYBE NOT NEED BECAUSE STUDENTCONTROLLER ??
    private ExamOfStudent examOfStudent; 

    @FXML
    void btnDownload(ActionEvent event) {
    	//Download the exam from the database to the student's computer
    	RequestToServer req = new RequestToServer("getManualExam");
		req.setRequestData(examOfStudent);
    	ClientUI.cems.accept(req);	
    }

    @FXML
    void btnSubmit(ActionEvent event) {

    }

    @FXML
    void checkBoxShowTime(MouseEvent event) {
    	//show time left
    	if(checkBoxShowTime.isSelected()) {
    		textTimeLeft.setDisable(false);
    		textTimeLeft.setOpacity(1);
    	} else { // Do not show time left
    		textTimeLeft.setDisable(true);
    		textTimeLeft.setOpacity(0);
    	}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		examOfStudent.setActiveExam(newActiveExam);
		student = (Student) ClientUI.loggedInUser.getUser();
		examOfStudent.setStudent(student);		
	}
	
	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;
	}

}
