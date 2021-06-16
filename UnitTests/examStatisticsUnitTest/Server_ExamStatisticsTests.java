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
import gui_teacher.TeacherStatisticsController;

public class Server_ExamStatisticsTests {

	/* -------------- Server Side Tests Only! ----------------- */
	/* ----- for client side, please see Client_ExamStatisticsTests.java ---- */

	ServerFrameController SFC=null;
	DBController DBC;
	String ExamID;
	ArrayList<Integer> DBgradesOfExam,expectedArray;
	
	TeacherStatisticsController TSC;

	@Before
	public void setUp() throws Exception {	
		DBC = new DBController();
		DBC.connectDB(SFC);
		expectedArray = new ArrayList<>();
	}
	

	/* testing gradesAverageCalc method. return Array List of student's grades
	 * expected: same amount of student between our expected array to the array that return from DBController class
	 * input: Exam id containing grade of student
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
	
	/* testing gradesAverageCalc method. return Array List of student's grades
	 * expected: not the same amount of student between our expected array to the array that return from DBController class
	 * input: Exam id containing grade of student
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
	
	/* testing gradesAverageCalc method. return Array List of student's grades
	 * expected:  same grades of students between our expected array to the array that return from DBController class
	 * input: Exam id containing grade of student
	 */
	@Test
	public void testMethodGradesAverageCalcReturnExpectedScores() {
		ExamID="010203";
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		expectedArray.add(40);
		assertTrue(expectedArray.equals(DBgradesOfExam));
	}
	
	
	/* testing gradesAverageCalc method. return Array List of studwent's grades
	 * expected:  same value of student between  expectedArraySize assamption  to the array that reutrn from DBController class
	 * input: Exam id containing grade of student
	 */
	
	@Test
	public void testMethodGradesAverageCalcReturSuccessAmountOfStudent2() {
		ExamID="010202";
		
		DBgradesOfExam= DBC.gradesAverageCalc(ExamID);
		int expectedArraySize=3;
		assertEquals(expectedArraySize,DBgradesOfExam.size());
	}
	/* testing gradesAverageCalc method. return Array List of studwent's grades
	 * expected:  Not equal amount of student between  expectedArraySize assamption  to the array that reutrn from DBController class
	 * input: Exam id containing grade of student
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
	
	/* testing gradesAverageCalc method. return Array List of student's grades
	 * expected:  true if the condition in loop is never accepted, which mean that arrays not qual
	 * input: Exam id containing grade of student
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
