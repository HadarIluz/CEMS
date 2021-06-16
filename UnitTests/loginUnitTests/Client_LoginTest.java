package loginUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import common.CemsIF;
import common.ICEMSClient;
import entity.Student;
import entity.Teacher;
import entity.User;
import gui_cems.LoginLogic;
import logic.ResponseFromServer;
import logic.StatusMsg;

public class Client_LoginTest {

	class StubCemsIF implements CemsIF {

		@Override
		public void display(String message) {
			// not used in this case
			
		}

		@Override
		public void accept(Object obj) {
			// imitate sending msg to client
			
		}
		
	}
	
	class StubCEMSClient implements ICEMSClient {

		@Override
		public ResponseFromServer getResponseFromServer() {
			return responseFromServer;
		}
		
	}
	/* -------------- Client Side Tests Only! ----------------- */
	/* ----- for server side, please see Server_LoginTest.java ---- */
	
	// variables needed to test
	private ResponseFromServer responseFromServer;
	private StatusMsg statusMsg;
	private User user;
	
	// stub classes needed for test
	public CemsIF ClientController;
	public ICEMSClient CemsClient;
	
	// class we are testing
	public LoginLogic login;
	
	@Before
	public void setUp() throws Exception {
		ClientController = new StubCemsIF();
		CemsClient = new StubCEMSClient();
		
		login = new LoginLogic(CemsClient, ClientController);
	}
	
	/* ------------------------- checkDetails --------------------------- */

	/* testing checkDetails method. throw exception when user is null
	 * expected: exception - "user object is null"
	 * input: null user
	 */
	@Test
	public void testFail_NullUser() {		
		user = null;
		String expected = "user object is null";
		
		try {
			login.checkDetails(user);
		} catch (NullPointerException e) {
			assertEquals(expected, e.getMessage());
		}
	}
	
	/* testing checkDetails method. return null when user is not found
	 * expected: exception - null
	 * input: user
	 */
	@Test
	public void testFail_UserNotFound() {
		// prepare objects for stub
		responseFromServer = new ResponseFromServer("USER NOT FOUND");
		statusMsg = new StatusMsg();
		statusMsg.setDescription("USER NOT FOUND");
		responseFromServer.setStatusMsg(statusMsg);
		// create objects for test
		user = new User(123456789, "password");
		User expected = null;
		User actual;
		
		try {
			actual = login.checkDetails(user);
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
	}
	
	/* testing checkDetails method. return null when user password is incorrect
	 * expected: exception - null
	 * input: user
	 */
	@Test
	public void testFail_WrongPassword() {
		// prepare objects for stub
		responseFromServer = new ResponseFromServer("USER  FOUND");
		responseFromServer.setResponseData(new User(123456789, "pass123"));
		// create objects for test
		user = new User(123456789, "password");
		User expected = null;
		User actual;
		
		try {
			actual = login.checkDetails(user);
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
	}

	/* testing checkDetails method. return user when details are correct
	 * expected: user
	 * input: user
	 */
	@Test
	public void testSuccess_UserCorrect() {
		// create objects for test
		user = new User(123456789, "password");
		User expected = new User(123456789, "password");
		User actual;
		// prepare objects for stub
		responseFromServer = new ResponseFromServer("USER  FOUND");
		responseFromServer.setResponseData(expected);
		
		try {
			actual = login.checkDetails(user);
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
	}
	
	/* ------------------------- checkIdFieldVaild --------------------------- */

	
	/* testing checkIdFieldVaild method. return false when length of given id is not 9
	 * expected: false
	 * input: userID = "1111"
	 */
	@Test
	public void testFail_LengthNot9() {
		boolean expected = false;
		String userID = "1111";
		boolean actual = login.checkIdFieldVaild(userID);
		
		assertEquals(expected, actual);
	}
	
	/* testing checkIdFieldVaild method. return false when userID is not only digits
	 * expected: false
	 * input: userID = "12345678A"
	 */
	@Test
	public void testFail_NotOnlyDigits() {
		boolean expected = false;
		String userID = "12345678A";
		boolean actual = login.checkIdFieldVaild(userID);
		
		assertEquals(expected, actual);
	}
	
	/* testing checkIdFieldVaild method. return true when userID is valid
	 * expected: true
	 * input: userID = "123456789"
	 */
	@Test
	public void testSuccess_ValidID() {
		boolean expected = true;
		String userID = "123456789";
		boolean actual = login.checkIdFieldVaild(userID);
		
		assertEquals(expected, actual);
	}
	
	/* ------------------------- getStudentData --------------------------- */

	/* testing getStudentData method. throw exception when user is null
	 * expected: exception - "user object is null"
	 * input: null user
	 */
	@Test
	public void testFailStudentData_NullUser() {		
		user = null;
		String expected = "user object is null";
		
		try {
			login.getStudentData(user);
		} catch (NullPointerException e) {
			assertEquals(expected, e.getMessage());
		}
	}
	
	/* testing getStudentData method. return student with details
	 * expected: student
	 * input: user
	 */
	@Test
	public void testSuccessStudentData() {
		// create objects for test
		user = new User(111111111, "password");
		Student expected = new Student(user, 88, null);
		Student actual;
		// prepare objects for stub
		responseFromServer = new ResponseFromServer("STUDENT DATA");
		responseFromServer.setResponseData(expected);
		
		try {
			actual = login.getStudentData(user);
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
	}
	
	/* ------------------------- getTeacherData --------------------------- */

	/* testing getTeacherData method. throw exception when user is null
	 * expected: exception - "user object is null"
	 * input: null user
	 */
	@Test
	public void testFailTeacherData_NullUser() {		
		user = null;
		String expected = "user object is null";
		
		try {
			login.getTeacherData(user);
		} catch (NullPointerException e) {
			assertEquals(expected, e.getMessage());
		}
	}
	
	/* testing getTeacherData method. return teacher with details
	 * expected: teacher
	 * input: user
	 */
	@Test
	public void testSuccessTeacherData() {
		// create objects for test
		user = new User(111111111, "password");
		Teacher expected = new Teacher(user, null);
		Teacher actual;
		// prepare objects for stub
		responseFromServer = new ResponseFromServer("TEACHER DATA");
		responseFromServer.setResponseData(expected);
		
		try {
			actual = login.getTeacherData(user);
			assertEquals(expected, actual);
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
	}

}
