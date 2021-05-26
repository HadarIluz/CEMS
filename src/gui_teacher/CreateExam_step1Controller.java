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
import entity.Teacher;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class CreateExam_step1Controller implements Initializable{

    @FXML
    private TextArea textLecturers_Instructions;
    
    @FXML
    private TextArea textStudent_Instructions;

    @FXML
    private Button btnNext;

    @FXML
    private ComboBox<String> selectProffessionList;

    @FXML
    private ComboBox<Course> selectCourseList;

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
    
    private HashMap<String, Profession> professionsMap = null;
    private Profession selectedProfession;
    private Course courseList;
    private Course selectedCourse = null;


    @FXML
    void btnNext(ActionEvent event) {
    	// check all fields:
    	if (selectedCourse == null) {
    		popUp("You must select a course");
    	}
    	else if (textExamDuration.getText().trim().length() == 0) {
    		popUp("You must enter exam duration");
    	}
    	else {
    		int time = Integer.parseInt(textExamDuration.getText().trim());
    		if (time <= 20) {
        		popUp("Exam time too short");
    		}
    		else {
    			Exam newExam = new Exam(selectedProfession, selectedCourse, time);
    			if (textLecturers_Instructions.getText().trim().length() > 0) {
    				newExam.setCommentForTeacher(textLecturers_Instructions.getText().trim());
    			}
    			if (textStudent_Instructions.getText().trim().length() > 0) {
    				newExam.setCommentForTeacher(textStudent_Instructions.getText().trim());
    			}
    			startNextScreen(newExam);
    		}
    	}
    }

    private void startNextScreen(Exam newExam) {
    	try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_addQ_step2.fxml.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			TeacherController.root.add(newPaneRight, 1, 0);
			CreateExam_addQ_step2Controller.setExamState(newExam);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

		
	}

	@FXML
    void selectCourseList(ActionEvent event) {
    	selectedCourse = selectCourseList.getValue();
    }

    @FXML
    void selectProffessionList(ActionEvent event) {
		noQbankError.setVisible(false);

    	if (professionsMap.containsKey(selectProffessionList.getValue())) {
    		selectedProfession = professionsMap.get(selectProffessionList.getValue());
    		
    		RequestToServer req = new RequestToServer("getQuestionBank");
    		req.setRequestData(selectedProfession);
    		ClientUI.cems.accept(req);
    		
    		if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("No Question Bank")) {
    			noQbankError.setText("No question bank was found for this profession");
    			noQbankError.setVisible(true);
    		}
    		else {
    			CreateExam_addQ_step2Controller.loadAvailableQuestions((ArrayList<Question>) CEMSClient.responseFromServer.getResponseData());
    			req = new RequestToServer("getCoursesByProfession");
    			req.setRequestData(selectedProfession);
        		ClientUI.cems.accept(req);
        		
        		if (CEMSClient.responseFromServer.getStatusMsg().getStatus().equals("No Courses")) {
        			noQbankError.setText("There are no courses available for this profession");
        			noQbankError.setVisible(true);
        		}
        		else {
        			courseList = (Course)CEMSClient.responseFromServer.getResponseData();
        			loadCourseListIntoComboBox();
        		}
    		}
    	}
    }

	private void loadCourseListIntoComboBox() {
		selectCourseList.setItems(FXCollections.observableArrayList(courseList));
		selectCourseList.setDisable(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textAuthor.setText(ClientUI.loggedInUser.getUser().getFirstName() + " " + ClientUI.loggedInUser.getUser().getLastName());
		noQbankError.setVisible(false);
		
		professionsMap = TeacherController.getProfessionsMap();
		loadProfessionsToCombobox();


	}
	
	private void loadProfessionsToCombobox() {
		//selectProffessionList.setItems(FXCollections.observableArrayList(professionsMap.keySet()));
		ArrayList<String> bla = new ArrayList<>();
		bla.add("1");
		bla.add("2");

		selectProffessionList.setItems(FXCollections.observableArrayList(bla));
    
    }
	
	// create a popup with a message
			public void popUp(String txt) {
				final Stage dialog = new Stage();
				VBox dialogVbox = new VBox(20);
				Label lbl = new Label(txt);
				lbl.setPadding(new Insets(5));
				lbl.setAlignment(Pos.CENTER);
				lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				dialogVbox.getChildren().add(lbl);
				Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
				dialog.setScene(dialogScene);
				dialog.show();
			}

}
