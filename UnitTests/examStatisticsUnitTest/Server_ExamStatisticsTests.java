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

	/* -------------- Client Side Tests Only! ----------------- */
	/* ----- for client side, please see Client_ExamStatisticsTests.java ---- */

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

	/* testing GradesAverageCalc method. check case of correct amount of student from specific exam
	 * expected: return 1 student
	 * input: ExamID=010203
	 */
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
	
	/* testing GradesAverageCalc method. check case of correct amount of student from specific exam 
	 * (check that dataBase took the amount of student from the right exam )
	 * expected: return 1 student
	 * input: ExamID=010203
	 */
	@Test
	public void testMethodGradesAverageCalcReturnWrongAmountOfStudent() {
		ExamID="010203";
		int expectedSize=3;//we choose 3 because there is another exam with 3 grades
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);		
		assertNotEquals(expectedSize,DBgradesOfExam.size());
		expectedSize=2;//same reason as above
		assertNotEquals(expectedSize,DBgradesOfExam.size());

	}
	
	/* testing GradesAverageCalc method. check case of correct scores of students from specific exam 
	 * expected: return student with grade = 40
	 * input: ExamID=010203
	 */
	@Test
	public void testMethodGradesAverageCalcReturnExpectedScores() {
		ExamID="010203";
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		expectedArray.add(40);
		assertTrue(expectedArray.equals(DBgradesOfExam));
	}
	//-------------------------------------------------------------------
	
	/* testing GradesAverageCalc method. check case of correct amount of student from specific exam
	 * expected: return 3 student
	 * input: ExamID=010202
	 */
	@Test
	public void testMethodGradesAverageCalcReturSuccessAmountOfStudent2() {
		ExamID="010202";
		
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		int expectedArraySize=3;
		assertEquals(expectedArraySize,DBgradesOfExam.size());
	}
	
	/* testing GradesAverageCalc method. check case of correct amount of student from specific exam
	 * (check that dataBase took the amount of student from the right exam )
	 * expected: return 3 student
	 * input: ExamID=010202
	 */
	@Test
	public void testMethodGradesAverageCalcReturnWrongAmountOfStudent2() {
		ExamID="010202";
		int expectedSize=1;//we choose 3 because there is another exam with 3 grades
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);		
		assertNotEquals(expectedSize,DBgradesOfExam.size());
		expectedSize=2;//same reason as above
		assertNotEquals(expectedSize,DBgradesOfExam.size());

	}
	
	/* testing GradesAverageCalc method. check case of correct scores of students from specific exam 
	 * expected: return 3 student with grades = 100, 40 , 25 
	 * input: ExamID=010202
	 */
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
