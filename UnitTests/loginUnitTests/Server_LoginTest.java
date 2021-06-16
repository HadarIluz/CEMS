package loginUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Server.DBController;
import entity.User;
import gui_server.ServerFrameController;

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

	/* testing verifyLoginUser method. throw exception when user is not found
	 * expected: exception - "user object is null"
	 * input: user with wrong id
	 */
	@Test
	public void testFail_UserNotExist() {
		user = new User(1111111112, "pass");
		String expected = "";
		try {
			dbController.verifyLoginUser(user);
		} catch (NullPointerException e) {
			assertEquals(expected, e.getMessage());
		}
	}

}
