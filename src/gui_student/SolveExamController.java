package gui_student;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import entity.ActiveExam;
import entity.QuestionInExam;
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

    
	@FXML
	void btnAnswer1(MouseEvent event) {
		studentAnswers[currentQuestion] = 1;
	}

	@FXML
	void btnAnswer2(MouseEvent event) {
		studentAnswers[currentQuestion] = 2;
	}

	@FXML
	void btnAnswer3(MouseEvent event) {
		studentAnswers[currentQuestion] = 3;
	}

	@FXML
	void btnAnswer4(MouseEvent event) {
		studentAnswers[currentQuestion] = 4;
	}

	@FXML
	void btnSubmitExam(ActionEvent event) {
		timer.cancel();
		submitExam();
	}
	
	private void submitExam() {
		btnSubmitExam.setDisable(true);
		
	}

	@FXML
    void btnNext(ActionEvent event) {
		currentQuestion++;
		loadQuestion(currentQuestion);
    }

    @FXML
    void btnPrev(ActionEvent event) {
    	currentQuestion++;
    	loadQuestion(currentQuestion);
    }


	@FXML
	void checkBoxShowTime(MouseEvent event) {
		lblTimeLeft.setVisible(!lblTimeLeft.visibleProperty().get());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// set the timer
		AtomicInteger timeForTimer = new AtomicInteger(newActiveExam.getExam().getTimeOfExam()*60);
		 timer = new Timer();
		    timer.scheduleAtFixedRate(new TimerTask(){
		        @Override
		        public void run(){
		          Platform.runLater(() -> lblTimeLeft.setText(timeForTimer.toString()));
		          timeForTimer.decrementAndGet();
		          if (timeForTimer.get() == 0) {
		        	  // cancel the timer
		        	  timer.cancel();
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
		lblQuestionNumber.setText("Question Number" + i+1 + " / 10");
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
		else if (currentQuestion == studentAnswers.length) {
			btnNext.setVisible(false);
		}
		else {
			btnPrev.setVisible(true);
			btnNext.setVisible(true);
		}
	}

	private void stopExam() {
		GuiCommon.popUp("Time for exam is over!");
		submitExam();
		
	}
	
	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;
	}

}
