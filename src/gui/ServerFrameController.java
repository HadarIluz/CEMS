package gui;

import java.awt.TextArea;

import Server.ServerUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerFrameController {

	String temp = "";

	@FXML
	private Button btnStartServer = null;
	@FXML
	private Button btnStopServer = null;

	@FXML
	private TextField portxt;

	@FXML
	private TextArea txtLogArea;

	
	private String getPort() {
		return portxt.getText();
	}
	
	
	//connect between messages to present in txtLogArea
	public String messagesConnector(String oldStr,String addStr) {
		return oldStr + "\n" + addStr;
	}

	public void pressStartServerBtn(ActionEvent event) throws Exception {
		String p;

		p = getPort();
		if (p.trim().isEmpty()) {
			ServerUI.runServer("5555");// default port
			txtLogArea.setText("Server listening for connections on port 5555");
		} else {
			ServerUI.runServer(p);
			txtLogArea.setText("Server listening for connections on port " + p);
		}

	}

//	public void Done(ActionEvent event) throws Exception {
//		String p;
//
//		p = getport();
//		if (p.trim().isEmpty()) {
//			System.out.println("You must enter a port number");
//
//		} else {
//			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//			Stage primaryStage = new Stage();
//			FXMLLoader loader = new FXMLLoader();
//			ServerUI.runServer(p);
//		}
//	}

	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/gui/ServerGUI.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("CEMS Server");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);
	}


	public void printToTextArea(String string) {
		// TODO Auto-generated method stub
		
	}

}