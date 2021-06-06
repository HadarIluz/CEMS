package gui_student;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import common.MyFile;
import entity.ActiveExam;
import entity.ExamOfStudent;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.RequestToServer;

public class StartManualExamController implements Initializable{

    @FXML
    private Button btnDownload;

    @FXML
    private ImageView imgDownload;

    @FXML
    private Button btnSubmit;

    @FXML
    private Text txtUploadSucceed;

    @FXML
    private Text txtError3;

    @FXML
    private Text txtError2;

    @FXML
    private Text txtError1;

    @FXML
    private Text txtMessageFrom;

    @FXML
    private ImageView imgNotification;

    @FXML
    private Label textTeacherName;

    @FXML
    private Label textNotificationMsg;

    @FXML
    private CheckBox checkBoxShowTime;

    @FXML
    private Label textTimeLeft;
    
    @FXML
    private TextField textLocalFilePath;

    @FXML
    private TextField textFileName;
    
    private static StudentController studentController; //why ??
    private static ActiveExam newActiveExam;
    
    private Student student; //MAYBE NOT NEED BECAUSE STUDENTCONTROLLER ??
    private ExamOfStudent examOfStudent; 

    @FXML
    void btnDownload(ActionEvent event) {
    	//Download the exam from the database to the student's computer
    	RequestToServer req = new RequestToServer("downloadManualExam");
		req.setRequestData(examOfStudent);
    	ClientUI.cems.accept(req);
    	btnDownload.setDisable(true);
    	btnSubmit.setDisable(false);
    }

    @FXML
    void btnSubmit(ActionEvent event) throws IOException {
    	String fileName = examOfStudent.getActiveExam().getExam().getExamID()+ "_" + examOfStudent.getStudent().getId() + ".docx";
		String home = System.getProperty("user.home");
    	String LocalfilePath = home + "/Downloads/" + examOfStudent.getActiveExam().getExam().getExamID() + "_exam.docx";
    	MyFile submitExam =  new MyFile(fileName); 
    	//////////////////////////////////////////
    	Scene scene = new Scene(SubmitPopUp());
		stage.setTitle("Voting Machine");
		stage.setScene(scene);
		stage.show();
    		try {
    			File newFile = new File (LocalfilePath);
    		    byte [] mybytearray  = new byte [(int)newFile.length()];
    		    FileInputStream fis = new FileInputStream(newFile);
    		    BufferedInputStream bis = new BufferedInputStream(fis);			   
    		    submitExam.initArray(mybytearray.length);
    		    submitExam.setSize(mybytearray.length);
    		    bis.read(submitExam.getMybytearray(),0,mybytearray.length);   
    		    RequestToServer req = new RequestToServer("submitManualExam");
    			req.setRequestData(submitExam);
    	    	ClientUI.cems.accept(req); 
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    }

    @FXML
    void checkBoxShowTime(ActionEvent event) {
    	//show time left
    	if(checkBoxShowTime.isSelected()) {
    		textTimeLeft.setDisable(false);
    		textTimeLeft.setOpacity(1.0);
    	} else { // Do not show time left
    		textTimeLeft.setDisable(true);
    		textTimeLeft.setOpacity(0.0);
    	}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		student = (Student) ClientUI.loggedInUser.getUser();
		examOfStudent = new ExamOfStudent(newActiveExam,student);		
	}
	
	public static void setActiveExamState(ActiveExam newActiveExamInProgress) {
		newActiveExam = newActiveExamInProgress;
	}
	
	/**
	 * this method create a popup with a message.
	 * 
	 * @param str
	 */
	public void popUp(String str) {
		final Stage dialog = new Stage();
		VBox dialogVbox = new VBox(20);
		Label lbl = new Label(str);
		lbl.setPadding(new Insets(5));
		lbl.setAlignment(Pos.CENTER);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		dialogVbox.getChildren().add(lbl);
		Scene dialogScene = new Scene(dialogVbox, lbl.getMinWidth(), lbl.getMinHeight());
		dialog.setScene(dialogScene);
		dialog.show();
	}
	
	private GridPane SubmitPopUp() {
		
		
		
		
		GridPane root = new GridPane();
		root.setPadding(new Insets(15)); 
		root.setHgap(20);
		root.setVgap(10);
		
		Button btnSubmit = new Button("Submit");
		Button btnCancel = new Button("Cancel");
		BackgroundFill bgFillSubmit = new BackgroundFill(Color.MEDIUMSEAGREEN, null, null);
		BackgroundFill bgFillCancel = new BackgroundFill(Color.INDIANRED, null, null);
		btnSubmit.setBackground(new Background(bgFillSubmit));
		btnCancel.setBackground(new Background(bgFillCancel));
		root.add(btnCancel, 0, 1);
		root.add(btnSubmit, 1, 0);
		Label lbl = new Label("Please Note!\nOnce you click Submit you can't edit exam egain.");
		lbl.setFont(new Font("Arial", 18)); // Set the font and font size for label
		lbl.setStyle("-fx-font-weight: bold"); // The text on the label will be bold
		lbl.setMaxWidth(Double.MAX_VALUE);
		lbl.setAlignment(Pos.CENTER); // What is written will be in the middle of the label
		root.add(lbl, 0, 1, 2, 1);

		//class LabelIncreaser implements EventHandler<ActionEvent> {
			//@Override
			//public void handle(ActionEvent event) {

				//Button b = (Button) event.getSource(); // Saves which button is pressed.
				//if (b.equals(bOfra)) // When pressed the button "Ofra Haza"
					//count++; // We will add one fan to Ofra
				//if (b.equals(bYardena)) // When pressed the button "Yardena Arazi"
					//count--; // We will remove one fan from Ofra
				//label.setText(count + ""); // Determines which number will appear on the label
			//}
		//}
		//bOfra.setOnAction(new LabelIncreaser());
		//bYardena.setOnAction(new LabelIncreaser());
		return root;
	}

}
