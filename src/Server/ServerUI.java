package Server;

import java.util.Vector;

import gui.ServerFrameController;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.TestRow;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static Vector<TestRow> students=new Vector<TestRow>();
	public static ServerFrameController sFrame;

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub				  		
		sFrame = new ServerFrameController(); // create StudentFrame
		 
		sFrame.start(primaryStage);
	}
	
	public static void runServer(String p)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!");
	        }
	    	
	        // instead of loader - need to put object that is the ui controller...
	        CEMSserver sv = new CEMSserver(port, sFrame);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
	

}
