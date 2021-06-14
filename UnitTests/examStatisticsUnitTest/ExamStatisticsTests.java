package examStatisticsUnitTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import common.CemsIF;
import common.ICEMSClient;
import gui_teacher.TeacherStatisticsController;
import logic.ResponseFromServer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.atLeastOnce;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;


import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class ExamStatisticsTests {

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
	
	public static void disableWarning() {
		System.err.close();
		System.setErr(System.out);
	}
	
	private ResponseFromServer responseFromServer;
	private TeacherStatisticsController TSC;
	@Before
	public void setUp() throws Exception {
		disableWarning();
		TSC=Mockito.mock(TeacherStatisticsController.class);
		TSC=new TeacherStatisticsController();
		when(TSC.checkExamExist(any(String.class))).thenReturn(true);
	}

	@Test
	public void test() {
	
		assertTrue(TSC.checkExamExist("123456"));
	}
}
