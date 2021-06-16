package loginUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import common.CemsIF;
import common.ICEMSClient;
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

}
