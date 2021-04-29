package gui;

import java.awt.event.ActionEvent;

import Server.ServerUI;
//import application.ServerLogger;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerFrameController1 {

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private TextField txtPort;

    @FXML
    private TextArea txtLog;
    
    private String getPort() {
		return txtPort.getText();
	}
    
    @FXML
	void initialize() {
		//miscellaneousVBox.setVisible(false);
		txtLog.setWrapText(true);
		txtPort.setText("5555");
		txtLog.setEditable(false);
		txtLog.appendText("sadasdasd");
	}
    
    
    
	public void start(Stage primaryStage) throws Exception {
		
		/*
		 * Pane root =
		 * FXMLLoader.load(getClass().getResource("/gui/ServerController.fxml"));
		 * 
		 * Scene scene = new Scene(root);
		 * //scene.getStylesheets().add(getClass().getResource("/gui/ServerGUI.css").
		 * toExternalForm()); primaryStage.setTitle("CEMS Server");
		 * primaryStage.setScene(scene); primaryStage.show();
		 */
		//txtLog = new TextArea();
		//txtLog.setWrapText(true);
     //	txtLog.setEditable(false);
	//	txtLog.appendText("sadasdasd");
	
		/*
		 * wait(100000); txtLog.setWrapText(true);
		 * 
		 * Platform.runLater(new Runnable() {
		 * 
		 * @Override public void run() { txtLog.appendText("asdasas"); } });
		 */
		/*
		 * 
		 * Pane scroll = new Pane(); scroll.add(txtArea);
		 */
	//	txtArea.append("Check");
	}
	
	
//	public void pressStartServerBtn(ActionEvent event) throws Exception {
//		String p;
//
//		p = getPort();
//		if (p.trim().isEmpty()) {
//			ServerUI.runServer("5555");// default port
//			txtLog.setText("Server listening for connections on port 5555");
//			
//		} else {
//			ServerUI.runServer(p);
//			//txtLogArea.setText("Server listening for connections on port " + p);
//		}
//
//	}

	/*
	 * @FXML void ServerStart(ActionEvent event) {
	 * 
	 * 
	 * }
	 * 
	 * @FXML void ServerStop(ActionEvent event) {
	 * 
	 * }
	 */

}
