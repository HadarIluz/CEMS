package gui;

import client.ChatClient;
import client.ClientUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.TestRow;
import javafx.scene.Node;

public class TableController {

    @FXML
    private Button pressBtnUpdateTesFiledtForm;

    @FXML
    private Button btnTable;

    @FXML
    private Font x1;

    @FXML
    private Label lblExamID;

    @FXML
    private TextField txtExamID;

    @FXML
    private Button pressShowBtn;

    @FXML
    private Label lblProfession;

    @FXML
    private Label lblCourse;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblPoints;

    @FXML
    private Text txtProfession;

    @FXML
    private Text txtCourse;

    @FXML
    private Text txtTime;

    @FXML
    private Text txtPoints;

    @FXML
    private Text txtReqFiledMessage;

    @FXML
    private Font x3;


	@FXML
	// display the "TestForm" after pressing btnTest from Main.
	public void pressBtnUpdateTesFiledtForm(MouseEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();

		System.out.println("Test Fram Tool"); // message to console.

		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Table) window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/TestForm.fxml").openStream());

		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/TestForm.css").toExternalForm());
		primaryStage.setTitle("Test Fram");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@FXML
	// Displays information for a requested test
	public void pressShowBtn(MouseEvent event) throws Exception {
		String examID = txtExamID.getText();
		TestRow test;

		if (examID.trim().isEmpty()) {
			System.out.println("You must enter an  exam id number"); // message to console.
			txtReqFiledMessage.setText("Exam ID is Req filed");
		} else {
			ClientUI.chat.accept("getRow " + examID);
			if (ChatClient.testRow.getExamID().equals("ERROR")) // Check that the test exists
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
