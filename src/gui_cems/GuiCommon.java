
package gui_cems;

import java.io.IOException;

import entity.Student;
import entity.Teacher;
import entity.User;
import gui_principal.PrincipalController;
import gui_student.StartManualExamController;
import gui_student.StudentController;
import gui_teacher.TeacherController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.ResponseFromServer;

/**
 * This class contains functions common to different classes that inherit from it. 
 * With the help of this department the reuse mechanism is implemented.
 * 
 * @author Hadar Iluz
 *
 */
public class GuiCommon {

	public final String principalStatusScreen = "PRINCIPAL";
	public final String teacherStatusScreen = "TEACHER";

	/**
	 * create a popUp with a given message.
	 * @param msg string text input to method to display in popUp message.
	 */
	public static void popUp(String msg) {
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

	/**
	 * this method checks if the given string includes letters.
	 * 
	 * @param str input to method to check if legal
	 * @return true if the String contains only digits.
	 */
	public boolean isOnlyDigits(String str) {
		boolean onlyDigits = true;
		for (char ch : str.toCharArray()) {
			if (!Character.isDigit(ch)) {
				onlyDigits = false;
				System.out.println("The string contains a character that he does not digit");
				break;
			}
		}
		System.out.println("isOnlyDigits returns:" + onlyDigits); // message to console
		return onlyDigits;
	}

	/**
	 * The method loads the desired right screen to which you want to move.
	 * @param userObj  input to identify the user who wants to switch to the screen
	 * @param fxmlName input is the screen Name of the XML file of the screen to
	 *                 which you are moving by loading it
	 */
	public void displayNextScreen(User userObj, String fxmlName) {

		if (userObj instanceof Teacher) {
			try {
				Pane newPaneRight = FXMLLoader.load(getClass().getResource(fxmlName));
				newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				TeacherController.root.add(newPaneRight, 1, 0);
			} catch (IOException e) {
				System.out.println("Couldn't load!");
				e.printStackTrace();
			}
		}

		else if (userObj instanceof Student) {
			try {
				Pane newPaneRight = FXMLLoader.load(getClass().getResource(fxmlName));
				newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				StudentController.root.add(newPaneRight, 1, 0);
			} catch (IOException e) {
				System.out.println("Couldn't load!");
				e.printStackTrace();
			}
		}

		else if (userObj instanceof User) {
			try {
				Pane newPaneRight = FXMLLoader.load(getClass().getResource(fxmlName));
				newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				PrincipalController.root.add(newPaneRight, 1, 0);
			} catch (IOException e) {
				System.out.println("Couldn't load!");
				e.printStackTrace();
			}

		}
	}

	/**
	 * FIXME: ADD JAVADOC
	 * 
	 * @param res
	 */
	public static void handleNotifications(ResponseFromServer res) {
		if (res.getResponseType().equals("NOTIFICATION_STUDENT_EXAM_LOCKED")) {
			System.out.println("notification exam locked");
			StartManualExamController.setFlagToLockExam((Boolean) true);
		}
		if (res.getResponseType().equals("NOTIFICATION_STUDENT_ADDED_TIME")) {
			System.out.println("added time to exam");
			StartManualExamController.addTimeToExam((int) res.getResponseData());
		}
	}

	/**
	 * @param ExamID input to method to check if legal
	 * @return true if legal, else false
	 */
	public static boolean checkForLegalID(String ExamID) {
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

	/**
	 * Method that check if the givenQuestion ID is legal
	 * 
	 * @param QuestionID input to method to check if legal
	 * @return true if legal, else false
	 */
	public static boolean checkForLegalquestionID(String QuestionID) {

		if (QuestionID.length() != 5) {
			popUp("Question ID Must be 5 digits.");
			return false;
		}
		for (int i = 0; i < QuestionID.length(); i++)
			if (!Character.isDigit(QuestionID.charAt(i))) {
				popUp("Question ID Must Contains only digits.");
				return false;
			}
		return true;
	}

}
