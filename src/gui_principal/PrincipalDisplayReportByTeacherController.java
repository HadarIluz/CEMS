package gui_principal;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Course;
import entity.Exam;
import entity.Profession;
import gui_cems.GuiCommon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import logic.RequestToServer;

public class PrincipalDisplayReportByTeacherController extends GuiCommon implements Initializable {

	@FXML
	private BarChart<?, ?> ExamsHisto;

	@FXML
	private CategoryAxis ca;

	@FXML
	private NumberAxis na;

	@FXML
	private Label TeacherIDLabel;

	@FXML
	private Label TeacherNameLabel;

	ArrayList<Integer> gradesOfExam;
	HashMap<String, String> idNamecourses = new HashMap<String, String>();
	// <id,name>

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TeacherIDLabel.setText(String.valueOf(PrincipalGetReportsController.Id));
		TeacherNameLabel.setText(PrincipalGetReportsController.UserName);
		RequestToServer req = new RequestToServer("getExams");
		req.setRequestData(PrincipalGetReportsController.Id);
		ClientUI.cems.accept(req);
		ArrayList<Exam> examsOfTeacher = (ArrayList<Exam>) CEMSClient.responseFromServer.getResponseData();
		if (examsOfTeacher.isEmpty()) {
			GuiCommon.popUp("Current Teacher Doesn't have any Exams.");
			return;
		}
		XYChart.Series avg = new XYChart.Series();
		XYChart.Series median = new XYChart.Series();
		avg.setName("Average");
		median.setName("Median");

		for (Exam curr : examsOfTeacher) {
			getCoursesNames(curr.getProfession().getProfessionID());
			RequestToServer req2 = new RequestToServer("gradesAverageCalc");
			req2.setRequestData(curr.getExamID());
			ClientUI.cems.accept(req2);
			gradesOfExam = (ArrayList<Integer>) CEMSClient.responseFromServer.getResponseData();
			if (gradesOfExam.isEmpty()) {//if we want to change that we see only the exam that student did, just erase the 2 lines in this if
				avg.getData().add(new XYChart.Data(idNamecourses.get(curr.getCourse().getCourseID())+"-"+curr.getExamID().charAt(4)+curr.getExamID().charAt(5), 0));
				median.getData().add(new XYChart.Data(idNamecourses.get(curr.getCourse().getCourseID())+"-"+curr.getExamID().charAt(4)+curr.getExamID().charAt(5), 0));
			} else {
				avg.getData().add(new XYChart.Data(idNamecourses.get(curr.getCourse().getCourseID())+"-"+curr.getExamID().charAt(4)+curr.getExamID().charAt(5),
						calcAvg()));
				// way to show only 2 digits after the decimal point
				median.getData().add(new XYChart.Data(idNamecourses.get(curr.getCourse().getCourseID())+"-"+curr.getExamID().charAt(4)+curr.getExamID().charAt(5),
						calcMedian()));
			} // new DecimalFormat("##.##").format(calcMedian()))
		}
		ExamsHisto.getData().addAll(avg, median);

	}

	private float calcAvg() {
		float sum = 0;
		for (Integer a : gradesOfExam)
			sum += a;
		sum /= gradesOfExam.size();
		return sum;
	}

	private float calcMedian() {
		Collections.sort(gradesOfExam);
		if (gradesOfExam.size() % 2 == 0) {
			int first, second;
			first = gradesOfExam.size() / 2 - 1;
			second = (gradesOfExam.size() + 2) / 2 - 1;
			return ((float) gradesOfExam.get(first) + gradesOfExam.get(second)) / 2;
		} else
			return (float) gradesOfExam.get((gradesOfExam.size() + 1) / 2 - 1);

	}

	public void getCoursesNames(String id) {
		Profession prof = new Profession(id);
		RequestToServer req = new RequestToServer("getCoursesByProfession");
		req.setRequestData(prof);
		ClientUI.cems.accept(req);
		ArrayList<Course> courses = (ArrayList<Course>) CEMSClient.responseFromServer.getResponseData();
		for (Course curr : courses)
			idNamecourses.put(curr.getCourseID(), curr.getCourseName());
	}
}
