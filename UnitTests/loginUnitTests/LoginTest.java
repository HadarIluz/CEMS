package loginUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import common.CemsIF;
import common.ICEMSClient;
import logic.ResponseFromServer;

public class LoginTest {

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
	
	private ResponseFromServer responseFromServer;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
