package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Course;
import entity.Exam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class ExamBankController implements Initializable {

	@FXML
	private Button btnEditExam;

	@FXML
	private Button btnDeleteExam;

	@FXML
	private TextField textExamID;

	@FXML
	private TableView<Exam> tableExam;

	@FXML
	private Button btnCreateNewExam;

	@FXML
	private Text textNavigation;

	@FXML
	private TableColumn<Exam, String> ExamID;

	@FXML
	private TableColumn<Exam, String> Proffesion;

	@FXML
	private TableColumn<Exam, Integer> Time;

	private ObservableList<Exam> data;

	@FXML
	private Button btnCreateActiveExam;
	private static TeacherController teacherController;

	@FXML
	void selectExamFromTable(MouseEvent event) {
		ObservableList<Exam> Qlist;
		Qlist = tableExam.getSelectionModel().getSelectedItems();
		textExamID.setText(Qlist.get(0).getExamID());
	}

	private Exam GetTableDetails(String ExamID) {
		Exam exam;
		for (Exam e : data) {
			if (e.getExamID().equals(ExamID)) {
				exam = new Exam(ExamID);
				exam.setCourse(new Course(e.getCourse().getCourseName()));
				exam.setProfession(e.getProfession());
				exam.setProfession(e.getProfession());
				return exam;
			}
		}
		return null;
	}

	private boolean checkForLegalID(String ExamID) {
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

	@FXML
	void btnDeleteExam(ActionEvent event) {
		// we need to insert case of letters of not 5 digits

		if (!checkForLegalID(textExamID.getText()))
			return;

		ObservableList<Exam> Qlist;

		Exam ExamToDelete = GetTableDetails(textExamID.getText());

		Qlist = tableExam.getSelectionModel().getSelectedItems();
		RequestToServer req = new RequestToServer("DeleteExam");
		req.setRequestData(ExamToDelete);
		ClientUI.cems.accept(req);

		if (CEMSClient.responseFromServer.getResponseData().equals("FALSE"))
			System.out.println("failed to delete question");
		else
			data.removeAll(Qlist);
		initTableRows();

	}

	@FXML
	void btnEditExam(ActionEvent event) {

		Exam selectedExam = getExistExamDetails(textExamID.getText());
		try {
			EditExamController.setActiveExamState(selectedExam);
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("EditExam.fxml"));
			teacherController.root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	@FXML
	void CreateNewExam(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_step1.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			teacherController.root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableExam.setEditable(true);
		initTableRows();
	}

	@SuppressWarnings("unchecked")
	private void initTableRows() {
		textExamID.setEditable(true);

		RequestToServer req = new RequestToServer("getExams");
		req.setRequestData(ClientUI.loggedInUser.getUser().getId());
		ArrayList<Exam> ExamsOfTeacher = new ArrayList<Exam>();
		ClientUI.cems.accept(req);
		ExamsOfTeacher = (ArrayList<Exam>) CEMSClient.responseFromServer.getResponseData();
		data = FXCollections.observableArrayList(ExamsOfTeacher);
		tableExam.getColumns().clear();
		ExamID.setCellValueFactory(new PropertyValueFactory<>("examID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("ProfessionName"));
		Time.setCellValueFactory(new PropertyValueFactory<>("timeOfExam"));
		tableExam.setItems(data);
		tableExam.getColumns().addAll(ExamID, Proffesion, Time);

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

	@FXML
	void btnCreateActiveExam(ActionEvent event) {
		Exam selectedExam = getExistExamDetails(textExamID.getText());

		try {
			CreateActiveExamController.setActiveExamState(selectedExam);
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateActiveExam.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			teacherController.root.add(newPaneRight, 1, 0);
		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	private Exam getExistExamDetails(String examID) {
		
		Exam selectedExam = new Exam(examID);
		RequestToServer req = new RequestToServer("getSelectedExamData_byID");
		req.setRequestData(selectedExam);
		ClientUI.cems.accept(req);
		return selectedExam = (Exam) CEMSClient.responseFromServer.getResponseData();
	}

}
