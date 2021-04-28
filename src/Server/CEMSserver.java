// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.*;
import java.util.Vector;

import com.mysql.cj.jdbc.result.UpdatableResultSet;


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

  public CEMSserver(int port) 
  {
    super(port);
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
    	if (msg.equals("Table")) {
    		this.sendToAllClients(dbController.getTestTable());
    	}
    	else if (msg instanceof UpdateDataRequest) {
    		dbController.updateTestTime((UpdateDataRequest) msg);
    	}
	    else {
	    	System.out.println("Error in request");
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
