package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Course;
import entity.Exam;
import entity.Profession;
import entity.Question;
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
    	Qlist= tableExam.getSelectionModel().getSelectedItems();
    	textExamID.setText(Qlist.get(0).getExamID());

    }


	@FXML
	void btnDeleteExam(ActionEvent event) {
		ObservableList<Exam> Qlist;
		
    	Qlist= tableExam.getSelectionModel().getSelectedItems();
    	
    	data.removeAll(Qlist);


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
 
		tableExam.setEditable(true);
		/*
		 * step 1 - ask for all teacher's test step 2- covert all for the type:
		 * string,string,integer step 3- show on the screnn
		 * 
		 */
		/*
		 * RequestToServer req = new RequestToServer("GetTeacherExams");
		 * req.setRequestData(teacher); ClientUI.cems.accept(req);
		 */
		

		 data = FXCollections.observableArrayList(new Exam("010203", "Algebra", 10), 
				 								  new Exam("111111", "Masadim", 2));

		tableExam.getColumns().clear();

		ExamID.setCellValueFactory(new PropertyValueFactory<>("examID"));

		Proffesion.setCellValueFactory(new PropertyValueFactory<>("ProfessionName"));

		Time.setCellValueFactory(new PropertyValueFactory<>("timeOfExam"));

		tableExam.setItems(data);
 
		tableExam.getColumns().addAll(ExamID, Proffesion, Time);

	}
}
