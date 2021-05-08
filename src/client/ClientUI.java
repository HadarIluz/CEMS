package client;
import gui_prototype.MainFormController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientController cems; //only one instance

	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 cems= new ClientController("localhost", 5555);
		// TODO Auto-generated method stub
		 
		 MainFormController MainForm = new MainFormController(); // create the TestFrame
		 MainForm.start(primaryStage);
	}

	
}
