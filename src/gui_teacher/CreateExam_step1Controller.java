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
import entity.ExamStatus;
import entity.Profession;
import entity.Question;
import entity.Teacher;
import gui_cems.GuiCommon;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class CreateExam_step1Controller extends GuiCommon implements Initializable{

    @FXML
    private TextArea textLecturers_Instructions;
    
    @FXML
    private TextArea textStudent_Instructions;

    @FXML
    private Button btnNext;

    @FXML
    private ComboBox<String> selectProffessionList;

    @FXML
    private ComboBox<String> selectCourseList;

    @FXML
    private TextField textExamDuration;

    @FXML
    private ImageView imgStep1;

    @FXML
    private Text textAuthor;

    @FXML
    private ImageView imgComputer;
    
    @FXML
    private Label noQbankError;
    
    @FXML
    private RadioButton btnComputerized;

    @FXML
    private RadioButton btnManual;

  
    
    private HashMap<String, Profession> professionsMap = null;
    private Profession selectedProfession;
    private ArrayList<Course> courseList;
    private Course selectedCourse = null;
    private HashMap<String, Course> courseMap = null;
    private static Exam newExam = null;

    @FXML
    void btnComputerizedPress(ActionEvent event) {
    	btnComputerized.setSelected(true);
    	btnManual.setSelected(false);
    }

    @FXML
    void btnManualPress(ActionEvent event) {
    	btnComputerized.setSelected(false);
    	btnManual.setSelected(true);
    }
    
    @FXML
    void btnNext(ActionEvent event) {
    	// check all fields:
    	if (selectedCourse == null) {
    		popUp("You must select a course");
    	}
    	else if (textExamDuration.getText().trim().length() == 0) {
    		popUp("You must enter exam duration");
    	}
    	else if(!btnComputerized.isSelected() & !btnManual.isSelected()) {
    		popUp("You must choose the type of exam you are creating");

    	}
    	else {
    		int time = Integer.parseInt(textExamDuration.getText().trim());
    		if (time <= 20) {
        		popUp("Exam time too short");
    		}
    		else {
    			if (newExam == null) {
        			newExam = new Exam(selectedProfession, selectedCourse, time);

    			}
    			if (textLecturers_Instructions.getText().trim().length() > 0) {
    				newExam.setCommentForTeacher(textLecturers_Instructions.getText().trim());
    			}
    			if (textStudent_Instructions.getText().trim().length() > 0) {
    				newExam.setCommentForStudents(textStudent_Instructions.getText().trim());
    			}
    			newExam.setAuthor((Teacher)ClientUI.loggedInUser.getUser());
    			if(btnComputerized.isSelected()) 
    				newExam.setActiveExamType("computerized");
    			
    			else 
    				newExam.setActiveExamType("manual");
    			newExam.setExamStatus(ExamStatus.inActive);
    			startNextScreen(newExam);

    		}
    	}
    }

    private void startNextScreen(Exam newExam) {
    	try {
    		if(newExam.getActiveExamType().equals("computerized")) {
			CreateExam_addQ_step2Controller.setExamState(newExam);
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_addQ_step2.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			TeacherController.root.add(newPaneRight, 1, 0);
    		}else {
    			UploadManualExam.setNewExam(newExam);
    			Pane newPaneRight = FXMLLoader.load(getClass().getResource("UploadManualExam.fxml"));
    			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    			TeacherController.root.add(newPaneRight, 1, 0);
    		}

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

		
	}

	@FXML
    void selectCourseList(ActionEvent event) {
		if (courseMap.containsKey(selectCourseList.getValue())) {
			selectedCourse = courseMap.get(selectCourseList.getValue());
		}
    }

    @FXML
    void selectProffessionList(ActionEvent event) {
		noQbankError.setVisible(false);

    	if (professionsMap.containsKey(selectProffessionList.getValue())) {
    		selectedProfession = professionsMap.get(selectProffessionList.getValue());
    		
    		RequestToServer req = new RequestToServer("getQuestionBank");
    		Question q = new Question();
    		q.setTeacher((Teacher)ClientUI.loggedInUser.getUser());
    		q.setProfession(selectedProfession);
    		req.setRequestData(q);
    		ClientUI.cems.accept(req);
    		
    		if (CEMSClient.responseFromServer.getResponseType().equals("No Question Bank")) {
    			noQbankError.setText("No question bank was found for this profession");
    			noQbankError.setVisible(true);
    		}
    		else {
    			CreateExam_addQ_step2Controller.loadAvailableQuestions((ArrayList<Question>) CEMSClient.responseFromServer.getResponseData());
    			req = new RequestToServer("getCoursesByProfession");
    			req.setRequestData(selectedProfession);
        		ClientUI.cems.accept(req);
        		
        		if (CEMSClient.responseFromServer.getResponseType().equals("No Courses")) {
        			noQbankError.setText("There are no courses available for this profession");
        			noQbankError.setVisible(true);
        		}
        		else {
        			courseList = (ArrayList<Course>)CEMSClient.responseFromServer.getResponseData();
        			loadCourseListIntoComboBox();
        		}
    		}
    	}
    }

	private void loadCourseListIntoComboBox() {
		courseMap = new HashMap<>();
		for (Course c: courseList) {
			courseMap.put(c.getCourseName(), c);
		}
		selectCourseList.setItems(FXCollections.observableArrayList(courseMap.keySet()));
		selectCourseList.setDisable(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textAuthor.setText(ClientUI.loggedInUser.getUser().getFirstName() + " " + ClientUI.loggedInUser.getUser().getLastName());
		noQbankError.setVisible(false);
		
		professionsMap = TeacherController.getProfessionsMap();
		loadProfessionsToCombobox();
		
		if (newExam != null) {
			selectedProfession = newExam.getProfession();
			selectProffessionList.getSelectionModel().select(selectedProfession.getProfessionName());
			selectedCourse = newExam.getCourse();
			selectCourseList.getSelectionModel().select(selectedCourse.getCourseName());
			selectCourseList.setDisable(false);
			textExamDuration.setText(String.valueOf(newExam.getTimeOfExam()));
			if (newExam.getCommentForTeacher() != null) {
				textLecturers_Instructions.setText(String.valueOf(newExam.getCommentForTeacher()));
			}
			
			if (newExam.getCommentForStudents() != null) {
				textStudent_Instructions.setText(String.valueOf(newExam.getCommentForStudents()));
			}
			
		}


	}
	
	private void loadProfessionsToCombobox() {
		selectProffessionList.setItems(FXCollections.observableArrayList(professionsMap.keySet()));
    
    }
	

	public static void setExamState(Exam newExam2) {
		newExam = newExam2;
	}

}
