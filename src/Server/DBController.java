package Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import client.CEMSClient;
import client.ClientUI;
import common.MyFile;
import entity.ActiveExam;
import entity.Course;
import entity.Exam;
import entity.ExamOfStudent;
import entity.ExamStatus;
import entity.ExtensionRequest;
import entity.Profession;
import entity.ProfessionCourseName;
import entity.Question;
import entity.QuestionInExam;
import entity.QuestionRow;
import entity.Student;
import entity.Teacher;
import entity.UpdateScoreRequest;
import entity.User;
import entity.UserType;
import gui_server.ServerFrameController;
import logic.RequestToServer;
import logic.ResponseFromServer;
import logic.StatusMsg;

/**
 * @author CEMS_Team
 *
 */
public class DBController {
	public Connection conn;
	public ServerFrameController serverFrame;

	public void connectDB(ServerFrameController serverFrame) {
		try {
			this.serverFrame = serverFrame;
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			serverFrame.printToTextArea("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			serverFrame.printToTextArea("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cems?serverTimezone=IST", "root",
					"Aa123456");
			serverFrame.printToTextArea("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			serverFrame.printToTextArea("SQLState: " + ex.getSQLState());
			serverFrame.printToTextArea("VendorError: " + ex.getErrorCode());
		}
	}

	/*
	 * checks if the user that try to login exists in the DB.
	 * 
	 * @param obj of user which include student id to verify if exists.
	 */
	public ResponseFromServer verifyLoginUser(User obj) {

		User existUser = obj;
		ResponseFromServer response = null;
		existUser.setPassword(null); // put null in order to check at the end if user found or not by this id.
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			pstmt.setInt(1, existUser.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existUser.setPassword(rs.getString(2));
				existUser.setFirstName(rs.getString(3));
				existUser.setLastName(rs.getString(4));
				existUser.setEmail(rs.getString(5));
				existUser.setUserType(UserType.valueOf(rs.getString(6)));
				rs.close();
			}

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		// in case not found any user match,
		// response ready to client with StatusMsg and
		// 'Object responseData', in case user found existUser include all data,
		// otherwise null.
		if (existUser.getPassword() != null) {
			response = new ResponseFromServer("USER FOUND IN CEMS SYSTEM");
			response.getStatusMsg().setStatus("USER FOUND");
		} else {
			response = new ResponseFromServer("USER NOT FOUND IN CEMS SYSTEM");
			response.getStatusMsg().setStatus("USER NOT FOUND");
		}
		response.setResponseData(existUser);
		return response;
	}

	/**
	 * We know that this student exists and we bring the additional data he has in
	 * addition to the user.
	 * 
	 * @param student include all data of student that hold by User object.
	 */
	public Student getStudentData_Logged(Student student) {

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT AVG FROM student WHERE id=?;");
			pstmt.setInt(1, student.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				student.setStudentAvg(rs.getFloat(1));
				rs.close();
			}

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		return student;

	}

	/**
	 * @param student include all data of this logged student.
	 */
	public Student getStudentCourses_Logged(Student student) {

		ArrayList<Course> coursesOfStudent = new ArrayList<Course>();
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM student_in_course WHERE student=?;");
			pstmt.setInt(1, student.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course newCourse = new Course(rs.getString(2));
				coursesOfStudent.add(newCourse); // add to list.
			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		student.setCourses(coursesOfStudent);
		return student;
	}

	/**
	 * @param teacherObj include all data of this logged teacher.
	 */
	public ArrayList<String> getTeacherProfessionIDs(Teacher teacher) {

		ArrayList<String> professionIDs = new ArrayList<String>();

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT profession FROM teacher_in_profession WHERE teacher=?;");
			pstmt.setInt(1, teacher.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				professionIDs.add(rs.getString(1)); // add to list
			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return professionIDs;
	}

	public String UpdateScoreOfStudent(UpdateScoreRequest req) {

		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(
					"UPDATE exam_of_student SET score = ?,updateReason=? WHERE student=? AND exam=?;");
			pstmt.setInt(1, req.getUpdatedScore());
			pstmt.setString(2, req.getReasonOfUpdate());
			pstmt.setString(3, req.getStudentID());
			pstmt.setString(4, req.getExamID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			return "FALSE";
		}
		return "TRUE";
	}

	public HashMap<String, Integer> SetDetailsForScoreApprovel(String examID) {
		HashMap<String, Integer> stdScore = new HashMap<>();

		PreparedStatement pstmt;
		try {

			pstmt = conn.prepareStatement("SELECT * FROM exam_of_student  WHERE exam=?");
			pstmt.setString(1, examID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				stdScore.put(rs.getString(2), rs.getInt(5));
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return stdScore;

	}

	/**
	 * @param question inserts new question to DB
	 * @return
	 */
	public boolean createNewQuestion(Question question) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("INSERT INTO question VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			pstmt.setInt(1, question.getTeacher().getId());
			pstmt.setString(2, question.getQuestionID());
			pstmt.setString(3, question.getProfession().getProfessionID());
			pstmt.setString(4, question.getQuestion());
			pstmt.setString(5, question.getAnswers()[0]);
			pstmt.setString(6, question.getAnswers()[1]);
			pstmt.setString(7, question.getAnswers()[2]);
			pstmt.setString(8, question.getAnswers()[3]);
			pstmt.setInt(9, question.getCorrectAnswerIndex());
			pstmt.setString(10, question.getDescription());

			if (pstmt.executeUpdate() != 0) {
				return true;
			}
			// to do something with status
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * @param professionID
	 * @return int, the number of questions in this profession
	 */
	public int getNumOfQuestionsInProfession(String professionID) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT SUM(profession=?) as sum FROM question;");
			pstmt.setString(1, professionID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int x = rs.getInt(1);
				return x;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * @param courseID
	 * @return the number of exams with this courseID
	 */
	public int getNumOfExamsInCourse(String courseID) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT SUM(course=?) as sum FROM exam;");
			pstmt.setString(1, courseID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int x = rs.getInt(1);
				return x;
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * @param exam
	 * @return true/false if creating a new exam in DB succeeded
	 */
	public boolean createNewExam(Exam exam) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("INSERT INTO exam VALUES(?, ?, ?, ?, ?, ?, ?);");
			pstmt.setString(1, exam.getExamID());
			pstmt.setString(2, exam.getProfession().getProfessionID());
			pstmt.setString(3, exam.getCourse().getCourseID());
			pstmt.setInt(4, exam.getTimeOfExam());
			pstmt.setString(5, exam.getCommentForTeacher());
			pstmt.setString(6, exam.getCommentForStudents());
			pstmt.setInt(7, exam.getAuthor().getId());

			if (pstmt.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	/**
	 * @param examID
	 * @param arrayList
	 * @return true/false if inserting all questions and scores of exam with examID
	 *         into table question_in_exam succeeded
	 */
	public boolean addQuestionsInExam(String examID, ArrayList<QuestionInExam> examQuestionsWithScores) {
		PreparedStatement pstmt;
		try {
			for (QuestionInExam q : examQuestionsWithScores) {
				pstmt = conn.prepareStatement("INSERT INTO question_in_exam VALUES(?, ?, ?);");
				pstmt.setString(1, q.getQuestion().getQuestionID());
				pstmt.setInt(2, q.getScore());
				pstmt.setString(3, examID);
				if (pstmt.executeUpdate() != 1) {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * check if the activeExam exist in the DB
	 * 
	 * @param obj of ActiveExam which include exam to verify if exists.
	 */
	public ResponseFromServer verifyActiveExam(Object obj) {
		ActiveExam existActiveExam = (ActiveExam) obj;
		ResponseFromServer response = null;
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM active_exam WHERE exam=?");
			pstmt.setString(1, existActiveExam.getExam().getExamID());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				existActiveExam.setStartTime(rs.getTime(2));
				existActiveExam.setTimeAllotedForTest(rs.getString(3));
				existActiveExam.setExamCode(rs.getString(4));
				rs.close();
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		// in case not found any active exam match.
		if (existActiveExam.getExamCode() == null) {
			response = new ResponseFromServer("ACTIVE EXAM NOT FOUND");
		} else
			response = new ResponseFromServer("ACTIVE EXAM FOUND");
		// ResponseFromServer class ready to client with StatusMsg and
		// 'Object responseData', in case active exam found existActiveExam include all
		// data, other null.
		response.setResponseData(existActiveExam);
		return response;
	}

	/**
	 * 
	 * @param extensionRequest
	 * @return true if creating a new extension request in DB succeeded, else return
	 *         false
	 */
	public ResponseFromServer createNewExtensionRequest(ExtensionRequest extensionRequest) {
		PreparedStatement pstmt;
		ResponseFromServer response = null;

		try {
			pstmt = conn.prepareStatement("INSERT INTO extension_request VALUES(?, ?, ?);");
			pstmt.setString(1, extensionRequest.getActiveExam().getExam().getExamID());
			pstmt.setString(2, extensionRequest.getAdditionalTime());
			pstmt.setString(3, extensionRequest.getReason());
			if (pstmt.executeUpdate() == 1) {
				response = new ResponseFromServer("EXTENSION REQUEST CREATED");
			} else {
				response = new ResponseFromServer("EXTENSION REQUEST DIDNT CREATED");
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return response;
	}

	public ArrayList<Exam> GetTeacherExams(Object obj) {

		int ID;

		ArrayList<Exam> examsOfTeacher = new ArrayList<Exam>();

		ID = (Integer) obj;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM exam WHERE author=?");

			pstmt.setInt(1, ID);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Exam exam = new Exam(rs.getString(1));
				exam.setProfession(new Profession(rs.getString(2)));
				exam.setCourse(new Course(rs.getString(3)));// addition
				exam.setTimeOfExam(Integer.parseInt(rs.getString(4)));
				examsOfTeacher.add(exam);

			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return examsOfTeacher;

	}

	public Boolean DeleteQuestion(Question question) {
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("DELETE FROM question WHERE questionID=? AND teacher=?");
			pstmt.setString(1, question.getQuestionID());
			pstmt.setInt(2, question.getTeacher().getId());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			return false;
		}
		return true;

	}

	public ArrayList<QuestionRow> GetTeacherQuestions(Object obj) {

		Teacher teacher;
		int id;

		ArrayList<QuestionRow> examsOfTeacher = new ArrayList<QuestionRow>();

		id = (Integer) obj;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM question WHERE teacher=?");

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				QuestionRow newQuestion = new QuestionRow();
				newQuestion.setQuestionID(rs.getString(2));
				newQuestion.setProfession(rs.getString(3));
				newQuestion.setQuestion(rs.getString(4));
				examsOfTeacher.add(newQuestion);
			}
			rs.close();

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return examsOfTeacher;// return null if no exsiting tests

	}

	/**
	 * @param activeExam
	 * @return true if the additional Time for activeExam has been updated at table
	 *         active_exam in DB. else, return false
	 */
	public boolean setTimeForActiveTest(ActiveExam activeExam) {
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement("UPDATE active_exam SET timeAllotedForTest=? WHERE exam=?");
			pstmt.setInt(1, activeExam.getTimeAllotedForTest());
			pstmt.setString(2, activeExam.getExam().getExamID());
			if (pstmt.executeUpdate() == 1)
				return true;
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param activeExam
	 * @return true if deleting request for activeExam from table active_exam in DB
	 *         succeeded, else return false
	 */
	public Boolean deleteExtenxtionRequest(ActiveExam activeExam) {
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("DELETE FROM extension_request WHERE exam=?");
			pstmt.setString(1, activeExam.getExam().getExamID());
			if (pstmt.executeUpdate() == 1)
				return true;
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * @param activeExam object which include 2 parameters of date and examcode for
	 *                   Query.
	 */
	public ResponseFromServer verifyActiveExam_byDate_and_Code(ActiveExam activeExam) {
		Exam exam = new Exam();// TODO:i remove null- need to check
		ResponseFromServer response = null;
		/*** EnterToExam ***/
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(
					"SELECT exam, timeAllotedForTest, examType FROM active_exam WHERE examCode=? and startTime>=? and startTime<?;");
			pstmt.setString(1, activeExam.getExamCode());
			pstmt.setTime(2, activeExam.getStartTime());
			pstmt.setTime(3, activeExam.getEndTimeToTakeExam());
			// Time Range for start the exam:
			System.out.println(activeExam.getStartTime() + " - " + activeExam.getEndTimeToTakeExam());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				exam.setExamID(rs.getString(1));
				activeExam.setExam(exam);
				activeExam.setTimeAllotedForTest(rs.getString(2));
				activeExam.setActiveExamType(rs.getString(3));
				rs.close();
			}

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		if (activeExam.getActiveExamType() == null) {
			response = new ResponseFromServer("ACTIVE EXAM_NOT_EXIST");

		} else {
			response = new ResponseFromServer("ACTIVE EXAM EXIST");
			response.setResponseData(activeExam);
		}
		return response;

	}

	public Profession getProfessionByID(String id) {
		Profession p = new Profession(id);
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT professionName FROM profession WHERE professionID=?;");
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				p.setProfessionName(rs.getString(1));
				rs.close();
			}

			// TODO: remove boolean return Exam object.
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return p;
	}

	/**
	 * @return Returns a list of all the Extensions Requests in the database
	 */
	public ArrayList<ExtensionRequest> getExtensionsRequests() {
		ArrayList<ExtensionRequest> extensionRequestsList = new ArrayList<ExtensionRequest>();
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM extension_request ;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Exam exam = new Exam(); // i remove null- need to check
				ActiveExam activeExam = new ActiveExam(exam);
				ExtensionRequest extensionRequest = new ExtensionRequest(activeExam);
				exam.setExamID(rs.getString(1));
				activeExam.setExam(exam);
				extensionRequest.setActiveExam(activeExam);
				extensionRequest.setAdditionalTime(rs.getString(2));
				extensionRequest.setReason(rs.getString(3));
				extensionRequestsList.add(extensionRequest);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		for (ExtensionRequest ex : extensionRequestsList) {
			ex.setActiveExam(getActiveExam(ex.getActiveExam()));
		}
		return extensionRequestsList;
	}

	/**
	 * @param An active exam that is initialized with a exam only
	 * @return Initializes the rest of the fields of an active exam and returns it
	 */
	public ActiveExam getActiveExam(ActiveExam activeExam) {

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM active_exam WHERE exam=?");
			pstmt.setString(1, activeExam.getExam().getExamID());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				activeExam.setStartTime(rs.getTime(2));
				activeExam.setTimeAllotedForTest(rs.getString(3));
				activeExam.setExamCode(rs.getString(4));
				rs.close();
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		// in case not found any active exam match.
		if (activeExam.getExamCode() == null) {
			deleteExtenxtionRequest(activeExam);
		}
		return activeExam;
	}

	public boolean DeleteExam(Exam exam) {

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("DELETE FROM exam WHERE examID=? AND profession=? AND course=?");
			pstmt.setString(1, exam.getExamID());
			pstmt.setString(2, exam.getProfName());
			pstmt.setString(3, exam.getCourse().getCourseName());// need to be courseID
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public ResponseFromServer getAllActiveExam() {

		ArrayList<ActiveExam> activeExamList = new ArrayList<ActiveExam>();
		ResponseFromServer response = null;
		/*** EnterToExam ***/
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM active_exam;");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Exam exam = new Exam();
				exam.setExamID(rs.getString(1));
				ActiveExam activeExam = new ActiveExam(exam);
				activeExam.setExam(exam);
				activeExam.setStartTime(rs.getTime(2));
				activeExam.setTimeAllotedForTest(rs.getString(3));
				activeExam.setExamCode(rs.getString(4));
				activeExam.setActiveExamType(rs.getString(5));
				activeExamList.add(activeExam);
			}
			rs.close();
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		response = new ResponseFromServer("ACTIVE EXAMS FOUND");
		response.setResponseData(activeExamList);

		return response;
	}

	public ResponseFromServer getQuestionByProfessionAndTeacher(Question requestData) {
		ArrayList<Question> qList = new ArrayList<Question>();
		ResponseFromServer response = null;
		/*** EnterToExam ***/
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM cems.question WHERE teacher=? AND profession=?;");
			pstmt.setString(1, String.valueOf(requestData.getTeacher().getId()));
			pstmt.setString(2, requestData.getProfession().getProfessionID());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Question q = new Question();
				q.setQuestionID(rs.getString(2));
				q.setQuestion(rs.getString(4));
				String[] answers = new String[4];
				answers[0] = rs.getString(5);
				answers[1] = rs.getString(6);
				answers[2] = rs.getString(7);
				answers[3] = rs.getString(8);
				q.setAnswers(answers);
				q.setCorrectAnswerIndex(rs.getInt(9));
				q.setDescription(rs.getString(10));

				qList.add(q);
			}
			rs.close();
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		if (qList.size() > 0) {
			response = new ResponseFromServer("Question bank FOUND");
			response.setResponseData(qList);
		} else {
			response = new ResponseFromServer("No Question Bank");
		}
		return response;
	}

	public ResponseFromServer getCoursesByProfession(Profession requestData) {
		ArrayList<Course> cList = new ArrayList<Course>();
		ResponseFromServer response = null;
		/*** EnterToExam ***/
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT courseID, CourseName FROM cems.course WHERE profession=?;");
			pstmt.setString(1, requestData.getProfessionID());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getString(1));
				c.setCourseName(rs.getString(2));
				cList.add(c);
			}
			rs.close();
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		if (cList.size() > 0) {
			response = new ResponseFromServer("Courses FOUND");
			response.setResponseData(cList);
		} else {
			response = new ResponseFromServer("No courses");
		}
		return response;
	}

	public HashMap<String, String> getProfNames() {
		HashMap<String, String> profName = new HashMap<String, String>();

		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM profession;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				profName.put(rs.getString(1), rs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profName;
	}

	public HashMap<String, ProfessionCourseName> getCoursesNames() {
		HashMap<String, ProfessionCourseName> courseMap = new HashMap<String, ProfessionCourseName>();
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM course;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String profID = rs.getString(1);
				String courseID = rs.getString(2);
				String courseName = rs.getString(3);

				// create new profession
				if (!courseMap.containsKey(profID)) {
					ProfessionCourseName pcn = new ProfessionCourseName();
					HashMap<String, String> courses = new HashMap<String, String>();
					pcn.setProfessionID(profID);
					courses.put(courseID, courseName);
					pcn.setCourses(courses);
					courseMap.put(profID, pcn);
				}
				// add new course to existing profession
				else {
					courseMap.get(profID).getCourses().put(courseID, courseName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return courseMap;
	}

	public String chechExamExist(String ExamID) {
		String answer = null;
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM exam WHERE examID=?;");
			pstmt.setString(1, ExamID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			if (rs.first() == false)
				return "FALSE";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "TRUE";
	}

	public ArrayList<Integer> gradesAverageCalc(String examID) {
		ArrayList<Integer> grades = new ArrayList<Integer>();
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("SELECT score FROM exam_of_student WHERE exam=?;");
			pstmt.setString(1, examID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				grades.add(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grades;
	}

	public ResponseFromServer getSelectedExamData_byID(Exam exam) {
		ResponseFromServer response = null;
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM exam WHERE examID=?");
			pstmt.setInt(1, Integer.parseInt(exam.getExamID()));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {

				Profession profession = new Profession(null);
				profession.setProfessionID(rs.getString(2));
				exam.setProfession(profession);

				Course course = new Course(null);
				course.setCourseID(rs.getString(3));
				exam.setCourse(course);

				exam.setTimeOfExam(Integer.parseInt(rs.getString(4)));
				exam.setCommentForTeacher(rs.getString(5));
				exam.setCommentForStudents(rs.getString(6));
				exam.setExamStatus((ExamStatus) rs.getObject(8));

				rs.close();
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		response = new ResponseFromServer("EXAM DATA");
		response.setResponseData(exam);

		return response;
	}

	public ActiveExam isActiveExamAlreadyExists(ActiveExam activeExam) {
		/*** CheckBeforeCreateNewActiveExam ***/
		ActiveExam acExam = new ActiveExam(activeExam.getExam(), activeExam.getStartTime());
		// FIXME: time problem with SQL.
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(
					"SELECT examCode, timeAllotedForTest FROM active_exam WHERE exam=? and startTime=?;");
			pstmt.setString(1, activeExam.getExam().getExamID());
			pstmt.setTime(2, activeExam.getStartTime());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				acExam.setExamCode(rs.getString(1));
				acExam.setTimeAllotedForTest(rs.getString(2));
				rs.close();
			}

		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}

		return acExam;
	}

	public ResponseFromServer createNewActiveExam(ActiveExam newActiveExam) {
		ResponseFromServer response = null;
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement("INSERT INTO active_exam VALUES(?, ?, ?, ?, ?);");
			
			pstmt.setString(1, newActiveExam.getExam().getExamID());
			pstmt.setTime(2, newActiveExam.getStartTime());
			pstmt.setInt(3, newActiveExam.getTimeAllotedForTest());
			pstmt.setString(4, newActiveExam.getExamCode());
			pstmt.setString(5, newActiveExam.getActiveExamType());
			if (pstmt.executeUpdate() != 0) {
				response = new ResponseFromServer("NEW ACTIVE EXAM CREATED");
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return response;

	}

	public ArrayList<Exam> GetAllExams() {
		ArrayList<Exam> examsList = new ArrayList<Exam>();
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM exam");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Profession profession = new Profession(null);
				profession.setProfessionID(rs.getString(2));

//				Course course = new Course(null);
//				course.setCourseID(rs.getString(3));
				// exam.setCourse(new Course(rs.getString(3)));// addition

				Exam exam = new Exam(rs.getString(1), profession, new Course(rs.getString(3)),
						Integer.parseInt(rs.getString(4)));
				examsList.add(exam);
				// --------------------

//				Exam exam = new Exam();
//				exam.setExamID(rs.getString(1));
//				Profession profession= new Profession(rs.getString(2));
//				exam.setProfession(profession);
//				Course course = new Course(rs.getString(3));
//				exam.setCourse(course);
//				exam.setTimeOfExam(Integer.parseInt(rs.getString(4)));		
//				examsList.add(exam);
			}
			rs.close();
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return examsList;

	}

	/**
	 * @param
	 * @return true if succeed to save edit exam values, return false if not.
	 * 
	 */
	public boolean editExamSave(Exam exam) {
		// Message from Lior: still not working & need to understand if need to save
		// changes in questions (
		// that delete in the next screen)
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(
					"UPDATE exam SET timeAllotedForTest=?, commentForTeacher=?, commentForStudents=?, author=? WHERE examID=?");
			pstmt.setString(1, Integer.toString(exam.getTimeOfExam())); // set exam time
			pstmt.setString(2, exam.getCommentForTeacher()); // set comments for teacher
			pstmt.setString(3, exam.getCommentForStudents());// set comments for students
			pstmt.setLong(4, exam.getAuthor().getId());// set teacher id
			pstmt.setString(5, exam.getExamID()); // set exam id
			if (pstmt.executeUpdate() == 1) {
				System.out.println("Saved");
				return true;
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return false;
	}

	public boolean deleteActiveExam(Exam exam) {
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("DELETE FROM active_exam WHERE exam=?");
			pstmt.setString(1, exam.getExamID());
			if (pstmt.executeUpdate() == 1)
				return true;
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
			return false;
		}
		return false;
	}

	
	public ResponseFromServer updateExamStatus(ActiveExam newActiveExam) {
		/* logic for -EnterToExam*/
		ResponseFromServer res = null;
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("UPDATE exam SET status=? WHERE exam=?");
			pstmt.setObject(1, newActiveExam.getExam().getExamStatus());
			pstmt.setString(2, newActiveExam.getExam().getExamID());
			if (pstmt.executeUpdate() == 1) {
				res = new ResponseFromServer("EXAM STATUS UPDATED");
			}
		} catch (SQLException ex) {
			serverFrame.printToTextArea("SQLException: " + ex.getMessage());
		}
		return res;
	}
	
	
	
	
	
	
	

}
