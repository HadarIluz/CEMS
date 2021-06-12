package gui_student;

import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import client.CEMSClient;
import client.ClientUI;
import entity.ActiveExam;
import entity.Exam;
import entity.ExamOfStudent;
import entity.QuestionInExam;
import entity.ReasonOfSubmit;
import entity.Student;
import gui_cems.GuiCommon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import logic.RequestToServer;

//FIXME: ADD JAVADOC

public class SolveExamController implements Initializable{

	@FXML
	private Button btnSubmitExam;

	@FXML
	private CheckBox checkBoxShowTime;

	@FXML
    private Button btnNext;

    @FXML
    private Button btnPrev;

	@FXML
	private Label lblTimeLeft;

	@FXML
	private RadioButton btnAnswer1;

	@FXML
	private RadioButton btnAnswer2;

	@FXML
	private RadioButton btnAnswer3;

	@FXML
	private RadioButton btnAnswer4;

	@FXML
	private Label lblQuestionNumber;

	@FXML
	private Label lblPoints;

	@FXML
	private TextArea txtQuestionDescription;

	@FXML
	private Label textQuestion;

	@FXML
	private Text txtnotification;

	@FXML
	private ImageView notificationIcon;

	@FXML
	private Label lblnotificationName;

	@FXML
	private Label lblnotificationMsg;
	
	
    private static StudentController studentController;
    private static ActiveExam newActiveExam;  //check if needed.
    private Timer timer;
    private int currentQuestion;
    private int[] studentAnswers;
    private AtomicInteger timeForTimer;

    
	@FXML
	void btnAnswer1(ActionEvent event) {
		studentAnswers[currentQuestion] = 1;
		
	}

	@FXML
	void btnAnswer2(ActionEvent event) {
		studentAnswers[currentQuestion] = 2;
	}

	@FXML
	void btnAnswer3(ActionEvent event) {
		studentAnswers[currentQuestion] = 3;
	}

	@FXML
	void btnAnswer4(ActionEvent event) {
		studentAnswers[currentQuestion] = 4;
	}

	@FXML
	void btnSubmitExam(ActionEvent event) {
		submitExam(ReasonOfSubmit.initiated);
	}
	
	private void submitExam(ReasonOfSubmit reasonOfSubmit) {
		btnSubmitExam.setDisable(true);
		timer.cancel();
		btnAnswer1.setDisable(true);
		btnAnswer2.setDisable(true);
		btnAnswer3.setDisable(true);
		btnAnswer4.setDisable(true);
		btnNext.setDisable(true);
		btnPrev.setDisable(true);
		
		HashMap<QuestionInExam, Integer> studentQuestions = new HashMap<>();
		for (int i = 0; i<studentAnswers.length; i++) {
			studentQuestions.put(newActiveExam.getExam().getExamQuestionsWithScores().get(i), studentAnswers[i]);
		}

		ExamOfStudent examOfStudent = new ExamOfStudent(newActiveExam, (Student)ClientUI.loggedInUser.getUser());
		examOfStudent.setQuestionsAndAnswers(studentQuestions);
		examOfStudent.setTotalTime((newActiveExam.getTimeAllotedForTest()*60 - timeForTimer.get())/60);
		examOfStudent.setExamType("computerized");
		examOfStudent.setReasonOfSubmit(reasonOfSubmit);
		
		RequestToServer req = new RequestToServer("StudentFinishExam");
		req.setRequestData(examOfStudent);
		ClientUI.cems.accept(req);
		
		if (CEMSClient.responseFromServer.getResponseType().equals("Success student finish exam")) {
			GuiCommon.popUp("Submit was successfull. You may exit the exam");
		}
		else {
			GuiCommon.popUp("There has been an error. please contact your teacher");
		}
	}

	@FXML
    void btnNext(ActionEvent event) {
		currentQuestion++;
		loadQuestion(currentQuestion);
		unselectRadioButton();
    }

    @FXML
    void btnPrev(ActionEvent event) {
    	currentQuestion--;
    	loadQuestion(currentQuestion);
    	unselectRadioButton();
    }
    
    private void unselectRadioButton() {
    	btnAnswer1.setSelected(false);
    	btnAnswer2.setSelected(false);
    	btnAnswer3.setSelected(false);
    	btnAnswer4.setSelected(false);
    	
    	if (studentAnswers[currentQuestion] != 0) {
    		switch(studentAnswers[currentQuestion]) {
    		case 1:
    			btnAnswer1.setSelected(true);
        		break;
    		case 2:
    			btnAnswer2.setSelected(true);
        		break;
    		case 3:
    			btnAnswer3.setSelected(true);
        		break;
    		case 4:
    			btnAnswer4.setSelected(true);
        		break;
    		}
    		
    	}
    }


	@FXML
	void checkBoxShowTime(MouseEvent event) {
		lblTimeLeft.setVisible(!lblTimeLeft.visibleProperty().get());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//bring all exam details (also questions and scores)
		RequestToServer req = new RequestToServer("getFullExamDetails");
		req.setRequestData(newActiveExam.getExam());
		ClientUI.cems.accept(req);
		
		newActiveExam.setExam((Exam)CEMSClient.responseFromServer.getResponseData());
		
		// set the timer
		LocalTime currentTime = (new Time(System.currentTimeMillis())).toLocalTime();
		int timeToDeduct = (currentTime.toSecondOfDay() - newActiveExam.getStartTime().toLocalTime().toSecondOfDay())
				/ 60;
		int timeForStudent = (newActiveExam.getTimeAllotedForTest() - timeToDeduct)*60;
		timeForTimer = new AtomicInteger(timeForStudent);
		 timer = new Timer();
		    timer.scheduleAtFixedRate(new TimerTask(){
		        @Override
		        public void run(){
		        	int hours = timeForTimer.get() / 3600;
					int minutes = (timeForTimer.get() % 3600) / 60;
					int seconds = timeForTimer.get() % 60;
					String str = String.format("Time left: %02d:%02d:%02d", hours, minutes, seconds);
					Platform.runLater(() -> lblTimeLeft.setText(str));
		          timeForTimer.decrementAndGet();
		          if (timeForTimer.get() == 0) {
			          Platform.runLater(() -> stopExam());
		          }
		        }
		    }, 0, 1000);
		    
		    ToggleGroup group = new ToggleGroup();
		    btnAnswer1.setToggleGroup(group);
		    btnAnswer2.setToggleGroup(group);
		    btnAnswer3.setToggleGroup(group);
		    btnAnswer4.setToggleGroup(group);

		    studentAnswers = new int[newActiveExam.getExam().getExamQuestionsWithScores().size()];
		    
		    currentQuestion = 0;
		    loadQuestion(currentQuestion);
	}
	
	private void loadQuestion(int i) {
		int qNum = i+1;
		lblQuestionNumber.setText("Question " + qNum + " / 10");
		QuestionInExam q = newActiveExam.getExam().getExamQuestionsWithScores().get(i);
		lblPoints.setText("<" + q.getScore() + "> Points");
		txtQuestionDescription.setText(q.getQuestion().getDescription());
		textQuestion.setText(q.getQuestion().getQuestion());
		btnAnswer1.setText(q.getQuestion().getAnswers()[0]);
		btnAnswer2.setText(q.getQuestion().getAnswers()[1]);
		btnAnswer3.setText(q.getQuestion().getAnswers()[2]);
		btnAnswer4.setText(q.getQuestion().getAnswers()[3]);

		if (currentQuestion == 0) {
			btnPrev.setVisible(false);
		}
		else if (currentQuestion == studentAnswers.length-1) {
			btnNext.setVisible(false);
		}
		else {
			btnPrev.setVisible(true);
			btnNext.setVisible(true);
		}
	}

	private void stopExam() {
		GuiCommon.popUp("Time for exam is over!");
		submitExam(ReasonOfSubmit.forced);
	}
	
	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;
	}

}
