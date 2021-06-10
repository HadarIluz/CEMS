package gui_principal;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import client.CEMSClient;
import client.ClientUI;
import entity.Exam;
import gui_cems.GuiCommon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import logic.RequestToServer;

public class PrincipalDisplayReporByController extends GuiCommon implements Initializable {

	@FXML
	private Label lblMedian;

	@FXML
	private Label lblGradesAverage;

	@FXML
	private Label lblCourse;

	@FXML
	private BarChart<String, Number> CourseHisto;

	@FXML
	private CategoryAxis ca;

	@FXML
	private NumberAxis na;

	@FXML
	private Label lblProf;

	private static PrincipalController principalController;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String CID="0"+String.valueOf(PrincipalGetReportsController.pId);
		String PID="0"+String.valueOf(PrincipalGetReportsController.Id);
		lblCourse.setText(PrincipalGetReportsController.Cname);
		lblProf.setText(PrincipalGetReportsController.Pname);
		RequestToServer req2 = new RequestToServer("getAllStudentsExams");
		ClientUI.cems.accept(req2);
		HashMap<String, ArrayList<Integer>> examsOfStudents = (HashMap<String, ArrayList<Integer>>) CEMSClient.responseFromServer
				.getResponseData();
		if (examsOfStudents.isEmpty()) {
			GuiCommon.popUp("This Course Does not have any solved exams.");
			return;
		}
		String ProfCourseID = CID+PID;
		examsOfStudents.keySet();
		examsOfStudents.keySet();

		XYChart.Series avg = new XYChart.Series();
		XYChart.Series median = new XYChart.Series();
		avg.setName("Average");
		median.setName("Median");
		Iterator<String> iter=examsOfStudents.keySet().iterator();
	
		while (iter.hasNext()) {
			String curr=iter.next();
			if (ProfCourseID.equals(curr.substring(0, 4))) {
				avg.getData().add(new XYChart.Data(curr.substring(4, 6), calcAvg(examsOfStudents.get(curr))));
				median.getData().add(new XYChart.Data(curr.substring(4, 6), calcMedian((examsOfStudents.get(curr)))));
			}
		}
		if(avg.getData().isEmpty())
			popUp("This Course Doesn't Have Any Solved Exam.");
		CourseHisto.getData().addAll(avg, median);
		

	}

	private float calcMedian(ArrayList<Integer> arr) {
		Collections.sort(arr);
		if (arr.size() % 2 == 0) {
			int first, second;
			first = arr.size() / 2 - 1;
			second = (arr.size() + 2) / 2 - 1;
			return ((float) arr.get(first) + arr.get(second)) / 2;
		} else
			return (float) arr.get((arr.size() + 1) / 2 - 1);
	}

	private float calcAvg(ArrayList<Integer> arr) {
		float sum = 0;
		for (Integer a : arr)
			sum += a;
		sum /= arr.size();
		return sum;
	}
}
