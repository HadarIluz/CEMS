package Server;

import java.util.Vector;

import gui.ServerFrameController;
import gui.ServerFrameController1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.TestRow;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static Vector<TestRow> students=new Vector<TestRow>();
	private  ServerFrameController serverFrameController;

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		serverFrameController = new ServerFrameController();
		
		Pane root = FXMLLoader.load(getClass().getResource("/gui/ServerGUI.fxml"));

		Scene scene = new Scene(root);
			primaryStage.setTitle("CEMS Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	

}
