package gui_principal;

import java.net.URL;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.RequestToServer;

public class PrincipalGetReportsController implements Initializable {

	@FXML
	private Button btnGenarateReport;

	@FXML
	private RadioButton radioBtnTeacher;

	@FXML
	private ComboBox<?> selectTeacher;

	@FXML
	private RadioButton radioBtnStudent;

	@FXML
	private ComboBox<?> selectStudent;

	@FXML
	private RadioButton radioBtnCourse;

	@FXML
	private ComboBox<?> selectCourse;

	@FXML
	private ComboBox<?> selectProfession;

	private static PrincipalController principalController;

	@FXML
	void btnGenarateReport(ActionEvent event) {

	}

	@FXML
	void reportByCourse(ActionEvent event) {

	}

	@FXML
	void reportByStudent(ActionEvent event) {

	}

	@FXML
	void reportByTeacher(ActionEvent event) {

	}

	@FXML
	void selectCourse(ActionEvent event) {

	}

	@FXML
	void selectProfession(ActionEvent event) {

	}

	@FXML
	void selectStudent(ActionEvent event) {

	}

	@FXML
	void selectTeacher(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initReportsBoxes();

	}

	private void initReportsBoxes() {
		RequestToServer req = new RequestToServer("getPrincipalReports");
		ClientUI.cems.accept(req);

		if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("FALSE")) {
			popUp("DB Problems, Try Again.");
			return;
		}
		
		

	}

	private void checkOnlyOneREportChoose() {

	}

	private void popUp(String msg) {
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

}
