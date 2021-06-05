package gui_teacher;

import java.io.IOException
;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.CEMSClient;
import client.ClientUI;
import entity.Profession;
import entity.Teacher;
import entity.User;
import gui_cems.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import logic.RequestToServer;


/**
 * @author Yadin and Nadav
 *
 */
public class TeacherController extends Application implements Initializable {

    @FXML
    private ImageView imgPrincipal;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label textTeacherName;

    @FXML
    private Button brnManageQuestionsBank;

    @FXML
    private ImageView imgPhone;

    @FXML
    private ImageView imgEmail;

    @FXML
    private Button btnManageExamsBank;

    @FXML
    private Button btnGetStatistics;

    @FXML
    private Button btnScoreApproval;

    @FXML
    private Button btnChangeExamTime;

    @FXML
    private Label pressLogout;
	
	public LoginController loginController;
	public static GridPane root;
	public Scene scene;
	protected User teacher;
	private static HashMap<String, Profession> professionsMap = null;

	/**TODO: add comment
	 * @param event
	 */
	@FXML
	void btnManageQuestionsBank(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("QuestionBank.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	/*TODO: add comment
	 * @param event
	 */
	@FXML
	void btnChangeExamTime(ActionEvent event) {
		try {

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("AddTimeToExam.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	/**TODO: add comment
	 * @param event
	 */
	@FXML
	void btnGetStatistics(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("DemoStatistics.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	/** TODO: add comment
	 * @param event
	 */
	@FXML
	void btnManageExamsBank(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("ExamBank.fxml"));
			root.add(newPaneRight, 1, 0);
		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}

	/** TODO: add comment
	 * @param event
	 */
	@FXML
	void btnScoreApproval(ActionEvent event) {
		try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("ScoreApproval.fxml"));
			root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}

	}
	
	
	/**Event Logout that occur when clicking on logout at the left menu
	 * @param event that display pop up message and ask if he want to logout.
	 */
	@FXML
	void pressLogout(MouseEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setContentText("Are you sure you want to logout ?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {		
				loginController = new LoginController();
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary(Main) window
				try {
					loginController.start(new Stage());

				} catch (IOException e) {
					e.printStackTrace();
				}
				loginController.performLogout(ClientUI.loggedInUser.getUser());

			}
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		root = new GridPane();
		scene = new Scene(root, 980, 580); // Login
		Pane newMnueLeft = FXMLLoader.load(getClass().getResource("TeacherMenuLeft.fxml"));
		root.add(newMnueLeft, 0, 0);
		primaryStage.setTitle("CEMS-Computerized Exam Management System");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		listenToCloseWindow(primaryStage);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		teacher=ClientUI.loggedInUser.getUser();
		textTeacherName.setText(teacher.getFirstName()+" " +teacher.getLastName());
		setProfessionMap(((Teacher)ClientUI.loggedInUser.getUser()).getProfessions());
	}
	

	public void setProfessionMap(ArrayList<Profession> professionsList) {
		professionsMap = new HashMap<>();
		for (Profession p: professionsList) {
			professionsMap.put(p.getProfessionName(), p);
		}
	}
	
	public static HashMap<String, Profession> getProfessionsMap() {
		return professionsMap;
	}
	
	
	/**
	 * listen for close events on a JavaFX Stage, notified when the user clicks the
	 * button with the X on, in the upper right corner of the Stage
	 * 
	 * @param primaryStage
	 */
	private void listenToCloseWindow(Stage primaryStage) {

		primaryStage.setOnCloseRequest((event) -> {
			System.out.println("Closing Stage");
			RequestToServer reqLogged = new RequestToServer("ClientDisconected");
			reqLogged.setRequestData(ClientUI.loggedInUser.getUser());
			ClientUI.cems.accept(reqLogged);
			// "USER LOGOUT"
			if (CEMSClient.responseFromServer.getResponseType().equals("USER LOGOUT")) {
				System.exit(0);
			}

		});

	}


}
