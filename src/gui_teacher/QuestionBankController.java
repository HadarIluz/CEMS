package gui_teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Exam;
import entity.Profession;
import entity.Question;
import entity.QuestionRow;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class QuestionBankController extends TeacherController implements Initializable  {

    @FXML 
    private Button btnEditQuestion;

    @FXML
    private Button btnDeleteQuestion;

    @FXML
    private TextField textQuestionID;

    @FXML
    private TableView<QuestionRow> tableQuestion;

    @FXML
    private Text textMsg1;

    @FXML
    private Text textMsg2;

    @FXML
    private Button btnCreateNewQuestion;

    @FXML
    private Button btnOpenQuestionInfo;

    @FXML
    private Text textNavigation;

    
    @FXML
    private TableColumn<QuestionRow, String> QuestionID;

    @FXML
    private TableColumn<QuestionRow,String> Proffesion;

    @FXML
    private TableColumn<QuestionRow, String> Question;
    
    private ObservableList<QuestionRow> data;

    
    @FXML
    void MouseC(MouseEvent event) {
    	
    	
     	ObservableList<QuestionRow> Qlist;
    	Qlist= tableQuestion.getSelectionModel().getSelectedItems();
    	textQuestionID.setText(Qlist.get(0).getQuestionID());

    }

    @FXML
    void btnCreateNewQuestion(ActionEvent event)  {

		try {

			AnchorPane newPaneRight = FXMLLoader.load(getClass().getResource("CreateQuestion.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

    @FXML
    void btnDeleteQuestion(ActionEvent event) {
    	ObservableList<QuestionRow> Qlist;
		
    	Qlist= tableQuestion.getSelectionModel().getSelectedItems();
    	
    	data.removeAll(Qlist);

    }

    @FXML
    void btnEditQuestion(ActionEvent event) {
    	try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("EditQuestion.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
    }

    

	

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Profession pro=new Profession("03","Math");
	
		
		Question q1=new Question("1000", "what is the sqrt(9)?", null, null, 0, pro,null);
		Question q2=new Question("1001", "what is the sqrt(25)?", null, null, 0, pro,null);
		
		
		QuestionRow qr= new QuestionRow();
		qr.setQuestionID(q1.getQuestionID());
		qr.setQuestion(q1.getQuestion());
		qr.setProfession(q1.getProfession().getProfessionName());
		
		QuestionRow qr2= new QuestionRow();
		qr2.setQuestionID(q2.getQuestionID());
		qr2.setQuestion(q2.getQuestion());
		qr2.setProfession(q2.getProfession().getProfessionName());
		
		
		 data = FXCollections.observableArrayList(qr,qr2);
		
		tableQuestion.getColumns().clear();
		QuestionID.setCellValueFactory(new PropertyValueFactory<>("QuestionID"));
		Proffesion.setCellValueFactory(new PropertyValueFactory<>("profession"));
		Question.setCellValueFactory(new PropertyValueFactory<>("Question"));
		
		
		tableQuestion.setItems(data);
		
		
		tableQuestion.getColumns().addAll(QuestionID, Proffesion, Question);
		
		 
	}

}
