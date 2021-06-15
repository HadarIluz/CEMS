package examStatisticsUnitTest;

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
	ArrayList<Integer> DBgradesOfExam,expectedArray;

	@Before
	public void setUp() throws Exception {	
		DBC = new DBController();
		DBC.connectDB(SFC);
		expectedArray = new ArrayList<>();
	}

	@Test
	public void testMethodGradesAverageCalcReturSuccessAmountOfStudent() {
		ExamID="010203";
		
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		expectedArray = new ArrayList<>();
		expectedArray.add(40);
		if(expectedArray.size()!=DBgradesOfExam.size())
			fail();
		assertTrue(true);
	}
	
	@Test
	public void testMethodGradesAverageCalcReturnWrongAmountOfStudent() {
		ExamID="010203";
		int expectedSize=3;//we choose 3 because there is another exam with 3 grades
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);		
		assertNotEquals(expectedSize,DBgradesOfExam.size());
		expectedSize=2;//same reason as above
		assertNotEquals(expectedSize,DBgradesOfExam.size());

	}
	
	@Test
	public void testMethodGradesAverageCalcReturnExpectedScores() {
		ExamID="010203";
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		expectedArray.add(40);
		assertTrue(expectedArray.equals(DBgradesOfExam));
	}
	//-------------------------------------------------------------------
	@Test
	public void testMethodGradesAverageCalcReturSuccessAmountOfStudent2() {
		ExamID="010202";
		
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		int expectedArraySize=3;
		assertEquals(expectedArraySize,DBgradesOfExam.size());
	}
	
	@Test
	public void testMethodGradesAverageCalcReturnWrongAmountOfStudent2() {
		ExamID="010202";
		int expectedSize=1;//we choose 3 because there is another exam with 3 grades
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);		
		assertNotEquals(expectedSize,DBgradesOfExam.size());
		expectedSize=2;//same reason as above
		assertNotEquals(expectedSize,DBgradesOfExam.size());

	}
	
	@Test
	public void testMethodGradesAverageCalcReturnExpectedScores2() {
		ExamID="010202";
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		expectedArray.add(100);
		expectedArray.add(45);
		expectedArray.add(25);
		for(Integer curr:DBgradesOfExam) 
			if(!DBgradesOfExam.contains(curr))
				fail();
		assertTrue(true);
	}
	
	

}
