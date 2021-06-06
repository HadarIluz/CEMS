package gui_cems;

import java.io.IOException;

import entity.Student;
import entity.Teacher;
import entity.User;
import gui_principal.PrincipalController;
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

public class GuiCommon {

	public final String principalStatusScreen = "PRINCIPAL";
	public final String teacherStatusScreen = "TEACHER";

	public void popUp(String msg) {
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

	//

}
