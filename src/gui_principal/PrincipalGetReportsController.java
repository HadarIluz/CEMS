package gui_principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import client.CEMSClient;
import client.ClientUI;
import entity.Course;
import entity.Profession;
import entity.Student;
import entity.Teacher;
import entity.User;
import gui_cems.GuiCommon;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import logic.RequestToServer;

public class PrincipalGetReportsController extends GuiCommon {

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
	private static User principal = (User) ClientUI.loggedInUser.getUser();
	HashMap<String, String> professions;
	static ArrayList<Course> courses;
	ArrayList<Teacher> teachers;
	ArrayList<Student> students;

	static String selctedValue,UserName,Pname,Cname;
	static int Id, pId;

	@FXML
	void btnGenarateReport(ActionEvent event) {
		String value;
		if (radioBtnTeacher.isSelected()) {
			value = selectTeacher.getSelectionModel().getSelectedItem();
			UserName=value;
			if(value==null) {
				popUp("Please select a Teacher from the list.");
				return;
			}
			for (Teacher curr : teachers) {
				if (value.equals(curr.getLastName() + " " + curr.getFirstName())) {
					Id = curr.getId();
					displayNextScreen(principal, "ReportByTeacher.fxml");
				}

			}

		} else if (radioBtnStudent.isSelected()) {
			value = selectStudent.getSelectionModel().getSelectedItem();
			UserName=value;
			if(value==null) {
				popUp("Please select a Student from the list.");
				return;
			}
			for (Student curr : students) {
				if (value.equals(curr.getLastName() + " " + curr.getFirstName())) {
					Id = curr.getId();
				displayNextScreen(principal, "ReportByStudent.fxml");
				}

			}

		} else if (radioBtnCourse.isSelected()) {
			value = selectCourse.getSelectionModel().getSelectedItem();
			Cname=value;
			Pname=selectProfession.getSelectionModel().getSelectedItem();
			if(value==null) {
				popUp("Please select a Course from the list.");
				return;
			}
			for (Course curr : courses)
				if (curr.getCourseName().equals(value)) {
					Id = Integer.valueOf(curr.getCourseID());
			displayNextScreen(principal, "PrincipalDisplayReporBy.fxml");
				}

		}
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
		pId = Integer.valueOf(id);
		Profession prof = new Profession(id);
		RequestToServer req = new RequestToServer("getCoursesByProfession");
		req.setRequestData(prof);
		ClientUI.cems.accept(req);
		courses = (ArrayList<Course>) CEMSClient.responseFromServer.getResponseData();

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
		students = (ArrayList<Student>) CEMSClient.responseFromServer.getResponseData();

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
