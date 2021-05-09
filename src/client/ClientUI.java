package client;

import guiControllers.DisplayScreenTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientController cems; // only one instance

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		cems = new ClientController("localhost", 5555);
		// TODO Auto-generated method stub

		 DisplayScreenTask MainForm = new DisplayScreenTask(); // create the TestFrame
		 MainForm.start(primaryStage);
/*
		try {
			GridPane root = new GridPane();
			// Scene scene = new Scene(root, 900, 600); //SignUp
			Scene scene = new Scene(root, 988, 586); // SCREENS

			// LEFT SCREENS:
			Pane newMnueLeft = FXMLLoader.load(getClass().getResource("/boundary/StudentMenuLeft.fxml"));
			// Pane newMnueLeft = FXMLLoader.load(getClass().getResource("TeacherMenuLeft.fxml"));
			// Pane newMnueLeft = FXMLLoader.load(getClass().getResource("PrincipalMenuLeft.fxml"));
			// Pane newMnueLeft = FXMLLoader.load(getClass().getResource("SignUp.fxml"));

			root.add(newMnueLeft, 0, 0);
			// root.getChildren().add(newMnueLeft);
			// --

			// STUDENT:
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("/giuControllers/StartManualExam.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("EnterToExam.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("SolveExam.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("ViewExam.fxml"));

			// TEACHER:
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("TeacherStatistics.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_step1.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("createExam_addQ_step2.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("createNewExam_step3.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("AddTimeToExam"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("ScoreApproval.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("EditExam.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("TeacherStatistics.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateActiveExam.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateQuestion.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("ExamBank.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("QuestionBank.fxml"));

			// PRINCIPAL:
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("PrincipalGetReports.fxml"));
			// Pane newPaneRight = FXMLLoader.load(getClass().getResource("ApprovalTimeExtention.fxml"));

			root.add(newPaneRight, 1, 0);
			// root.getChildren().add(newPaneRight);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
}
