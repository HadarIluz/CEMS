package gui;

import java.io.IOException;

//import com.sun.prism.Image;

import Server.CEMSserver;
import Server.DBController;
import Server.ServerUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class ServerFrameController {

	final public static String DEFAULT_PORT = "5555";
	@FXML
	private VBox rootOfMain;
	@FXML
	private SplitPane paneSplitter;
	@FXML
	private Pane serevrUserInteraction;
	@FXML
	private TextField portxt;
	@FXML
	private Button btnStartServer;
	@FXML
	private Button btnStop;
	@FXML
	private Font x1;
	@FXML
	private TextArea txtArea;
	@FXML
	private Font x3;
	@FXML
	private Button ClearLogBtn;

	private CEMSserver Eserver;
	private DBController dbControl;

	private String getPort() {
		return portxt.getText();
	}
	public void pressStopServerBtn(ActionEvent event) throws Exception {
		String port=getPort();
		if (port.trim().isEmpty())
		ServerUI.closeServer(DEFAULT_PORT, this);
		else
		ServerUI.closeServer(port, this);
		btnStartServer.setDisable(false);
		btnStop.setDisable(true);
	}

	public void pressStartServerBtn(ActionEvent event) throws Exception {
		String p;
		p = getPort();
		txtArea.setEditable(false); //user can not write in text area
		if (p.trim().isEmpty()) {
			ServerUI.runServer(DEFAULT_PORT, this);// default port
			btnStartServer.setDisable(true);
			btnStop.setDisable(false);

		} else {
			ServerUI.runServer(p, this);
			btnStartServer.setDisable(true);
			btnStop.setDisable(false);
		}

	}

	public void ClearLogTextArea(ActionEvent event) throws Exception {
		txtArea.clear();
		txtArea.setPromptText("");// clear the opening sentence of the text area
	}

	public void start(Stage primaryStage) throws IOException {
		Pane root = FXMLLoader.load(getClass().getResource("/gui/ServerGUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEMS Server");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void printToTextArea(String msg) {
		txtArea.appendText(msg + "\n");
	}

}