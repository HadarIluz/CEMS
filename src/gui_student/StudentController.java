package gui_student;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import entity.User;
import gui_cems.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author iluzh
 *
 */
public class StudentController {

	@FXML
	private Label pressLogout;

	@FXML
	private ImageView imgStudent;

	@FXML
	private ImageView imgLogo;

	@FXML
	private Label textHelloStudent;

	@FXML
	private Label textStudentName;

	@FXML
	private Button btnStartManualExam;

	@FXML
	private ImageView imgPhone;

	@FXML
	private ImageView imgEmail;

	@FXML
	private Button btnViewExamInfo;

	@FXML
	private Button btnStartComputerizedExam;

	//
	public static LoginController loginController;
	public User user;
	//
	protected static GridPane root;
	protected static Pane newPane;
	
	/**
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		this.root = new GridPane();
		Scene scene = new Scene(root, 988, 586); // SCREENS size
		primaryStage.setTitle("CEMS-Computerized Exam Management System");
		this.newPane = FXMLLoader.load(getClass().getResource("/gui_student/StudentMenuLeft.fxml"));
		root.add(newPane, 0, 0);
		// a grey screen need to display at the right side, handle this here if it is
		// not working..(after test)
		// Pane newPaneRight=
		// root.add(newPaneRight, 1, 0);

		/*
		 * listen for close events on a JavaFX Stage, notified when the user clicks the
		 * button with the X on, in the upper right corner of the Stage
		 */
		primaryStage.setOnCloseRequest((event) -> {
			System.out.println("Closing Stage");
			//need to notified server about this?
		});
		
		
		//-----pic-----//
		
		
	      //creating the image object
	      InputStream stream = new FileInputStream("@../../images/student_img/signIn_student.png");
	      Image image = new Image(stream);
	      //Setting image to the image view
	      imgStudent.setImage(image);
	      //Setting the image view parameters
	      imgStudent.setX(58);
	      imgStudent.setY(56);
	      imgStudent.setPreserveRatio(true);
		
	}
	
	
	/**
	 * @param event that loading the student's right screen after pressing a button.
	 */
	@FXML
	void btnStartComputerizedExam(ActionEvent event) {
		try {
			Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_student/EnterToExam.fxml"));
			root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param event that loading the student's right screen after pressing a button.
	 */
	@FXML
	void btnStartManualExam(ActionEvent event) {
		try {
			Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_student/EnterToExam.fxml"));
			root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param event that loading the student's right screen after pressing a button.
	 */
	@FXML
	void btnViewExamInfo(ActionEvent event) {
		try {
			Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_student/ViewExam.fxml"));
			root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**Event Logout that occur when clicking on logout at the left menu
	 * @param event that display pop up message and ask if he want to logout.
	 */
	@FXML
	void pressLogout(MouseEvent event) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Logout");
		alert.setContentText("Are you Sure?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
				// ASK -Sure not like that, I currently do not know what it
				// will look like after learning about the singleton.
				loginController.performLogput(this.user);

			}
		});

	}
	
	
	/*
	 * שאילתה שמוצאת מבחן שתואם בתאריך ולבדוק האם האם הסטודנט רשום לקורס הזה
	 * ואם וכן אז להוציא את הexamID
	 * אם לא חלון שאומר לו שאין לו מבחן פעיל עכשיו.
	 */

}
