package gui_teacher;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExamBankController {

    @FXML
    private Button btnEditExam;

    @FXML
    private Button btnDeleteExam;

    @FXML
    private TextField textExamID;

    @FXML
    private TableView<?> tableExam;

    @FXML
    private Button btnBack1;

    @FXML
    private Button btnOpenExamInfo;

    @FXML
    private Text textNavigation;

    @FXML
    private Button btnBack;

    @FXML
    void btnBack(ActionEvent event) {

    }

    @FXML
    void btnDeleteExam(ActionEvent event) {

    }

    @FXML
    void btnEditExam(ActionEvent event) {

    }

    @FXML
    void btnOpenExamInfo(ActionEvent event) {

    }
    
    @FXML
    void CreateNewExam(ActionEvent event) {
    	
    	try {
    		
    		
    		
    		Stage primaryStage=new Stage();

			Pane root = FXMLLoader.load(getClass().getResource("QuestionBank.fxml"));
			Scene scene = new Scene(root, 900, 600);
			
			Pane CreateNewExam = FXMLLoader.load(getClass().getResource("CreateExam_step1.fxml"));
			root.getChildren().add(CreateNewExam);
			primaryStage.setTitle("CEMS-Computerized Exam Management System");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

    }

}
