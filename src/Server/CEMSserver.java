// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import client.ClientUI;
import common.MyFile;
import entity.ActiveExam;
import entity.Exam;
import entity.ExamOfStudent;
import entity.ExtensionRequest;
import entity.Profession;
import entity.Question;
import entity.QuestionInExam;
import entity.Student;
import entity.Teacher;
import entity.UpdateScoreRequest;
import entity.User;
import gui_server.ServerFrameController;
import logic.RequestToServer;
import logic.ResponseFromServer;
import logic.StatusMsg;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class CEMSserver extends AbstractServer {
	// Class variables *************************************************
	//
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
	private HashMap<Integer, User> loggedInUsers;
	private HashMap<Integer, ConnectionToClient> loogedClients; // HashMap<StudentID>

	public CEMSserver(int port, ServerFrameController serverUI) {
		super(port);
		this.serverFrame = serverUI;
		loggedInUsers = new HashMap<Integer, User>();
		loogedClients = new HashMap<Integer, ConnectionToClient>();
	}

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
		ResponseFromServer respon = null;

		switch (req.getRequestType()) {

		case "getUser": {
			getUser((User) req.getRequestData(), client);

		}

			break;
		case "UpdateUserLoggedIn": {
			UpdateUserLoggedIn((User) req.getRequestData(), client);

		}
			break;

		case "UpdateUserLoggedOut": {
			UpdateUserLoggedOut((User) req.getRequestData(), client);

		}
			break;

		case "getStudentData_Login": {
			getStudentData_Login((Student) req.getRequestData(), client);

		}
			break;

		case "getTeacherData_Login": {
			getTeacherData_Login((Teacher) req.getRequestData(), client);
		}
			break;

		case "getStudentGrades": {
			getStudentGrades((int) req.getRequestData(), client);
		}
			break;

		case "getAllStudentsExams": {
			getAllStudentsExams(client);
		}
			break;

		case "createNewQuestion": {
			createNewQuestion((Question) req.getRequestData(), client);
		}
			break;

		case "teacherStatistics": {

		}
			break;

		case "getTeachers": {
			getTeachers(client);

		}
			break;
		case "getProfNames": {
			getProfNames(client);
		}
			break;
		case "getCoursesNames": {
			getCoursesNames(client);
		}
			break;

		case "createNewExam": {
			try {
				client.sendToClient(createNewExam((Exam) req.getRequestData()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			break;

		case "addTimeToExam": {
			addTimeToExam((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "getQuestionBank": {
			getQuestionBank((Question) req.getRequestData(), client);
		}
			break;

		case "createNewExtensionRequest": {
			createNewExtensionRequest((ExtensionRequest) req.getRequestData(), client);
		}
			break;

		case "isActiveExamExist": {
			isActiveExamExist((ActiveExam) req.getRequestData(), client);

		}
			break;

		case "getAllActiveExamBeforEnterToExam": {
			getAllActiveExamBeforEnterToExam(client);
		}
			break;

		case "approvalTimeExtension": {
			approvalTimeExtension((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "gradesAverageCalc": {
			gradesAverageCalc((String) req.getRequestData(), client);

		}
			break;

		case "declineTimeExtension": {
			declineTimeExtension((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "getStudents": {
			getStudents(client);
		}
			break;

		case "getStudentsByExamID": {
			try {
				ResponseFromServer Res = new ResponseFromServer("SCORE APPROVAL");
				Res.setResponseData(dbController.SetDetailsForScoreApprovel((String) req.getRequestData()));
				client.sendToClient(Res);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
			break;

		case "getQuestions": {

			try {

				ResponseFromServer respond = new ResponseFromServer("TEACHER QUESTIONS");
				respond.setResponseData(dbController.GetTeacherQuestions((Integer) req.getRequestData()));

				client.sendToClient(respond);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		case "ClientDisconected": {
			clientDisconected(req.getRequestData(), client);

		}
			break;

		case "getExams": {

			try {

				ResponseFromServer respond = new ResponseFromServer("TEACHER EXAMS");
				respond.setResponseData((ArrayList<Exam>) dbController.GetTeacherExams((Integer) req.getRequestData()));
				client.sendToClient(respond);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
			break;

		case "DeleteQuestion": {
			try {

				ResponseFromServer respond = new ResponseFromServer("DELETE TEACHER QUESTION");
				if (dbController.DeleteQuestion((Question) req.getRequestData()))
					respond.setResponseData("TRUE");
				else
					respond.setResponseData("FALSE");

				client.sendToClient(respond);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		case "DeleteExam": {
			try {

				ResponseFromServer respond = new ResponseFromServer("DELETE TEACHER EXAM");
				if (dbController.DeleteExam((Exam) req.getRequestData()))
					respond.setResponseData("TRUE");
				else
					respond.setResponseData("FALSE");

				client.sendToClient(respond);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
			break;

		case "getExtensionRequests": {
			getExtensionRequests(client);

		}
			break;

		case "chechExamExist": {
			chechExamExist((String) req.getRequestData(), client);

		}
			break;

		case "Update Grade": {
			try {

				ResponseFromServer respond = new ResponseFromServer("TEACHER SCORE UPDATE");
				respond.setResponseData(dbController.UpdateScoreOfStudent((UpdateScoreRequest) req.getRequestData()));

				client.sendToClient(respond);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

			break;

		case "getCoursesByProfession": {
			getCoursesByProfession((Profession) req.getRequestData(), client);
		}
			break;

		case "downloadManualExam": {
			downloadManualExam((ExamOfStudent) req.getRequestData(), client);

		}
			break;

		case "getSelectedExamData_byID": {
			/*--logic for createActive Exam && Edit exam--*/
			getSelectedExamData_byID((Exam) req.getRequestData(), client);

		}
			break;

		case "CheckIfActiveExamAlreadyExists": {
			CheckIfActiveExamAlreadyExists((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "createNewActiveExam": {
			createNewActiveExam((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "submitManualExam": {
			submitManualExam((MyFile) req.getRequestData(), client);

		}
			break;

		case "SaveEditExam": {
			SaveEditExam((Exam) req.getRequestData(), client);

		}
			break;

		case "getAllExamsStoredInSystem": {
			getAllExamsStoredInSystem(client);

		}
			break;

		case "lockActiveExam": {
			lockActiveExam((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "getStudentsInActiveExam": {
			getStudentsInActiveExam((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "InsertExamOfStudent": {
			InsertExamOfStudent((ExamOfStudent) req.getRequestData(), client);

		}
			break;

		case "getQuestionsByIDForEditExam": {
			getQuestionsByIDForEditExam((String) req.getRequestData(), client);
		}
			break;
		case "getAllQuestionsStoredInSystem":{
			getAllQuestionsStoredInSystem(client);
		}
			break;
		case "getQuestionDataBy_questionID":{
			getQuestionDataBy_questionID((String) req.getRequestData(), client);
		}
		

		}

	}

	/*------------------------------------Private Methods-------------------------------------------------*/



	/**
	 * @param requestData
	 * @param client
	 * 
	 *                    gets from the client question with teacher and profession.
	 *                    gets from the DB all relevant questions and sends it back
	 *                    to the client
	 */
	private void getQuestionBank(Question requestData, ConnectionToClient client) {
		try {
			client.sendToClient(dbController.getQuestionByProfessionAndTeacher(requestData));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getAllStudentsExams(ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("AllStudentsExams");
			Res.setResponseData(dbController.getAllStudentsExams());
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getStudentGrades(int studentID, ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Student Grades");
			Res.setResponseData(dbController.getStudentGrades(studentID));
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getTeachers(ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Calculate Grades Average");
			Res.setResponseData(dbController.getTeachers());
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getStudents(ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Get All Students");
			Res.setResponseData(dbController.getStudents());
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getCoursesByProfession(Profession requestData, ConnectionToClient client) {
		try {
			client.sendToClient(dbController.getCoursesByProfession(requestData));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gradesAverageCalc(String ExamID, ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Calculate Grades Average");
			Res.setResponseData(dbController.gradesAverageCalc(ExamID));
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Verify if activeExam exist at this time, set ActiveExam object if found.
	 * 
	 * @param activeExam
	 * @param client
	 */
	private void isActiveExamExist(ActiveExam activeExam, ConnectionToClient client) {
		// logic for 'EnterToExam'
		ResponseFromServer response = null;
		response = dbController.verifyActiveExam_byDate_and_Code(activeExam);
		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	private void getProfNames(ConnectionToClient client) {

		ResponseFromServer response = new ResponseFromServer("getProffesionsName");
		response.setResponseData(dbController.getProfNames());
		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	private void getCoursesNames(ConnectionToClient client) {

		ResponseFromServer response = new ResponseFromServer("getCoursesNames");
		response.setResponseData(dbController.getCoursesNames());
		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	private void chechExamExist(String ExamID, ConnectionToClient client) {

		ResponseFromServer response = new ResponseFromServer("CHECK EXAM EXIST");
		response.setResponseData(dbController.chechExamExist(ExamID));
		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	private void getAllActiveExamBeforEnterToExam(ConnectionToClient client) {
		// logic for 'EnterToExam'
		ResponseFromServer response = null;
		response = dbController.getAllActiveExam();
		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	/**
	 * @param requestData
	 * @param client
	 */
	private void clientDisconected(Object requestData, ConnectionToClient client) {
		if (requestData instanceof User) {
			User user = (User) requestData;
			serverFrame.printToTextArea("Client disconnected --->" + user.getUserType() + " logout.");
			UpdateUserLoggedOut(user, client);
		}

		else if (requestData.equals("ClientDisconected_from_login_fram")) {
			serverFrame.printToTextArea("---> Client Disconnected <---");
			ResponseFromServer respon = new ResponseFromServer("CLIENT DISCONECT !");
			try {
				client.sendToClient(respon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param str      is a message which displayed in server`s log.
	 * @param response contains the information that the server transmits to the
	 *                 client.
	 */
	private void printMessageInLogFramServer(String str, ResponseFromServer response) {
		serverFrame.printToTextArea("--->" + str + " " + response.toString());
	}

	//
	/**
	 * @param user   is who server needs to identify the details.
	 * @param client
	 */
	private void getUser(User user, ConnectionToClient client) {
		// logic of login
		ResponseFromServer respon = null;
		respon = dbController.verifyLoginUser(user);
		try {
			client.sendToClient(respon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", respon);// print to server log.

	}

	/**
	 * logic of login: update logged status after successfully LOGIN action.
	 * 
	 * @param user   who server needs to identify and update his details.
	 * @param client
	 */
	private void UpdateUserLoggedIn(User user, ConnectionToClient client) {
		user.setLogged(1); // set isLogged to 1.
		loggedInUsers.put(user.getId(), user); // add new user to hashMap of all the logged users.
		loogedClients.put(user.getId(), client); // add this client to HashMao by key: ID
		// Response:
		ResponseFromServer response = new ResponseFromServer("USER LOGIN !");
		System.out.println("print new list for DEBUG");
		printLoggedInUsersList(); // for DEBUG
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client: " + user.toString(), response);// print to server log.

	}

	/**
	 * logic of login: update logged status after LOGOUT action.
	 * 
	 * @param user   who server needs to identify and update his details.
	 * @param client
	 */
	private void UpdateUserLoggedOut(User user, ConnectionToClient client) {
		ResponseFromServer response = new ResponseFromServer("USER LOGOUT");
		loggedInUsers.remove(user.getId()); // remove this user from list.
		loogedClients.remove(user.getId());
		printLoggedInUsersList(); // for DEBUG- print current list.

		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client: " + user.getUserType(), response);// print to server log.

	}

	/**
	 * logic of login- gets student`s courses after successfully LOGIN action.
	 * 
	 * @param student who server needs to identify and get his all data.
	 * @param client
	 */
	private void getStudentData_Login(Student student, ConnectionToClient client) {
		student = dbController.getStudentData_Logged(student);
		student = dbController.getStudentCourses_Logged(student);
		// create ResponseFromServer (to client) with all student data.
		ResponseFromServer response = new ResponseFromServer("STUDENT DATA");
		response.setResponseData(student);
		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client: transferred the ", response);// print to server log.

	}

	/**
	 * @param teacher who server needs to identify and get his all data.
	 * @param client
	 */
	private void getTeacherData_Login(Teacher teacher, ConnectionToClient client) {
		// logic of login- gets teacher`s profession after successfully login action.
		ArrayList<String> professionIds = dbController.getTeacherProfessionIDs(teacher);
		ArrayList<Profession> teacherProfessions = new ArrayList<>();
		for (String id : professionIds) {
			teacherProfessions.add(dbController.getProfessionByID(id));
		}
		teacher.setProfessions(teacherProfessions);
		// create ResponseFromServer (to client) with all student data.
		ResponseFromServer response = new ResponseFromServer("TEACHER DATA");
		response.setResponseData(teacher);
		// sent to client.
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		printMessageInLogFramServer("Message to Client: transferred the ", response);// print to server log.

	}

	/**
	 * print all loggedIn users in hashMap list.
	 */
	private void printLoggedInUsersList() {
		// for(loggedInUsers log : )
		System.out.println("------\nPrint loggedInUsers list:");
		for (Integer user : loggedInUsers.keySet()) {
			String key = loggedInUsers.toString();
			System.out.println(key);
		}
		System.out.println("END LIST loggedInUsers\n------\n");

		System.out.println("**********\nPrint loogedClients list:");
		for (Integer id : loogedClients.keySet()) {
			String keyID = loogedClients.toString();
			System.out.println(keyID);
		}
		System.out.println("END LIST loogedClients\n*******\n");

	}

	/**
	 * @param questionData this method creates the question ID and then inserts the
	 *                     new question into the DB.
	 */
	private void createNewQuestion(Question questionData, ConnectionToClient client) {
		int numOfQuestions = dbController.getNumOfQuestionsInProfession(questionData.getProfession().getProfessionID());
		String questionID = String.valueOf(numOfQuestions + 1);
		questionID = ("000" + questionID).substring(questionID.length());
		questionID = questionData.getProfession().getProfessionID() + questionID;
		questionData.setQuestionID(questionID);
		ResponseFromServer res;
		if (dbController.createNewQuestion(questionData)) {
			res = new ResponseFromServer("SuccessCreateNewQuestion");
		} else {
			res = new ResponseFromServer("FailCreateNewQuestion");

		}
		try {
			client.sendToClient(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * TODO: add comment
	 * 
	 * @param examData
	 * @return
	 */
	private ResponseFromServer createNewExam(Exam examData) {
		// create the exam ID by number of exams in this profession and course
		int numOfExams = dbController.getNumOfExamsInCourse(examData.getCourse().getCourseID()) + 1;
		String examID = numOfExams < 10 ? "0" + String.valueOf(numOfExams) : String.valueOf(numOfExams);
		examID = examData.getProfession().getProfessionID() + examData.getCourse().getCourseID() + examID;
		examData.setExamID(examID);
		// create the new exam in DB
		if (!dbController.createNewExam(examData)) {
			ResponseFromServer res = new ResponseFromServer("Error creating new Exam");
			StatusMsg stat = new StatusMsg();
			stat.setStatus("ERROR");
			stat.setDescription("There was a problem with saving the new exam in DB!");
			res.setStatusMsg(stat);
		}
		// add questions and scores to DB
		if (!dbController.addQuestionsInExam(examID, examData.getExamQuestionsWithScores())) {
			// return error
			ResponseFromServer res = new ResponseFromServer("Error creating new Exam");
			StatusMsg stat = new StatusMsg();
			stat.setStatus("ERROR");
			stat.setDescription("There was a problem with saving the questions for new exam in DB!");
			res.setStatusMsg(stat);

		}
		ResponseFromServer res = new ResponseFromServer("Success Create New Exam");
		res.setResponseData(examData.getExamID());
		return res;
	}

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

	private void addTimeToExam(ActiveExam activeExam, ConnectionToClient client) {
		ResponseFromServer respon = dbController.verifyActiveExam((ActiveExam) activeExam);
		try {
			client.sendToClient(respon);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", respon);// print to server log.
	}

	private void createNewExtensionRequest(ExtensionRequest extensionRequest, ConnectionToClient client) {
		ResponseFromServer res;
		if (!dbController.checkIfExtensionRequestExists(extensionRequest))
			res = dbController.createNewExtensionRequest(extensionRequest);
		else
			res = new ResponseFromServer("EXTENSION REQUEST DIDN'T CREATED");
		try {
			client.sendToClient(res);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", res);// print to server log.
	}

	private void getExtensionRequests(ConnectionToClient client) {
		ResponseFromServer respon = new ResponseFromServer("EXTENSION REQUEST");
		try {
			respon.setResponseData(dbController.getExtensionsRequests());
			client.sendToClient(respon);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", respon);// print to server log.
	}

	private void approvalTimeExtension(ActiveExam activeExam, ConnectionToClient client) {
		// update time alloted for test in active exam after the principal approves the
		// request.
		ResponseFromServer respon = null;
		try {
			respon = dbController.setTimeForActiveTest(activeExam);
			client.sendToClient(respon);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", respon);// print to server log.
	}

	private void declineTimeExtension(ActiveExam activeExam, ConnectionToClient client) {
		ResponseFromServer respon = null;
		try {
			respon = dbController.deleteExtensionRequest(activeExam);
			client.sendToClient(respon);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", respon);// print to server log.
	}

	private void downloadManualExam(ExamOfStudent examOfStudent, ConnectionToClient client) {
		String fileName = examOfStudent.getActiveExam().getExam().getExamID() + "_exam.docx";
		MyFile exam = new MyFile(fileName);
		try {
			String path = new File("").getCanonicalPath();
			String LocalfilePath = path + "/files/" + fileName;
			File newFile = new File(LocalfilePath);
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			exam.initArray(mybytearray.length);
			exam.setSize(mybytearray.length);
			bis.read(exam.getMybytearray(), 0, mybytearray.length);
			fis.close();
			client.sendToClient(exam);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void submitManualExam(MyFile msg, ConnectionToClient client) {
		ResponseFromServer respon = new ResponseFromServer("SUBMIT EXAM");
		MyFile submitExam = (MyFile) msg;
		int fileSize = ((MyFile) msg).getSize();
		System.out.println("Message received: " + msg + " from " + client);
		System.out.println("length " + fileSize);
		try {
			String path = new File("").getCanonicalPath();
			String LocalfilePath = path + "/files/" + submitExam.getFileName();
			FileOutputStream fos = new FileOutputStream(LocalfilePath);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(submitExam.getMybytearray(), 0, fileSize);
			bos.flush();
			fos.flush();
			fos.close();
			client.sendToClient(respon);
		} catch (IOException ex) {
			respon = new ResponseFromServer("DIDNT SUBMIT EXAM");
			ex.printStackTrace();
		}

	}

	private void getSelectedExamData_byID(Exam exam, ConnectionToClient client) {
		ResponseFromServer response = null;
		response = dbController.getSelectedExamData_byID(exam);
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	private void CheckIfActiveExamAlreadyExists(ActiveExam activeExam, ConnectionToClient client) {
		// logic for 'createNewActiveExam'
		ActiveExam actExam = null;
		ResponseFromServer res = null;
		actExam = dbController.isActiveExamAlreadyExists(activeExam);

		// in case ExamCode is null the same examID not found, so create this active
		// exam is allowed.
		if (actExam.getExamCode() == null) {
			res = createResponse("CREATE ACTION ALLOWED", "Create action is allowed");
		}

		else {
			String str = "ACTIVE EXAM EXIST: " + activeExam.getExam().getExamID() + ", start time: "
					+ activeExam.getStartTime() + " Code: " + actExam.getExamCode();

			// check if they have the same start time AND code.
			/* actExam.getStartTime().equals(activeExam.getStartTime())&& */
			if (actExam.getExamCode().equals(activeExam.getExamCode())) {
				res = createResponse(str, "ACTIVE EXAM NOT ALLOWED");
			} else {
				// we allowed to perform different exams at the same time.
				// we allowed to perform the same exam but NOT in the same time
				res = createResponse(str, "Create action is allowed");
			}

		}

		// ----------------------

		try {
			client.sendToClient(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", res);

	}

	/**
	 * @param responseType
	 * @param Status
	 * @return Returns an answer according to inputs function.
	 */
	private ResponseFromServer createResponse(String responseType, String status) {
		ResponseFromServer response = null;
		response = new ResponseFromServer(responseType);
		response.getStatusMsg().setStatus(status);
		return response;
	}

	private void createNewActiveExam(ActiveExam newActiveExam, ConnectionToClient client) {
		ResponseFromServer response = dbController.createNewActiveExam(newActiveExam);
		Boolean ans = dbController.updateExamStatus(newActiveExam.getExam());
		if (ans) {
			response.getStatusMsg().setStatus("New active exam created successfully");
		}
		try {
			client.sendToClient(response);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);// print to server log.

	}

	private void getAllExamsStoredInSystem(ConnectionToClient client) {
		ResponseFromServer respond = new ResponseFromServer("ALL EXAMS");
		respond.setResponseData((ArrayList<Exam>) dbController.GetAllExams());
		try {
			client.sendToClient(respond);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void lockActiveExam(ActiveExam examToLock, ConnectionToClient client) {
		ResponseFromServer respon = new ResponseFromServer("EXAM LOCK");
		try {
			if (dbController.deleteActiveExam(examToLock)) {
				Boolean ans = dbController.updateExamStatus(examToLock.getExam());// hadar: update working
				respon.setResponseData((Boolean) false);
				if (respon.getStatusMsg().getStatus().equals("EXAM STATUS UPDATED"))
					respon.setResponseData((Boolean) true);
			}
			client.sendToClient(respon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getStudentsInActiveExam(ActiveExam activeExam, ConnectionToClient client) {
		ResponseFromServer respon = new ResponseFromServer("STUDENT IN ACTIVE EXAM");
		try {
			respon.setResponseData(dbController.getStudentsInActiveExam(activeExam));
			client.sendToClient(respon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", respon); // print to server log.
	}

	private void InsertExamOfStudent(ExamOfStudent examOfStudent, ConnectionToClient client) {
		/* logic for EnterToExam */
		ResponseFromServer response = null;
		response = dbController.InsertExamOfStudent(examOfStudent);
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);
	}

	private void SaveEditExam(Exam editExam, ConnectionToClient client) {
		/* logic for EditExam */
		ResponseFromServer response = null;
		response = dbController.SaveEditExam(editExam);
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	//TODO: CHECK
	private void getQuestionsByIDForEditExam(String examID, ConnectionToClient client) {
		/*logic for- EditExam _step2*/
		ResponseFromServer response = null; 
		ArrayList<QuestionInExam> questionIDList_InExam;
		HashMap<String, Question> allQuestionInExam;
		// HashMap<questionID, QuestionInExam>

		
		// Set<QuestionInExam> questionIDList_InExam = new HashSet<>();
		// Map<String, Set<QuestionInExam>> allQuestionInExam = new HashMap<>();
		// //Map<questionID, Set<QuestionInExam>>
		try {
			//DELETE
			questionIDList_InExam = (ArrayList<QuestionInExam>) dbController.getQuestionsID_byExamID(examID);
			allQuestionInExam = (HashMap<String, Question>) dbController.allQuestionInExam(questionIDList_InExam);
			if (allQuestionInExam != null) {
				response = new ResponseFromServer("All Question In ExamID: " + examID);
				response.setResponseData(allQuestionInExam);
			} else {
				response = new ResponseFromServer("NOT Found All Question In Exam");
			}

			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void getAllQuestionsStoredInSystem(ConnectionToClient client) {
		ResponseFromServer response = null;
		response=dbController.GetAllQuestions_ToQuestionsBank();
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);// print to server log.
		
	}
	
	
	
	private void getQuestionDataBy_questionID(String questionID, ConnectionToClient client) {
		ResponseFromServer response = new ResponseFromServer("Question Data");
		response.setResponseData((Question)dbController.getQuestionDataBy_questionID(questionID));
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);// print to server log.
		
	}
	
	
	
	
	
	
	
	
	//

}
