// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.IOException;

import entity.Exam;
import entity.Question;
import entity.User;
import gui_server.ServerFrameController;
import logic.RequestToServer;
import logic.StatusMsg;
import logic.UpdateDataRequest;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class CEMSserver extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	private static DBController dbController = new DBController();
	private ServerFrameController serverFrame;

	public CEMSserver(int port, ServerFrameController serverUI) {
		super(port);
		this.serverFrame = serverUI;
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */

	@SuppressWarnings("null")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		StatusMsg status = new StatusMsg();

		serverFrame.printToTextArea("Message received: " + msg + " from " + client);

		RequestToServer req = (RequestToServer) msg;

		switch (req.getRequestType()) {
		case "getUser": {
			// logic of login
			User user = (User) req.getRequestData();
			User userInSystem = null;
			userInSystem = dbController.verifyLoginUser((User) msg);  //DEBUG: problem in this line.
			if (userInSystem != null) {
				userInSystem.setStatus("USER FOUND");
				// serverFrame.printToTextArea(??.toString());
			} else {
				userInSystem.setStatus("USER NOT FOUND");
				// serverFrame.printToTextArea(??.toString());
			}
			// need to change to sendToClient(status);
			// this.sendToAllClients(userInSystem);
			try {
				client.sendToClient(userInSystem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		break;
		case "UpdateUserLoged": {
			// logic of login- update logged status after Successfully login action
			//String reqUpdateID = (String) msg;
			User user = (User) req.getRequestData();
			// boolean chnageSucceed = //for print in serverFrame
			dbController.setLoginUserLogged(user.getId(), user.isLogged());
			// serverFrame.printToTextArea(??.toString());
		}
		break;
		case "createNewQuestion": {
			createNewQuestion((Question)req.getRequestData());
		}
		break;

		case "createNewExam": {
			createNewExam((Exam)req.getRequestData());
		}
		break;

		}
	}

	/**
	 * @param questionData
	 * this method creates the question ID and then inserts the new question into the DB.
	 */
	private void createNewQuestion(Question questionData) {
		int numOfQuestions = dbController.getNumOfQuestionsInProfession(questionData.getProfession().getProfessionID());
		String questionID = String.valueOf(numOfQuestions+1);
		questionID = ("0000"+questionID).substring(questionID.length());
		questionID = questionData.getProfession().getProfessionID() + questionID;
		questionData.setQuestionID(questionID);
		dbController.createNewQuestion(questionData);
	}
	
	private void createNewExam(Exam examData) {
		// create the exam ID by number of exams in this profession and course
		int numOfExams = dbController.getNumOfExamsInCourse(examData.getCourse().getCourseID()) + 1;
		String examID = numOfExams < 10 ? "0" + String.valueOf(numOfExams) : String.valueOf(numOfExams);
		examID = examData.getProfession().getProfessionID() + examData.getCourse().getCourseID() + examID;
		examData.setExamID(examID);
		// create the new exam in DB
		dbController.createNewExam(examData);
		// add questions and scores to DB
		dbController.addQuestionsInExam(examID, examData.getQuestionScores());
	}

	//	    case "getRow":
	//	    {
	//			this.sendToAllClients(dbController.getTestRow(req.split(" ")[1]));
	//	    }

	// }

	//    	if (msg instanceof String) {
	//    		String req = (String)msg;
	//    		if (req.contains("getRow")) {
	//    			this.sendToAllClients(dbController.getTestRow(req.split(" ")[1]));
	//    		}
	//    		/*---------Login----------*/
	//    		if(req.contains("getUser")) {
	//    			User userInSystem= null;
	//    			userInSystem = dbController.verifyLoginUser((User) msg);
	//        		if (userInSystem != null) {
	//        			userInSystem.setStatus("USER FOUND");
	//        			//serverFrame.printToTextArea(??.toString());
	//        		}
	//        		else {
	//        			userInSystem.setStatus("USER NOT FOUND");
	//        			//serverFrame.printToTextArea(??.toString());
	//        		}
	//        		//need to change to sendToClient(status);
	//        		this.sendToAllClients(userInSystem);
	//        		}
	//    			
	//    		}
	//    		/*---------End_Login----------*/
	//    		
	//    	
	//    	if (msg instanceof UpdateDataRequest) {
	//    		
	//    		if (dbController.updateTestTime((UpdateDataRequest) msg)) {
	//    			status.setStatus("SUCCESS");
	//    			status.setDescription(" Table Updated");
	//    			serverFrame.printToTextArea(status.toString());
	//    			
	//    		}
	//    		else {
	//    			status.setStatus("ERROR");
	//    			status.setDescription(" Incorrect value");
	//    			serverFrame.printToTextArea(status.toString());
	//    		}
	//    		this.sendToAllClients(status);
	//
	//    	}
	//	    else {
	//	     	status.setStatus("ERROR");
	//			status.setDescription("Error in request");
	//			serverFrame.printToTextArea(status.toString());
	//	    	
	//	    }

	// }

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		serverFrame.printToTextArea("Server listening for connections on port " + getPort());
		dbController.connectDB(serverFrame);
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		serverFrame.printToTextArea("Server has stopped listening for connections.");
	}
}