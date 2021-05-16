package gui_principal;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ApprovalTimeExtentionController implements Initializable{

    @FXML
    private ComboBox<String> selectExam;

    @FXML
    private Label lblAdditionalTime;

    @FXML
    private TextArea textReasonField;

    @FXML
    private Button btnDecline;

    @FXML
    private Button btnApprove;

    private Exam selectedExam;

    @FXML
    void selectExam(ActionEvent event) {
    	}

    
    
    public void loadExamToCombobox() {
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		selectedExam=null;
		
	}

}
