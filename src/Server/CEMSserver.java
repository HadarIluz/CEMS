// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import common.MyFile;
import entity.ActiveExam;
import entity.Exam;
import entity.ExamOfStudent;
import entity.ExamStatus;
import entity.ExtensionRequest;
import entity.Profession;
import entity.Question;
import entity.QuestionInExam;
import entity.ReasonOfSubmit;
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
		case "getTeachers": {
			getTeachers(client);
		}
			break;
		case "StudentView grade": {
			getStudentGrade(client, (String[]) req.getRequestData());
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
		case "downloadSolvedManualExam": {
			downloadSolvedManualExam((ExamOfStudent) req.getRequestData(), client);
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
		case "getAllExams": {
			getAllExams(client);
		}
			break;
		case "getStudents": {
			getStudents(client);
		}
			break;
		case "getStudentsByExamID": {
			getStudentsByExamID((String) req.getRequestData(), client);
		}
			break;
		case "getQuestions": {
			getQuestions((Integer) req.getRequestData(), client);
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

		case "createNewActiveExam": {
			createNewActiveExam((ActiveExam) req.getRequestData(), client);
		}
			break;

		case "EditQuestion": {
			EditQuestion((Question) req.getRequestData(), client);
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
		case "getSolvedComputerizedExam": {
			getSolvedComputerizedExam((String[]) req.getRequestData(), client);

		}
			break;

		case "getAnswersOfMistakeQuestion": {
			getAnswersOfMistakeQuestion((String) req.getRequestData(), client);

		}
			break;

		case "getAllExamsStoredInSystem": {
			getAllExamsStoredInSystem(client);

		}
			break;
		case "getStudentsInActiveExam": {
			getStudentsInActiveExam((Exam) req.getRequestData(), client);
		}
			break;

		case "InsertExamOfStudent": {
			InsertExamOfStudent((ExamOfStudent) req.getRequestData(), client);

		}
			break;

		case "StudentFinishManualExam": {
			StudentFinishManualExam((ExamOfStudent) req.getRequestData(), client);
		}
			break;

		case "getQuestionsByIDForEditExam": {
			getQuestionsByIDForEditExam((String) req.getRequestData(), client);
		}
			break;
		case "getAllQuestionsStoredInSystem": {
			getAllQuestionsStoredInSystem(client);
		}
			break;
		case "getQuestionDataBy_questionID": {
			getQuestionDataBy_questionID((String) req.getRequestData(), client);
		}
			break;
		case "getFullExamDetails": {
			getFullExamDetails((Exam) req.getRequestData(), client);
		}
			break;
		case "StudentFinishExam": {
			StudentFinishExam((ExamOfStudent) req.getRequestData(), client);
		}
			break;

		case "checkExam_of_student_NotExistsBeforeStartExam": {
			checkExam_of_student_NotExistsBeforeStartExam((ExamOfStudent) req.getRequestData(), client);
		}
			break;
		case "updateScoresOfEditExam": {
			updateScoresOfEditExam((ArrayList<QuestionInExam>) req.getRequestData(), client);
		}
			break;
		}
	}

	/*------------------------------------Private Methods-------------------------------------------------*/

	/**
	 * @param studentExam
	 * @param client      update that the student finished the manual exam
	 */
	private void StudentFinishManualExam(ExamOfStudent studentExam, ConnectionToClient client) {
		ResponseFromServer response = null;
		int notSubmitted = -1;
		if (dbController.updateStudentExam(studentExam)) {
			response = new ResponseFromServer("EXAM OF STUDENT UPDATE");
		}
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

		if (studentExam.getReasonOfSubmit() == ReasonOfSubmit.forced)
			notSubmitted = dbController.getNumberOfNotSubmitted(studentExam.getActiveExam().getExam().getExamID());

		if (notSubmitted == 0 || checkIfExamFinished(studentExam.getActiveExam())) {
			documentExam(studentExam.getActiveExam());
		}
	}

	/**
	 * @param studentExam
	 * @param client      inserts all student exam data to DB
	 */
	private void StudentFinishExam(ExamOfStudent studentExam, ConnectionToClient client) {
		ResponseFromServer res = null;
		int finaleScore = 100;
		for (QuestionInExam q : studentExam.getQuestionsAndAnswers().keySet()) {
			if (studentExam.getQuestionsAndAnswers().get(q) != q.getQuestion().getCorrectAnswerIndex()) {
				finaleScore -= q.getScore();
			}
		}
		studentExam.setScore(finaleScore);
		if (dbController.updateStudentExam(studentExam)) {
			if (dbController.insertStudentQuestions(studentExam)) {
				res = new ResponseFromServer("Success student finish exam");
			}
		} else {
			res = new ResponseFromServer("Fail student finish exam");
		}
		try {
			client.sendToClient(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (studentExam.getReasonOfSubmit() == ReasonOfSubmit.forced
				|| checkIfExamFinished(studentExam.getActiveExam())) {
			documentExam(studentExam.getActiveExam());
			checkForCopying(studentExam.getActiveExam());
		}
	}

	private void EditQuestion(Question question, ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Edit Question Update");
			Res.setResponseData(dbController.EditQuestion((Question) question));
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getAllExams(ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Edit Question Update");
			Res.setResponseData(dbController.getAllExams());
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param activeExam
	 * @return true or false if the exam is finished a finished exam is 30 minutes
	 *         after start time & all students submitted (forced ot initiated)
	 */
	private boolean checkIfExamFinished(ActiveExam activeExam) {
		// first check if it's after half an hour of beginning of exam
		Time time = dbController.getStartTimeOfActiveExam(activeExam.getExam().getExamID());
		if (time == null) {
			printMessageInLogFramServer("There was a problem getting the start time");
			return false;
		}
		activeExam.setStartTime(time);
		LocalTime halfHourAfterStart = time.toLocalTime().plusMinutes(30);
		LocalTime currentTime = (new Time(System.currentTimeMillis())).toLocalTime();
		
		if (currentTime.compareTo(halfHourAfterStart) >= 0 ) {
			// it's half an hour past the starting time of the exam
			// now check if some students are not done
			int notSubmitted = dbController.getNumberOfNotSubmitted(activeExam.getExam().getExamID());
			if (notSubmitted == 0)
				return true;
		}
		return false;
	}

	/**
	 * @param activeExam this method documents a finished exam in the db
	 */
	private void documentExam(ActiveExam activeExam) {
		if (dbController.activeExamExists(activeExam)) {
			// delete the active exam and document it
			if (!dbController.deleteActiveExam(activeExam)) {
				printMessageInLogFramServer("There was a problem with deleteing the active exam");
			}
			activeExam.getExam().setExamStatus(ExamStatus.inActive);
			if (!dbController.updateExamStatus(activeExam.getExam())) {
				printMessageInLogFramServer("There was a problem with update exam status");
			}

			if (dbController.documentExam(activeExam)) { // enter all relavent data to record_exam table
				printMessageInLogFramServer("document exam suceeded");
			} else
				printMessageInLogFramServer("document exam failed");
		}
	}

	/**
	 * @param exam
	 * @param client
	 * 
	 *               gets from the db details of exam for student to solve: exam
	 *               comments, all questions and scores
	 */
	private void getFullExamDetails(Exam exam, ConnectionToClient client) {
		exam = dbController.getCommentForStudents(exam);
		ArrayList<QuestionInExam> questionsList = dbController.getQuestionsOfExam(exam.getExamID());
		for (QuestionInExam q : questionsList) {
			q.setQuestion(dbController.getFullQuestion(q.getQuestion().getQuestionID()));
		}
		exam.setExamQuestionsWithScores(questionsList);
		ResponseFromServer res = new ResponseFromServer("FullExam");
		res.setResponseData(exam);

		try {
			client.sendToClient(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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

	private void getSolvedComputerizedExam(String[] requestData, ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Solved Computerized Exam");
			Res.setResponseData(dbController.getSolvedComputerizedExam(requestData));
			client.sendToClient(Res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getAnswersOfMistakeQuestion(String questionID, ConnectionToClient client) {
		try {
			ResponseFromServer Res = new ResponseFromServer("Answer For Mistake Question");
			Res.setResponseData(dbController.correctAnswerForQuestion(questionID));
			client.sendToClient(Res);
		} catch (IOException e) {
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

	private void getStudentGrade(ConnectionToClient client, String[] requestData) {
		try {
			ResponseFromServer Res = new ResponseFromServer("StudentScore");
			Res.setResponseData((ArrayList<String>) dbController.getStudentScore(requestData));
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
	 * @param activeExam get all details about solutions of students for an exam,
	 *                   and check if suspected for copying
	 */
	private void checkForCopying(ActiveExam activeExam) {
		ArrayList<ExamOfStudent> examsOfStudent = dbController.getExamsOfStudentsByExamID(activeExam);
		for (ExamOfStudent e : examsOfStudent) {
			e.setQuestionsAndAnswers(dbController.getQuestionsAndAnswersByExamOfStudent(e));
		}
		ArrayList<Integer> suspectStudentID = dbController.getPotentialCopyList(examsOfStudent);

		int teacherID = dbController.getTeacherOfExam(activeExam.getExam());
		// send notification to teacher
		ResponseFromServer res = new ResponseFromServer("NOTIFICATION_TEACHER_POTENTIAL_COPY");
		res.setResponseData(suspectStudentID);
		try {
			loogedClients.get(teacherID).sendToClient(res);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

//	private void CheckSameMistakeOfStudent(ArrayList<ExamOfStudent> exams, ConnectionToClient client) {
//		try {
//			ResponseFromServer Res = new ResponseFromServer("Check Copy of Exam");
//			Res.setResponseData(dbController.getPotentialCopyList(exams));
//			client.sendToClient(Res);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

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

	/**
	 * @param str is a message which displayed in server`s log.f
	 */
	private void printMessageInLogFramServer(String str) {
		serverFrame.printToTextArea("--->" + str + " ");
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
	 * 
	 * @param examData
	 * @return a response to the client this method gets all details of a new exam
	 *         and inserts it to the DB
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
		// in case of computerized exam
		// add questions and scores to DB
		if (examData.getActiveExamType().equals("computerized")){

			if (!dbController.addQuestionsInExam(examID, examData.getExamQuestionsWithScores())) {
				// return error
				ResponseFromServer res = new ResponseFromServer("Error creating new Exam");
				StatusMsg stat = new StatusMsg();
				stat.setStatus("ERROR");
				stat.setDescription("There was a problem with saving the questions for new exam in DB!");
				res.setStatusMsg(stat);
			}

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
		ResponseFromServer responForTeacher = dbController.verifyActiveExam((ActiveExam) activeExam);
		ResponseFromServer responForPrincipal = new ResponseFromServer("NOTIFICATION_PRINCIPAL_REQUEST_RECEIVED");
		int principalID = dbController.getPrincipalId();
		responForPrincipal.setResponseData(activeExam.getExam().getExamID());
		try {
			client.sendToClient(responForTeacher);
			(loogedClients.get(principalID)).sendToClient(responForPrincipal);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", responForTeacher);// print to server log.
	}

	private void createNewExtensionRequest(ExtensionRequest extensionRequest, ConnectionToClient client) {
		ResponseFromServer res;
		if (!dbController.checkIfExtensionRequestExists(extensionRequest)) {
			res = dbController.createNewExtensionRequest(extensionRequest);
		} else
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
		ArrayList<Integer> students = dbController.getStudentsInActiveExam(activeExam.getExam());
		int teacherID = dbController.getTeacherOfExam(activeExam.getExam()); //to check
		ResponseFromServer responForPrincipal = null;
		ResponseFromServer responForStudent = new ResponseFromServer("NOTIFICATION_STUDENT_ADDED_TIME");
		ResponseFromServer responForTeacher = new ResponseFromServer("NOTIFICATION_TEACHER_REQUEST_APPROVED");
		responForTeacher.setResponseData(activeExam.getExam().getExamID());
		responForStudent.setResponseData((ActiveExam) activeExam);
		try {
			responForPrincipal = dbController.setTimeForActiveTest(activeExam);
			client.sendToClient(responForPrincipal);
			(loogedClients.get(teacherID)).sendToClient(responForTeacher);
			for (Integer id : students) {
				(loogedClients.get(id)).sendToClient(responForStudent);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", responForPrincipal);// print to server log.
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
		} catch (IOException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void downloadSolvedManualExam(ExamOfStudent examOfStudent, ConnectionToClient client) {
		String fileName = examOfStudent.getActiveExam().getExam().getExamID() + "_" + examOfStudent.getStudent().getId()
				+ ".docx";
		ResponseFromServer respon = new ResponseFromServer("Download Solved EXAM");
		MyFile exam = new MyFile(fileName);
		try {
			String path = new File("").getCanonicalPath();
			String LocalfilePath = path + "/files/" + fileName;
			File newFile = new File(LocalfilePath);
			if (!newFile.exists()) {
				respon.setResponseData("Download Failed");
				client.sendToClient(respon);
			} else {
				byte[] mybytearray = new byte[(int) newFile.length()];
				FileInputStream fis = new FileInputStream(newFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				exam.initArray(mybytearray.length);
				exam.setSize(mybytearray.length);
				bis.read(exam.getMybytearray(), 0, mybytearray.length);
				fis.close();
				client.sendToClient(exam);
			}
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

	private void getStudentsInActiveExam(Exam exam, ConnectionToClient client) {
		ResponseFromServer responForStudents = new ResponseFromServer("NOTIFICATION_STUDENT_EXAM_LOCKED");
		responForStudents.setResponseData(exam);
		ResponseFromServer responForTeacher = null;
		ArrayList<Integer> students = dbController.getStudentsInActiveExam(exam);
		try {
			if (students.isEmpty()) {
				ActiveExam activeExam = new ActiveExam(exam);
				ResponseFromServer respon = dbController.verifyActiveExam((ActiveExam) activeExam);
				activeExam = (ActiveExam) respon.getResponseData();
				documentExam(activeExam);
				responForTeacher = new ResponseFromServer("EXAM LOCKED");
			} else {
				for (Integer id : students) {
					(loogedClients.get(id)).sendToClient(responForStudents);
					responForTeacher = new ResponseFromServer("EXAM LOCKED");
				}
			}
			client.sendToClient(responForTeacher);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", responForStudents); // print to server log.
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

	// TODO: CHECK----->DELETE AND TACK FROM YUVAL
	private void getQuestionsByIDForEditExam(String examID, ConnectionToClient client) {
		/* logic for- EditExam _step2 */
		ResponseFromServer response = null;
		ArrayList<QuestionInExam> questionIDList_InExam;
		HashMap<String, Question> allQuestionInExam;
		// HashMap<questionID, QuestionInExam>

		// Set<QuestionInExam> questionIDList_InExam = new HashSet<>();
		// Map<String, Set<QuestionInExam>> allQuestionInExam = new HashMap<>();
		// //Map<questionID, Set<QuestionInExam>>
		try {
			// DELETE
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
		response = dbController.GetAllQuestions_ToQuestionsBank();
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);// print to server log.

	}

	private void getQuestionDataBy_questionID(String questionID, ConnectionToClient client) {
		ResponseFromServer response = new ResponseFromServer("Question Data");
		response.setResponseData((Question) dbController.getQuestionDataBy_questionID(questionID));
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);// print to server log.

	}

	private void checkExam_of_student_NotExistsBeforeStartExam(ExamOfStudent examOfStudent, ConnectionToClient client) {
		ResponseFromServer response = null;
		if (dbController.verifyExamOfStudentByExamID(examOfStudent)) {
			response = new ResponseFromServer("exam_of_student_allowed");
		} else {
			response = new ResponseFromServer("Exam_of_student Already Exists");
		}
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);// print to server log.
	}

	private void getStudentsByExamID(String requestData, ConnectionToClient client) {
		ResponseFromServer response = new ResponseFromServer("SCORE APPROVAL");
		try {
			response.setResponseData(dbController.SetDetailsForScoreApprovel(requestData));
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	private void getQuestions(Integer requestData, ConnectionToClient client) {
		ResponseFromServer response = new ResponseFromServer("TEACHER QUESTIONS");
		try {
			response.setResponseData(dbController.GetTeacherQuestions(requestData));
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

	private void updateScoresOfEditExam(ArrayList<QuestionInExam> updatedQuestions, ConnectionToClient client) {
		/* logic for EditExam */
		ResponseFromServer response = null;
		response = dbController.updateScoresOfEditExam(updatedQuestions);
		try {
			client.sendToClient(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		printMessageInLogFramServer("Message to Client:", response);

	}

}
