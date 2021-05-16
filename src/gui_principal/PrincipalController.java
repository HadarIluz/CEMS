package gui_principal;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrincipalController {

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

	//
	public static LoginController loginController;
	public User user;
	//
	protected static GridPane root;
	protected static Pane newPane;

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
	      person.setImage(image);
	      //Setting the image view parameters
	      person.setX(58);
	      person.setY(56);
	      person.setPreserveRatio(true);
		

		
	}

	@FXML
	void btnApproveTimeExtention(ActionEvent event) {
		try {
			Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_principal/ApprovalTimeExtention.fxml"));
			root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void btnGetReports(ActionEvent event) {
		try {
			Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_principal/PrincipalGetReports.fxml"));
			root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//need to create here screen with buttons according to our screen assignment
	@FXML
	void btnViewInfo(ActionEvent event) {
		/*
		try {
			//Pane newPaneRight= FXMLLoader.load(getClass().getResource("/gui_principal/?????.fxml"));
			//root.add(newPaneRight, 1, 0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}

	// Event Logout that occur when clicking on logout at the left menu
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

}
