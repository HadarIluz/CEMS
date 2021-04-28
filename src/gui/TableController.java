package gui;

import java.awt.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.TestRow;
import logic.TestTableRequest;

public class TableController {
	
	private TestRow test;

	@FXML
	private Button btnTest;

	@FXML
	private Button btnTable;

	@FXML
	private Button btnShow;

	@FXML
	private Font x1;

	@FXML
	private Text txtReqFiledMessage;

	@FXML
	private TextField txtExamID;

	@FXML
	private Text txtProfession;

	@FXML
	private Text txtCourse;

	@FXML
	private Text txtTime;

	@FXML
	private Text txtPoints;

	@FXML
	private Font x3;

	// display the "TestForm" after pressing btnTest from Main.
	public void pressUpdateTesFiledtBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		System.out.println("Test Fram Tool"); // message to console.

		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Table) window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/TestForm.fxml").openStream());
		// TestController testController = loader.getController(); //ASK?
		// testController.loadTable(ChatClient.testsTable.getTableData());

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/TestForm.css").toExternalForm());
		primaryStage.setTitle("Test Fram");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// Displays information for a requested test
	public void pressShowBtn(ActionEvent event) throws Exception {
		String examID = txtExamID.getText();

		if (examID.trim().isEmpty()) {
			System.out.println("You must enter an  exam id number"); // message to console.
			txtReqFiledMessage.setText("Exam ID is Req filed");
		} else {
			ClientUI.chat.accept(examID);
			if (ChatClient.testRow.getExamID().equals("Error")) // Check that the test exists
			{
				System.out.println("Exam ID Not Found");
				txtReqFiledMessage.setText("Exam ID Not Found");
			} else {
				test = ChatClient.testRow;
				System.out.println("Exam ID Found"); // message to console.
				txtProfession.setText(test.getProfession());
				txtCourse.setText(test.getCourse());
				txtTime.setText(test.getTimeAllotedForTest());
				txtPoints.setText(test.getPointsPerQuestion());
			}
		}
	}

}
