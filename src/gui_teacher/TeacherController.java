package gui_teacher;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import entity.Profession;
import entity.Teacher;
import entity.User;
import entity.UserType;
import gui_cems.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//import logic.Student;

public class TeacherController extends Application  { 

	@FXML
	private ImageView imgPrincipal;

	@FXML
	private ImageView imgLogo;

	@FXML
	private Label textTeacherName;

	@FXML
	private Button brnManageQuestionsBank;

	@FXML
	private ImageView imgPhone;

	@FXML
	private ImageView imgEmail;

	@FXML
	private Button btnCreateActiveExam;

	@FXML
	private Button btnManageExamsBank;

	@FXML
	private Button btnGetStatistics;

	@FXML
	private Button btnScoreApproval;

	@FXML
	private Button btnChangeExamTime;

	 protected static GridPane root;
	 Scene scene;

	protected User teacher;
	
	LoginController login;
	
	
	 
	 
	 
	@FXML
	void brnManageQuestionsBank(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("QuestionBank.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
		
	}

	@FXML
	void btnChangeExamTime(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("AddTimeToExam.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
		

	}

	@FXML
	void btnCreateActiveExam(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateActiveExam.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
		

	}

	@FXML
	void btnGetStatistics(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("DemoStatistics.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
		
		

	}

	@FXML
	void btnManageExamsBank(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("ExamBank.fxml"));
			root.add(newPaneRight, 1, 0);
		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
		
		
		
		
		

	}

	@FXML
	void btnScoreApproval(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("ScoreApproval.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	@FXML
	void pressLogout(MouseEvent event)
	{

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		root = new GridPane();
		 scene = new Scene(root, 980, 580); // Login
		Pane newMnueLeft = FXMLLoader.load(getClass().getResource("TeacherMenuLeft.fxml"));
		root.add(newMnueLeft, 0, 0);
		primaryStage.setTitle("CEMS-Computerized Exam Management System");
		
		//That the way to get the user details-(ClientUI.loggedInUser.getUser().getFirstName());
//		textTeacherName.setText(ClientUI.loggedInUser.getUser().getFirstName());
		
		primaryStage.setScene(scene);
		primaryStage.show();

		
		

	}

	/*
	 * public static void main(String[] args) { launch(args); }
	 */

}
