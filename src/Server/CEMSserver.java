// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.*;
import java.util.Vector;

import com.mysql.cj.jdbc.result.UpdatableResultSet;

import gui.ServerFrameController;
import logic.StatusMsg;
import logic.TestTableRequest;
import logic.UpdateDataRequest;
import ocsf.server.*;

public class CEMSserver extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  //final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * 
   */
	private static DBController dbController = new DBController();
	private static ServerFrameController serverUI;

  public CEMSserver(int port, ServerFrameController serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
  }

  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   * @param 
   */
  public void handleMessageFromClient  (Object msg, ConnectionToClient client)
  {
	 //int flag=0;
	    System.out.println("Message received: " + msg + " from " + client);
    	if (msg instanceof String) {
    		String req = (String)msg;
    		if (req.contains("getRow")) {
    			this.sendToAllClients(dbController.getTestRow(req.split(" ")[1]));
    		}
    	}
    	else if (msg instanceof UpdateDataRequest) {
    		StatusMsg status = new StatusMsg();
    		if (dbController.updateTestTime((UpdateDataRequest) msg)) {
    			status.setStatus("SUCCESS");
    		}
    		else {
    			status.setStatus("ERROR");
    		}
    		this.sendToAllClients(status);

    	}
	    else {
	    	System.out.println("Error in request");
	    	// create this method
	    	serverUI.printToTextArea("Error in request");
	    }
  
  }
   
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println ("Server listening for connections on port " + getPort());

  }
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()  {
    System.out.println ("Server has stopped listening for connections.");
  }  
}
//End of EchoServer class
