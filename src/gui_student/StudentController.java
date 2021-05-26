package gui_student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.Student;
import gui_cems.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Hadar Iluz
 *
 */
public class StudentController extends Application implements Initializable {

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

	LoginController loginController;
	protected Student student;
	protected static GridPane root;
	public Scene scene;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		student = (Student) ClientUI.loggedInUser.getUser();
//		textStudentName.setText(student.getFirstName() + " " + student.getLastName());
//		// TODO: continue ..
		textStudentName.setText(ClientUI.loggedInUser.getUser().getFirstName());
	}

	/**
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		root = new GridPane();
		scene = new Scene(root, 980, 580);// SCREENS size
		Pane newMnueLeft = FXMLLoader.load(getClass().getResource("StudentMenuLeft.fxml"));
		root.add(newMnueLeft, 0, 0);
		primaryStage.setTitle("CEMS-Computerized Exam Management System");
		primaryStage.setScene(scene);
		primaryStage.show();

//		/*
//		 * listen for close events on a JavaFX Stage, notified when the user clicks the
//		 * button with the X on, in the upper right corner of the Stage
//		 */
//		primaryStage.setOnCloseRequest((event) -> {
//			System.out.println("Closing Stage");
//			// need to notified server about this?
//		});

		// -----pic-----//

		// CHECK:
//		// creating the image object
//		InputStream stream = new FileInputStream("@../../images/student_img/signIn_student.png");
//		Image image = new Image(stream);
//		// Setting image to the image view
//		imgStudent.setImage(image);
//		// Setting the image view parameters
//		imgStudent.setX(58);
//		imgStudent.setY(56);
//		imgStudent.setPreserveRatio(true);

	}

	/**
	 * @param event that loading the student's right screen after pressing a button.
	 */
	@FXML
	void btnStartComputerizedExam(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("/gui_student/EnterToExam.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event that loading the student's right screen after pressing a button.
	 */
	@FXML
	void btnStartManualExam(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("/gui_student/EnterToExam.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event that loading the student's right screen after pressing a button.
	 */
	@FXML
	void btnViewExamInfo(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("/gui_student/ViewExam.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Event Logout that occur when clicking on logout at the left menu
	 * 
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
				loginController.performLogout(this.student);

			}
		});

	}

	// TODO:
	/*
	 * ������ ������ ���� ����� ������ ������ ��� ��� ������� ���� ����� ��� ��� ���
	 * �� ������ �� �examID �� �� ���� ����� �� ���� �� ���� ���� �����.
	 */

}
