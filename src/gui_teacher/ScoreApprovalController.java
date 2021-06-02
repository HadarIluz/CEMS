package gui_teacher;

import java.util.HashMap;

import client.CEMSClient;
import client.ClientUI;
import entity.UpdateScoreRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
	// <StudentID,Score>

	public boolean isLegalScore(String score) {

		for (int i = 0; i < score.length(); i++) {
			if (!Character.isDigit(score.charAt(i))) {
				textNewGradeReqField.setTextFill(Color.RED);
				textNewGradeReqField.setText("Score Must Contains only digits.");
				textNewGradeReqField.setVisible(true);
				return false;
			}
		}
		int Score = Integer.parseInt(score);

		if (Score < 0 || Score > 100) {
			textNewGradeReqField.setTextFill(Color.RED);
			textNewGradeReqField.setText("Score must be between 0 to 100.");
			textNewGradeReqField.setVisible(true);
			return false;
		}
		return true;
	}

	@FXML
	void btnUpdate(ActionEvent event) {

		textNewGradeReqField.setVisible(false);

		String changeReason = textGradeChangeReason.getText();
		String newGrade = textnewGradeField.getText();
		//check if student selected
		
		if(selectStudent.getSelectionModel().getSelectedItem()==null) {
			textNewGradeReqField.setTextFill(Color.RED);
			textNewGradeReqField.setText("Must choose student to update");
			textNewGradeReqField.setVisible(true);
			return;
		}
		//check if fields are empty
		if (changeReason.isEmpty() || newGrade.trim().isEmpty()) {
			textNewGradeReqField.setTextFill(Color.RED);
			textNewGradeReqField.setText("Must add grade and reason to update");
			textNewGradeReqField.setVisible(true);
			return;
		} else {
			if (!isLegalScore(newGrade))
				return;
			
		RequestToServer req = new RequestToServer("Update Grade");
		UpdateScoreRequest upReq=new UpdateScoreRequest();
		upReq.setExamID(textExamID.getText());
		upReq.setReasonOfUpdate(changeReason);
		upReq.setStudentID(selectStudent.getSelectionModel().getSelectedItem());
		upReq.setUpdatedScore(Integer.parseInt(newGrade));
		req.setRequestData(upReq);
		ClientUI.cems.accept(req);
		if(CEMSClient.responseFromServer.getResponseType().equals("FALSE")) {
			popUp("Update Grade has failed.");
			return;
		}
		textNewGradeReqField.setText("Grade Updated Successfuly");
		textNewGradeReqField.setTextFill(Color.GREEN);
		textNewGradeReqField.setVisible(true);
		
	}
	}

	@FXML
	void btnViewStudentExam(ActionEvent event) {

	}

	@FXML
	void selectStudent(ActionEvent event) {
		textCurrentGrade.setText(String.valueOf(stdScore.get(selectStudent.getSelectionModel().getSelectedItem())));
	}

	@SuppressWarnings("unchecked")
	@FXML
	void ShowStudentByExamID(ActionEvent event) {
		textNewGradeReqField.setVisible(false);
		textnewGradeField.setText("");
		textGradeChangeReason.setText("");
		String ExamID = textExamID.getText();

		if (!checkForLegalID(ExamID))
			return;

		RequestToServer req = new RequestToServer("getStudentsByExamID");
		req.setRequestData(ExamID);
		ClientUI.cems.accept(req);
		stdScore = (HashMap<String, Integer>) CEMSClient.responseFromServer.getResponseData();

		selectStudent.setDisable(false);
		selectStudent.setItems(FXCollections.observableArrayList(stdScore.keySet()));

	}

	public boolean checkForLegalID(String ExamID) {

		if (ExamID.length() != 6) {
			popUp("Exam ID Must be 6 digits.");
			return false;
		}
		for (int i = 0; i < ExamID.length(); i++)
			if (!Character.isDigit(ExamID.charAt(i))) {
				popUp("Exam ID Must Contains only digits.");
				return false;
			}
		return true;
	}

	private void popUp(String msg) {
		final Stage dialog = new Stage();
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(msg);
		lbl.setPadding(new Insets(15));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
		dialog.setScene(dialogScene);
		dialog.show();
	}

}
