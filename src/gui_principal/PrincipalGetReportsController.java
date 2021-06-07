package gui_principal;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Course;
import entity.Profession;
import entity.Student;
import entity.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import logic.RequestToServer;
import logic.ResponseFromServer;

public class PrincipalGetReportsController {

	@FXML
	private Button btnGenarateReport;

	@FXML
	private RadioButton radioBtnTeacher;

	@FXML
	private ComboBox<String> selectTeacher;

	@FXML
	private RadioButton radioBtnStudent;

	@FXML
	private ComboBox<String> selectStudent;

	@FXML
	private RadioButton radioBtnCourse;

	@FXML
	private ComboBox<String> selectCourse;

	@FXML
	private ComboBox<String> selectProfession;

	private static PrincipalController principalController;

	HashMap<String, String> professions;

	@FXML
	void btnGenarateReport(ActionEvent event) {

	}

	@SuppressWarnings("unchecked")
	@FXML
	void reportByCourse(ActionEvent event) {
		selectCourse.setDisable(true);
		selectProfession.setDisable(false);
		selectStudent.setDisable(true);
		selectTeacher.setDisable(true);
		radioBtnStudent.setSelected(false);
		radioBtnTeacher.setSelected(false);

		RequestToServer req = new RequestToServer("getProfNames");
		ClientUI.cems.accept(req);
		professions = (HashMap<String, String>) CEMSClient.responseFromServer.getResponseData();
		selectProfession.setItems(FXCollections.observableArrayList(professions.values()));
	}

	@SuppressWarnings("unchecked")
	@FXML
	void selectProfession(ActionEvent event) {
		String profName = selectProfession.getSelectionModel().getSelectedItem();
		String id = null;
		for (String key : professions.keySet()) {
			if (professions.get(key).equals(profName))
				id = key;
		}
		Profession prof = new Profession(id);
		RequestToServer req = new RequestToServer("getCoursesByProfession");
		req.setRequestData(prof);
		ClientUI.cems.accept(req);
		ArrayList<Course> courses = (ArrayList<Course>) CEMSClient.responseFromServer.getResponseData();
		
		HashMap<String, String> Qlist = new HashMap<String, String>();
		for (Course curr : courses)
			Qlist.put(curr.getCourseID(), curr.getCourseName());
		selectCourse.setDisable(false);
		selectCourse.setItems(FXCollections.observableArrayList(Qlist.values()));

	}

	@FXML
	void reportByStudent(ActionEvent event) {
		selectCourse.setDisable(true);
		selectProfession.setDisable(true);
		selectStudent.setDisable(false);
		selectTeacher.setDisable(true);
		radioBtnCourse.setSelected(false);
		radioBtnTeacher.setSelected(false);
		RequestToServer req = new RequestToServer("getStudents");
		ClientUI.cems.accept(req);
		ArrayList<Student> students = (ArrayList<Student>) CEMSClient.responseFromServer.getResponseData();
		
		HashMap<Integer, String> Qlist = new HashMap<Integer, String>();
		for (Student curr : students)
			Qlist.put(curr.getId(), curr.getLastName() + " " + curr.getFirstName());
		selectStudent.setItems(FXCollections.observableArrayList(Qlist.values()));
	}

	@SuppressWarnings("unchecked")
	@FXML
	void reportByTeacher(ActionEvent event) {
		selectCourse.setDisable(true);
		selectProfession.setDisable(true);
		selectStudent.setDisable(true);
		radioBtnStudent.setSelected(false);
		radioBtnCourse.setSelected(false);
		
		ArrayList<Teacher> teachers;
		RequestToServer req = new RequestToServer("getTeachers");
		ClientUI.cems.accept(req);
		teachers = (ArrayList<Teacher>) CEMSClient.responseFromServer.getResponseData();

		HashMap<Integer, String> Qlist = new HashMap<Integer, String>();

		for (Teacher curr : teachers)
			Qlist.put(curr.getId(), curr.getLastName() + " " + curr.getFirstName());

		selectTeacher.setItems(FXCollections.observableArrayList(Qlist.values()));
	}

	@SuppressWarnings("unchecked")
	@FXML
	void selectCourse(ActionEvent event) {

	}

	@FXML
	void selectStudent(ActionEvent event) {

	}

	@FXML
	void selectTeacher(ActionEvent event) {

	}

}
