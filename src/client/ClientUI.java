package client;

import java.io.IOException;

import gui_cems.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientController cems; // only one instance

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws IOException {
		cems = new ClientController("localhost", 5555);
		// TODO Auto-generated method stub
		
		//NEED TO CHANGE WHEN START WORKING ON CEMS PROJECT
		LoginController LogInHomeScreen = new LoginController(); // create the TestFrame
		 LogInHomeScreen.start(primaryStage);

	}
	
}
