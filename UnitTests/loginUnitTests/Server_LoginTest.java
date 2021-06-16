package loginUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Server.DBController;
import entity.User;
import gui_server.ServerFrameController;
import logic.ResponseFromServer;

public class Server_LoginTest {

	// prepare objects
	private ServerFrameController serverFrameController;
	private DBController dbController;
	User user;
	
	@Before
	public void setUp() throws Exception {
		serverFrameController = null;
		dbController = new DBController();
		dbController.connectDB(serverFrameController);
	}

	/* testing verifyLoginUser method. return ResponseFromServer saying when user is not found
	 * expected: exception - "USER NOT FOUND IN CEMS SYSTEM"
	 * input: user with wrong id
	 */
	@Test
	public void testSuccess_UserNotExist() {
		user = new User(1111111112, "pass");
		String expected = "USER NOT FOUND IN CEMS SYSTEM";
		
		String actual = dbController.verifyLoginUser(user).getResponseType();
		
		assertEquals(expected, actual);
		
	}
	
	/* testing verifyLoginUser method. return ResponseFromServer saying when user is found
	 * expected: exception - "USER FOUND IN CEMS SYSTEM"
	 * input: user with right id
	 */
	@Test
	public void testSuccess_UserFound() {
		user = new User(111111111, "pass");
		String expected = "USER FOUND IN CEMS SYSTEM";
		
		String actual = dbController.verifyLoginUser(user).getResponseType();
		
		assertEquals(expected, actual);
		
	}

}
