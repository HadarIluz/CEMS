package gui;

import java.io.IOException;

import client.ChatClient;
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
import logic.Student;

public class MainFormController {

	private TestController test_Fc;
	private static int itemIndex = 3; // ASK?
	@FXML
	private Button btnTest = null;

	@FXML
	private Button btnTable = null;

	@FXML
	private Font x1;

	@FXML
	private Font x3;
	
	//Display MainPrototypeForm after connection
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/MainPrototypeForm.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/MainPrototypeForm.css").toExternalForm());
		//message to primaryStage
		primaryStage.setTitle("Main Prototype Fram Tool");
		primaryStage.setScene(scene);

		primaryStage.show();
	}
	
	//display the "TestForm" after pressing btnTest from Main.
	public void btnTest(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		System.out.println("Test Fram Tool"); //message to console.

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary(Main) window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/TestForm.fxml").openStream());
		TestController testController = loader.getController();	//ASK?	
		//testController.loadStudent(ChatClient.s1);
	
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/TestForm.css").toExternalForm());
		primaryStage.setTitle("Test Fram");

		primaryStage.setScene(scene);		
		primaryStage.show();
		
	}

}
