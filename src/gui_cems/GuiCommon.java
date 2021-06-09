
package gui_cems;

import java.io.IOException;

import Server.CEMSserver;
import client.CEMSClient;
import client.ClientUI;
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
 * This class contains functions common to different classes that inherit from
 * it. With the help of this department the reuse mechanism is implemented.
 * 
 * @author Hadar Iluz
 *
 */
public class GuiCommon {

	public final String principalStatusScreen = "PRINCIPAL";
	public final String teacherStatusScreen = "TEACHER";

	/**
	 * create a popUp with a given message.
	 * 
	 * @param msg
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
	 * @param str
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

	public static void handleNotifications(ResponseFromServer res) {
		if (res.getResponseType().startsWith("NOTIFICATION_STUDENT"))
			handleStudentNotifications(res);
		else if (res.getResponseType().startsWith("NOTIFICATION_PRINCIPAL"))
			handlePrincipalNotifications(res);
		else if (res.getResponseType().startsWith("NOTIFICATION_TEACHER"))
			handleTeacherNotifications(res);
	}

	private static void handleStudentNotifications(ResponseFromServer res) {
		if (res.getResponseType().equals("NOTIFICATION_STUDENT_EXAM_LOCKED")) {
			System.out.println("notification exam locked");
			StartManualExamController.setTimeForExam(0);
		}
		
	}

	private static void handlePrincipalNotifications(ResponseFromServer res) {
		// if the response is notificatoin for principal -> check that user is student
		// and do what you need
	}

	private static void handleTeacherNotifications(ResponseFromServer res) {
		// if the response is notificatoin for student -> check that user is student and
		// do what you need
	}

}
