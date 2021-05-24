package gui_principal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.ClientUI;
import entity.User;
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
 * @author Hadar_Iluz
 *
 */
public class PrincipalController extends Application implements Initializable{

    @FXML
    private ImageView person;

    @FXML
    private ImageView logo;

    @FXML
    private Label lblUserName;

    @FXML
    private ImageView phone;

    @FXML
    private ImageView messageContactUs;

    @FXML
    private Button btnGetReports;

    @FXML
    private Button btnApproveTimeExtention;

    @FXML
    private Button btnViewInfo;

    @FXML
    private Label pressLogout;

	public static LoginController loginController;
	protected User principal;
	protected static GridPane root;
	public Scene scene;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		user=ClientUI.loggedInUser.getUser();
//		lblUserName.setText(user.getFirstName()+" " +user.getLastName());
		lblUserName.setText(ClientUI.loggedInUser.getUser().getFirstName());
		//TODO: continue ..	img for jar file
	}
	
	
	/**
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		root = new GridPane();
		scene = new Scene(root, 980, 580);// SCREENS size
		Pane newMnueLeft = FXMLLoader.load(getClass().getResource("PrincipalMenuLeft.fxml"));
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
//			//need to notified server about this?
//		});
		
		
		//-----pic-----//
		
//		// <ImageView fx:id="person" fitHeight="111.0" fitWidth="112.0" layoutX="58.0" layoutY="56.0" pickOnBounds="true" preserveRatio="true">
//	      //creating the image object
//	      InputStream stream = new FileInputStream("@../../images/teacher-Principal_img/PrincipalUserImg.png");
//	      Image image = new Image(stream);
//	      //Setting image to the image view
//	      imgPrincipal.setImage(image);
//	      //Setting the image view parameters
//	      imgPrincipal.setX(58);
//	      imgPrincipal.setY(56);
//	      imgPrincipal.setPreserveRatio(true);
		

		
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
			System.out.println("Couldn't load- ApprovalTimeExtention.fxml");
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
			System.out.println("Couldn't load- PrincipalGetReports.fxml");
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
			System.out.println("Couldn't load fxml");
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
				loginController.performLogout(principal);

			}
		});

	}



}
