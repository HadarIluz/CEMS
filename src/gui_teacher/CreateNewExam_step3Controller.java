package gui_teacher;

import java.io.IOException;

import client.ClientUI;
import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.RequestToServer;

public class CreateNewExam_step3Controller {

    @FXML
    private Text textMsg_newExam;

    @FXML
    private Button btnCreateNewExam;

    @FXML
    private Text textMsg_totalScore;

    @FXML
    private ImageView imgStep1;

    @FXML
    private ImageView imgStep2;

    @FXML
    private ImageView imgStep3;

    @FXML
    private Text textTotalExamScore;

    @FXML
    private Text textExamID;

    @FXML
    private Button btnBack;

    @FXML
    private ImageView imgComputer;
    
    private static Exam newExam;


    @FXML
    void btnBack(ActionEvent event) {
    	CreateExam_addQ_step2Controller.setExamState(newExam);
    	try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_addQ_step2.fxml.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			TeacherController.root.add(newPaneRight, 1, 0);
			CreateExam_addQ_step2Controller.setExamState(newExam);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
    }

    @FXML
    void btnCreateNewExam(ActionEvent event) {
    	btnBack.setDisable(true);
    	RequestToServer req = new RequestToServer("createNewExam");
    	req.setRequestData(newExam);
		ClientUI.cems.accept(req);


    }
    
    public static void setExamState(Exam newExamInProgress) {
		newExam = newExamInProgress;
	}

}
