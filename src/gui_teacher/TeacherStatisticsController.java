package gui_teacher;

import java.util.HashMap;

import client.CEMSClient;
import client.ClientUI;
import entity.ProfessionCourseName;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.RequestToServer;

public class TeacherStatisticsController {

	@FXML
	private TextField textExamID;

	@FXML
	private Label textMedian;

	@FXML
	private Label textCourse;

	@FXML
	private BarChart<?, ?> StudentsHisto;

	@FXML
	private Button btnShowStatistic;

	@FXML
	private Label textErrorMessage;

	@FXML
	private Label textProfession;

	@FXML
	private Label textAverage;

	private TeacherController teacherController; // we will use it for load the next screen ! (using root).

	HashMap<String, String> ProfName = new HashMap<String, String>();
	// profID,profName
	HashMap<String, ProfessionCourseName> ProfCourseName = new HashMap<String, ProfessionCourseName>();
	// profID,CourseID+name

	@FXML
	void btnShowStatistic(MouseEvent event) {
		String ExamID = textExamID.getText();
		String ProfID = "" + ExamID.charAt(0) + ExamID.charAt(1);
		String CourseID = "" + ExamID.charAt(2) + ExamID.charAt(3);
		if (!checkForLegalID(ExamID))
			return;
		if(!chechExamExist(ExamID)) 
			return;
			
		getProffesionsNames();
		getCoursesNames();
		textProfession.setText(ProfName.get(ProfID) + "(" + ProfID + ")");
		textCourse.setText(ProfCourseName.get(ProfID).getCourses().get(CourseID) + "(" + CourseID + ")");

	}

	@SuppressWarnings("unchecked")
	public void getProffesionsNames() {
		RequestToServer req = new RequestToServer("getProfNames");
		ClientUI.cems.accept(req);
		ProfName = (HashMap<String, String>) CEMSClient.responseFromServer.getResponseData();
	}

	@SuppressWarnings("unchecked")
	public void getCoursesNames() {
		RequestToServer req = new RequestToServer("getCoursesNames");
		ClientUI.cems.accept(req);
		ProfCourseName = (HashMap<String, ProfessionCourseName>) CEMSClient.responseFromServer.getResponseData();
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
	
	public boolean chechExamExist(String ExamID) {
		boolean isExsit=true;
		RequestToServer req = new RequestToServer("chechExamExist");
		req.setRequestData(ExamID);
		ClientUI.cems.accept(req);
		if(CEMSClient.responseFromServer.getResponseData().equals("FALSE")) {
			popUp("Exam does not exist.");
			isExsit=false;
		}
		return isExsit;
	
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
