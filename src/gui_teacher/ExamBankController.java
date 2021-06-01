package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Course;
import entity.Exam;
import entity.Profession;
import entity.Question;
import entity.QuestionRow;
import entity.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.RequestToServer;

public class ExamBankController extends TeacherController implements Initializable {

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
	void MouseC(MouseEvent event) {

		ObservableList<Exam> Qlist;
		Qlist = tableExam.getSelectionModel().getSelectedItems();
		textExamID.setText(Qlist.get(0).getExamID());

	}

	
	public Exam GetTableDetails(String ExamID) {

		Exam exam;

		for (Exam e : data) {
			if (e.getExamID().equals(ExamID)) {
				exam = new Exam(ExamID);
				exam.setCourse(new Course(e.getCourse().getCourseName()));
				exam.getCourse().setCourseID(e.getCourse().getCourseID());
				exam.setProfessionName(e.getProfessionName());
				return exam;

			}

		}

		return null;

	}

	@FXML
	void btnDeleteExam(ActionEvent event) {
		// we need to insert case of letters of not 5 digits

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

		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("EditExam.fxml"));
			root.add(newPaneRight, 1, 0);

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
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTableRows();

	}

	@SuppressWarnings("unchecked")
	public void initTableRows() {
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

}
