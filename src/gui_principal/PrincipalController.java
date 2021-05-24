package gui_principal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import entity.User;
import gui_cems.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Hadar_Iluz
 *
 */
public class PrincipalController implements Initializable{

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
    private Button btnCreateActiveExam;

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

	//
	public static LoginController loginController;
	public User user;
	//
	protected static GridPane root;
	protected static Pane newPane;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user=ClientUI.loggedInUser.getUser();
		textTeacherName.setText(user.getFirstName()+" " +user.getLastName());
		//TODO: continue ..		
	}
	
	
	/**
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		this.root = new GridPane();
		Scene scene = new Scene(root, 988, 586); // SCREENS size
		primaryStage.setTitle("CEMS-Computerized Exam Management System");
		this.newPane = FXMLLoader.load(getClass().getResource("/gui_principal/PrincipalMenuLeft.fxml"));
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
		
		// <ImageView fx:id="person" fitHeight="111.0" fitWidth="112.0" layoutX="58.0" layoutY="56.0" pickOnBounds="true" preserveRatio="true">
	      //creating the image object
	      InputStream stream = new FileInputStream("@../../images/teacher-Principal_img/PrincipalUserImg.png");
	      Image image = new Image(stream);
	      //Setting image to the image view
	      imgPrincipal.setImage(image);
	      //Setting the image view parameters
	      imgPrincipal.setX(58);
	      imgPrincipal.setY(56);
	      imgPrincipal.setPreserveRatio(true);
		

		
	}

	/**
	 * @param event that loading the teacher's right screen after pressing a button.
	 */
	@FXML
	void btnApproveTimeExtention(ActionEvent event) {
		try {
			Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_principal/ApprovalTimeExtention.fxml"));
			root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param event that loading the teacher's right screen after pressing a button
	 */
	@FXML
	void btnGetReports(ActionEvent event) {
		try {
			Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_principal/PrincipalGetReports.fxml"));
			root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//need to create here screen with buttons according to our screen assignment
	@FXML
	void btnViewInfo(ActionEvent event) {
		//TODO: task Lior (TERELLO).
		/*
		try {
			//Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_principal/?????.fxml"));
			//root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 */
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
				loginController.performLogout(this.user);

			}
		});

	}



}
