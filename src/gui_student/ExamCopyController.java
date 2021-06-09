package gui_student;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Exam;
import entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ExamCopyController implements Initializable{

    @FXML
    private TableView<Question> tableQuestion;

   
    @FXML
    private TableColumn<Question, String> Question;

    @FXML
    private TableColumn<Question, String> YourAnswer;

    @FXML
    private TableColumn<Question, String> CorrectAns;

    @FXML
    private Text textNavigation;
    
	private ObservableList<Question> data;


	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableQuestion.getColumns().clear();
		data = FXCollections.observableArrayList(ViewExamController.questions);
		Question.setCellValueFactory(new PropertyValueFactory<>("question"));
		YourAnswer.setCellValueFactory(new PropertyValueFactory<>("StdAns"));
		CorrectAns.setCellValueFactory(new PropertyValueFactory<>("correctAns"));
		tableQuestion.setItems(data);
		tableQuestion.getColumns().addAll(Question, YourAnswer, CorrectAns);

	}

   

}
