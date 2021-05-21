package gui_teacher;

import java.util.HashMap;

import client.CEMSClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import logic.RequestToServer;
//
public class ScoreApprovalController {

	@FXML
	private ComboBox<String> selectStudent;

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
	private Button btnShow;

	@FXML
	private Label txtExamIdError;
	
	HashMap<String, Integer> stdScore = new HashMap<>();
	
	

	@FXML
	void btnUpdate(ActionEvent event) {
//check if fields are empty
		String changeReason = textGradeChangeReason.getText();
		String newGrade = textnewGradeField.getText();
		
		 if (changeReason.isEmpty()||newGrade.trim().isEmpty())
		 { textNewGradeReqField.setVisible(true);}
		 else {
	//chack grade legal
		 
		 try {
		 int gradeForUpdate= Integer.parseInt(newGrade);
		 
		 	 if (gradeForUpdate>100||gradeForUpdate<0)
			 throw new NumberFormatException();
		 	 else  textNewGradeReqField.setVisible(false);
		 
		 }
		 
		 catch(NumberFormatException e){  
			 textNewGradeReqField.setText("grade is not legal,check again!");
			 textNewGradeReqField.setVisible(true);
		 
		 }
		 }
		
		 RequestToServer req=new RequestToServer("Update Grade");
		 
		 req.setRequestData(null);
		
		 
		 
		
		
		
	}

	@FXML
	void btnViewStudentExam(ActionEvent event) {

	}

	@FXML
	void selectStudent(ActionEvent  event) {
		textCurrentGrade.setText(String.valueOf(stdScore.get(selectStudent.getSelectionModel().getSelectedItem())));
	}

	@SuppressWarnings("unchecked")
	@FXML
	void ShowStudentByExamID(ActionEvent event) {
		
		String ExamID = textExamID.getText();
		/*
		 * if (ExamID.trim().isEmpty()) { txtExamIdError.setVisible(true);
		 * 
		 * } RequestToServer req = new RequestToServer("getStudentsByExamID");
		 * req.setRequestData(ExamID); ClientUI.cems.accept(req); stdScore =
		 * (HashMap<String, Integer>) CEMSClient.responseFromServer.getResponseData();
		 */
		selectStudent.setDisable(false);
		stdScore.put("2033327754", 63);
		stdScore.put("2343243244", 92);
		stdScore.put("201416187", 100);
		
		selectStudent.setItems(FXCollections.observableArrayList(stdScore.keySet()));
		
		
		

	}

}
