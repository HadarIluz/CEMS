package gui_teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Course;
import entity.Exam;
import entity.Teacher;
import entity.User;
import entity.Exam.Status;
import gui_cems.GuiCommon;
import gui_student.StartManualExamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import logic.RequestToServer;

public class ExamBankController extends GuiCommon implements Initializable {

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

	@FXML
	private TableColumn<Exam, Status> StatusC; //matar

	@FXML
	private Button btnExamInfoPrincipal;

	@FXML
	private Button btnCreateActiveExam;

	@FXML
	private Button btnLockExam; //matar

	@FXML
	private Text textMsg1;

	@FXML
	private Text textMsg2;

	private ObservableList<Exam> data;
	private static Teacher teacher;
	private static User principal;
	private boolean displayCourseColumn = false;

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
		// we need to insert case of letters of not 5 digits //TODO: ??

		// FIXME: when delete exam it is not delete from table
		if (!textExamID.getText().isEmpty()) {
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
			textExamID.clear();
		}

	}

	@FXML
	void btnEditExam(ActionEvent event) {
		if (!textExamID.getText().isEmpty()) {
			Exam selectedExam = getExistExamDetails(textExamID.getText());
			EditExamController.setActiveExamState(selectedExam, super.teacherStatusScreen);
			displayNextScreen(teacher, "EditExam.fxml");
		}
	}

	@FXML
	void CreateNewExam(ActionEvent event) {
		textExamID.clear();
		displayNextScreen(teacher, "CreateExam_step1.fxml");
//		try {
//			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_step1.fxml"));
//			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//			teacherController.root.add(newPaneRight, 1, 0);
//
//		} catch (IOException e) {
//			System.out.println("Couldn't load!");
//			e.printStackTrace();
//		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableExam.setEditable(true);
		if (ClientUI.loggedInUser.getUser() instanceof Teacher) {
			teacher = (Teacher) ClientUI.loggedInUser.getUser();
			initTableRows();
		}

		else if (ClientUI.loggedInUser.getUser() instanceof User) {
			// setUp before load screen.
			principal = (User) ClientUI.loggedInUser.getUser();
			btnCreateNewExam.setDisable(false);
			btnCreateNewExam.setVisible(false);
			btnEditExam.setDisable(true);
			btnEditExam.setVisible(false);
			btnDeleteExam.setDisable(true);
			btnDeleteExam.setVisible(false);
			btnCreateActiveExam.setDisable(true);
			btnCreateActiveExam.setVisible(false);
			textMsg1.setVisible(false);
			textMsg2.setVisible(false);
			textNavigation.setVisible(true);
			btnExamInfoPrincipal.setVisible(true);
			displayCourseColumn = true;
			fillTableForPrincipalALLexamsInSystem(); // set all exams in cems system into the table
		}
	}

	@FXML
	void btnExamInfoPrincipal(ActionEvent event) {
		if (!textExamID.getText().isEmpty()) {
			Exam selectedExam = getExistExamDetails(textExamID.getText());
			EditExamController.setActiveExamState(selectedExam, super.principalStatusScreen);
			displayNextScreen(principal, "/gui_teacher/EditExam.fxml");
		}

	}

	/**
	 * The function get from server all exams of the logged teacher and insert into
	 * the table.
	 */
	@SuppressWarnings("unchecked")
	private void initTableRows() {
		textExamID.setEditable(true);
		RequestToServer req = new RequestToServer("getExams");
		req.setRequestData(teacher.getId());
		ArrayList<Exam> ExamsOfTeacher = new ArrayList<Exam>();
		ClientUI.cems.accept(req);
		ExamsOfTeacher = (ArrayList<Exam>) CEMSClient.responseFromServer.getResponseData();
		data = FXCollections.observableArrayList(ExamsOfTeacher);
		tableExam.getColumns().clear();
		ExamID.setCellValueFactory(new PropertyValueFactory<>("examID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("ProfessionName"));
		Time.setCellValueFactory(new PropertyValueFactory<>("timeOfExam"));
		StatusC.setCellValueFactory(new PropertyValueFactory<>("status")); //matar
		tableExam.setItems(data);
		tableExam.getColumns().addAll(ExamID, Proffesion, Time,StatusC);

	}

	@FXML
	void btnCreateActiveExam(ActionEvent event) {

		if (!textExamID.getText().isEmpty()) {
			Exam selectedExam = getExistExamDetails(textExamID.getText());
			CreateActiveExamController.setActiveExamState(selectedExam);
			displayNextScreen(teacher, "CreateActiveExam.fxml");
		}
	}

	private Exam getExistExamDetails(String examID) {

		Exam selectedExam = new Exam(examID);
		RequestToServer req = new RequestToServer("getSelectedExamData_byID");
		req.setRequestData(selectedExam);
		ClientUI.cems.accept(req);
		//if(selectedExam.getStatus() == Status.active) //matar
		//	btnLockExam.setDisable(false); //matar
		//else //matar
		//	btnLockExam.setDisable(true); //matar
		return selectedExam = (Exam) CEMSClient.responseFromServer.getResponseData();
	}

	/**
	 * The function get from server all exams stored in the system and insert into
	 * the table.
	 */
	@SuppressWarnings("unchecked")
	private void fillTableForPrincipalALLexamsInSystem() {
		RequestToServer req = new RequestToServer("getAllExamsStoredInSystem");
		ArrayList<Exam> examsList = new ArrayList<Exam>();
		ClientUI.cems.accept(req);
		examsList = (ArrayList<Exam>) CEMSClient.responseFromServer.getResponseData();
		TableColumn<Exam, String> course = new TableColumn<>("course");
		// PropertyValueFactory<Exam, String> factory = new PropertyValueFactory<>();

		data = FXCollections.observableArrayList(examsList);
		tableExam.getColumns().clear();
		ExamID.setCellValueFactory(new PropertyValueFactory<>("examID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("ProfessionName"));
		Time.setCellValueFactory(new PropertyValueFactory<>("timeOfExam"));
		//StatusC.setCellValueFactory(new PropertyValueFactory<>("status")); //matar
		course.setCellValueFactory(new PropertyValueFactory<>("course"));
		tableExam.setItems(data);
		tableExam.getColumns().addAll(ExamID, Proffesion, Time, course);  
	}

	@FXML
	void btnLockExam(ActionEvent event) {
			//examToLock.setStatus(Status.inActive); //matar
		 Exam exam = new Exam("040101"); // temp
		 exam.setStatus(Status.active); // temp
		 exam.setStatus(Status.inActive);
	     RequestToServer req = new RequestToServer("lockActiveExam");//matar
	     req.setRequestData(exam);
	     StartManualExamController.lockExam(exam);   
	     //req.setRequestData(examToLock);//matar
	}

}
