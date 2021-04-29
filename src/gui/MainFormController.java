package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainFormController {

	@FXML
	private Button pressBtnUpdateTesFiledtForm;

	@FXML
	private Button pressBtnTableForm;

	@FXML
	private Font x1;

	@FXML
	private Font x3;

	// Display MainPrototypeForm after connection
	public void start(Stage primaryStage) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainPrototypeForm.fxml"));

			Scene scene = new Scene(root);
			// message to primaryStage
			primaryStage.setTitle("Main Prototype Fram Tool");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// display the "TestForm" after pressing btnTest from Main.
	@FXML
	public void pressBtnUpdateTesFiledtForm(ActionEvent event) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			System.out.println("Test Fram Tool"); // message to console.

			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Main) window
			Stage primaryStage = new Stage();
			System.out.println("BEFORE ");
			Pane root = loader.load(getClass().getResource("TestForm.fxml").openStream());

			Scene scene = new Scene(root);
			primaryStage.setTitle("Test Fram");

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// display the "TableForm" after pressing btnTable from Main.
	@FXML
	public void pressBtnTableForm(ActionEvent event) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			System.out.println("Table Fram Tool"); // message to console.

			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Main) window
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("TableForm.fxml").openStream());

			Scene scene = new Scene(root);
			primaryStage.setTitle("Table Fram");

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
