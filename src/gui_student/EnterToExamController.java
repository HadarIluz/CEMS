package gui_student;

import java.util.Calendar;import client.CEMSClient;
import client.ClientUI;
import entity.ActiveExam;
import entity.Exam;
import entity.ExamOfStudent;
import entity.Student;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.RequestToServer;

/**
 * @author iluzh
 *
 */
public class EnterToExamController {

	@FXML
	private Button btnStart;

	@FXML
	private TextField textExamCode;

	@FXML
	private TextField textStudentID;

	@FXML
	private CheckBox ApprovalInsrtuctions;

	@FXML
	private CheckBox CommitPreformByMyself;
	
	
	private Student student;
	private Exam exam;

	@FXML
	void btnStart(ActionEvent event) {
		String examCode = textExamCode.getText().trim();
		String studentID = textStudentID.getText().trim();

		boolean condition = checkConditionToStart(examCode, studentID);
		
		if(condition) {
			
			Calendar date = Calendar.getInstance();
			System.out.println("The current date is : " + date.getTime());
			date.add(Calendar.DATE, 0);
			
			int id = Integer.parseInt(studentID.trim());
			student.setId(id);
			//exam.getExamID();
			
			
			
			
			//-------I DONT KNOE WHICH OBJECT I NEED TO SEND TO SERVER.
			//-------ALL THE PK IN THE TABLES MIXED IN THE CODE!! ):
			
			ActiveExam activeExam= new ActiveExam(date, exam, examCode);
			
			/*
			//try1:
			Exam exam=new Exam(date);
			
			ActiveExam activeExam= new ActiveExam(date, exam, examCode);
			*/
			//---------------------------
			
			
			/*			
			//Exam exam= new Exam(exam);
			
			ActiveExam startActiveExam = new ActiveExam(date, examCode);
			
			Student student= new Student(id); //new constructor added to student.
			*/
			//ExamOfStudent examOfStudent= new ExamOfStudent(startActiveExam, student); //new constructor added to ExamOfStudent.
			

			RequestToServer req = new RequestToServer("getExamID_perStudentID");
			//req.setRequestData();
			ClientUI.cems.accept(req);
			
			
			
			//ActiveExam ectiveExam=new ActiveExam();
//----------------------------------------------------------------
			
			RequestToServer req2 = new RequestToServer("startActiveExam");
			//req.setRequestData();
			ClientUI.cems.accept(req2); 
			
			
			
			if (CEMSClient.statusMsg.getStatus().equals("SUCCESS")) {
				// The student has entered all the given details and transferred to exam screen
				// - computerized or manual
			}
		
		}
	}

	

	/** Checks whether the student has filled all the required fields, if not open popUp message.
	 * @param examCode
	 * @param studentID
	 * @return Returns true if all fields are filled otherwise returns false.
	 */
	private boolean checkConditionToStart(String examCode, String studentID) {
		boolean approve1 = ApprovalInsrtuctions.isSelected();
		boolean approve2 = CommitPreformByMyself.isSelected();

		if (examCode.length() == 0 || studentID.length() == 0 || examCode.length() != 4) {
			popUp("One or more of the parameters which insert are incorrect. Please try again.");
			return false;
		} else if (!approve1 && !approve2) {
			popUp("You must confirm all terms in orderto start the exam!");
			return false;
		}
		return true;
	}

	/**create a popUp with a given message.
	 * @param msg
	 */
	private void popUp(String msg) {
		final Stage dialog = new Stage();
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(msg);
		lbl.setPadding(new Insets(5));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), 15);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	
	// Loading this fxml window while loading this controller from another location
	// Not sure it's need to be like that !!!
	
	/**
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("/gui_student/EnterToExam.fxml"));
			GridPane root = null;
			root.add(newPaneRight, 1, 0);
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
		}

	}

}
