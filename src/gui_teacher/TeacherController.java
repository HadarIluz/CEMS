package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Profession;
import entity.Teacher;
import entity.User;
import gui_cems.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//import logic.Student;

public class TeacherController extends Application implements Initializable { 

	@FXML
	private ImageView imgPrincipal;

	@FXML
	private ImageView imgLogo;

	@FXML
	public Label textTeacherName;

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
    private static HashMap<String, Profession> professionsMap = null;

	
	 
	 
	 
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
		
		
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		textTeacherName.setText(ClientUI.loggedInUser.getUser().getFirstName());
		setProfessionMap(((Teacher)ClientUI.loggedInUser.getUser()).getProfessions());
		 Image flag = new Image("file:src/images/teacher_userImg.png");
		 
		// private Image flag = new Image("file:src/resources/flag.png");

		 imgPrincipal= new ImageView(flag);
	}
	
	public static void setProfessionMap(ArrayList<Profession> professionsList) {
		professionsMap = new HashMap<>();
		for (Profession p: professionsList) {
			professionsMap.put(p.getProfessionName(), p);
		}
	}
	
	public static HashMap<String, Profession> getProfessionsMap() {
		return professionsMap;
	}

	
}
