package examStatisticsUnitTest;

import static org.mockito.Matchers.any;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import Server.DBController;
import gui_server.ServerFrameController;

public class Server_ExamStatisticsTests {

	
	ServerFrameController SFC=null;
	DBController DBC;
	String ExamID;


	@Before
	public void setUp() throws Exception {	
		DBC = new DBController();
		DBC.connectDB(SFC);
	}

	@Test
	public void testMethodGradesAverageCalcReturSuccessAmountOfStudent() {
		ExamID="010203";
		ArrayList<Integer> gradesOfExam,expectedArray;
		gradesOfExam= DBC.gradesAverageCalc(ExamID);
		expectedArray = new ArrayList<>();
		expectedArray.add(40);
		if(expectedArray.size()!=gradesOfExam.size())
			fail();
		assertTrue(true);
	}
	
	@Test
	public void testMethodGradesAverageCalcReturWrongAmountOfStudent() {
		ExamID="010203";
		ArrayList<Integer> gradesOfExam;
		int expectedSize=3;//we choose 3 because there is another exam with 3 grades
		gradesOfExam= DBC.gradesAverageCalc(ExamID);		
		assertNotEquals(expectedSize,gradesOfExam.size());
		expectedSize=2;//same reason as above
		assertNotEquals(expectedSize,gradesOfExam.size());

	}

}
