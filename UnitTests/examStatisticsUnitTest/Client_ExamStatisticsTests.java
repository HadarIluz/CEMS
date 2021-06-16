package examStatisticsUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import client.CEMSClient;
import client.ClientUI;
import common.CemsIF;
import common.ICEMSClient;
import gui_teacher.TeacherStatisticsController;
import logic.RequestToServer;
import logic.ResponseFromServer;

public class Client_ExamStatisticsTests {

	class StubCemsIF implements CemsIF {

		@Override
		public void display(String message) {
			// not used in this case

		}

		@Override
		public void accept(Object obj) {

			if (obj instanceof RequestToServer) {

				RequestToServer rts = (RequestToServer) obj;

				switch (rts.getRequestType()) {

				case "checkExamExist": {

					responseFromServer.setResponseData(boolCheck);

				}
					break;

				case "gradesAverageCalc": {

					responseFromServer.setResponseData(GradesArrayForCheck);
				}

				}
			}

		}

	}

	class StubCEMSClient implements ICEMSClient {

		@Override
		public ResponseFromServer getResponseFromServer() {
			return responseFromServer;
		}

	}
	
	/* -------------- Client Side Tests Only! ----------------- */
	/* ----- for server side, please see Server_ExamStatisticsTests.java ---- */

	// stub class needed for test
	private CemsIF cemsIFM;
	
	// variables needed to test
	private ResponseFromServer responseFromServer;	
	private String boolCheck;// for check if exam exist- not neccessary for project
	private ArrayList<Integer> GradesArrayForCheck;
	
	//class under test
	private TeacherStatisticsController TSC;

	@Before
	public void setUp() throws Exception {
	
		cemsIFM = new StubCemsIF();
		ClientUI.cems = cemsIFM;
		responseFromServer = new ResponseFromServer("");
		ICEMSClient iCemsManager = new StubCEMSClient();
		CEMSClient.instance = iCemsManager;
		TSC = new TeacherStatisticsController();
		TSC.setFlag(false);
	}

	//-------------------------
	/* testing checkDetails method. throw exception when user is null
	 * expected: exception - "user object is null"
	 * input: null user
	 */
	@Test
	public void testCheckExamExistReturnTrue() {

		boolCheck = "TRUE";
		responseFromServer = new ResponseFromServer("Grades array for Average1");
		assertTrue(TSC.checkExamExist("123456"));
	}

	
	//--------------------
	
	/* testing gradesAverageCalc method. success case
	 * expected: average calculation (expected = 75)
	 * input: GradesArrayForCheck = {74,76,75}
	 */
	@Test
	public void testAverageSuccess() {
		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(75);

		float expected = (float) 75.0;
		float acctualRes;

		acctualRes = TSC.gradesAverageCalc("123456");

		assertEquals(expected, acctualRes, 0.1);
	}

	/* testing gradesAverageCalc method. checking case of the limit above the returning range from method
	 * expected: average calculation (expected = 75.2)
	 * input: GradesArrayForCheck = {74,76,75}
	 */
	@Test
	public void testAverageNotSuccessAboveTheRange() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(75);

		float expected = (float) 75.2;
		float acctualRes;

		acctualRes = TSC.gradesAverageCalc("123456");

		assertNotEquals(expected, acctualRes, 0.1);
	}

	/* testing gradesAverageCalc method. checking case of the limit below the returning range from method
	 * expected: average calculation (expected = 74.89)
	 * input: GradesArrayForCheck = {74,76,75}
	 */
	@Test
	public void testAverageNotSuccessBelowTheRange() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(75);

		float expected = (float) 74.89;
		float acctualRes;

		acctualRes = TSC.gradesAverageCalc("123456");

		assertNotEquals(expected, acctualRes, 0.1);
	}

	/* testing gradesAverageCalc method. checking case of empty array sent to method
	 * expected: average calculation (expected = 0)
	 * input: GradesArrayForCheck = {}
	 */
	@Test
	public void testAverageEmptyGradesArray() {

		GradesArrayForCheck = new ArrayList<Integer>();

		float expected = (float) 0;
		float acctualRes;

		acctualRes = TSC.gradesAverageCalc("123456");

		assertNotEquals(expected, acctualRes, 0.1);
	}

	/* testing gradesAverageCalc method. checking case of null array sent to method
	 * expected: Null Pointer Exception throw
	 * input: GradesArrayForCheck = null
	 */
	@Test
	public void testAverageNullGradesArray() {

		GradesArrayForCheck = null;

		try {
			TSC.gradesAverageCalc("123456");
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

	/* testing medianCalc method. checking case of odd amount of grades in array sent to method
	 * expected: median calculation (expected = 75)
	 * input: GradesArrayForCheck = {74,76,75}
	 */
	@Test
	public void testMedianSuccessWithOddAmountOfValues() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(75);

		float expected = (float) 75;
		float acctualRes;

		acctualRes = TSC.medianCalc(GradesArrayForCheck);

		assertEquals(expected, acctualRes, 0.1);

	}

	/* testing medianCalc method. checking case of even amount of grades in array sent to method
	 * expected: median calculation (expected = 75.5)
	 * input: GradesArrayForCheck = {74,76,80,75}
	 */
	@Test
	public void testMedianSuccessWithEvenAmountOfValues() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(80);
		GradesArrayForCheck.add(75);

		float expected = (float) 75.5;
		float acctualRes;

		acctualRes = TSC.medianCalc(GradesArrayForCheck);

		assertEquals(expected, acctualRes, 0.1);

	}

	/* testing medianCalc method. checking case of even amount of grades in array sent to method above the limit range (0.1)
	 * expected: median calculation (expected = 75)
	 * input: GradesArrayForCheck = {74,76,80,75}
	 */
	@Test
	public void testMedianNotSuccessWithEvenAmountOfValuesAbove() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(80);
		GradesArrayForCheck.add(75);

		float expected = (float) 75;
		float acctualRes;

		acctualRes = TSC.medianCalc(GradesArrayForCheck);

		assertNotEquals(expected, acctualRes, 0.1);

	}

	/* testing medianCalc method. checking case of even amount of grades in array sent to method below the limit range (0.1)
	 * expected: median calculation (expected = 75)
	 * input: GradesArrayForCheck = {74,76,80,75}
	 */
	@Test
	public void testMedianNotSuccessWithEvenAmountOfValuesBelow() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(80);
		GradesArrayForCheck.add(75);

		float expected = (float) 74;
		float acctualRes;

		acctualRes = TSC.medianCalc(GradesArrayForCheck);

		assertNotEquals(expected, acctualRes, 0.1);

	}

	/* testing medianCalc method. checking case of odd amount of grades in array sent to method above the limit range (0.1)
	 * expected: median calculation (expected = 76)
	 * input: GradesArrayForCheck = {74,76,75}
	 */
	@Test
	public void testMedianNotSuccessWithOddAmountOfValuesAboveTheMedian() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(75);

		float expected = (float) 76;
		float acctualRes;

		acctualRes = TSC.medianCalc(GradesArrayForCheck);

		assertNotEquals(expected, acctualRes, 0.1);

	}

	/* testing medianCalc method. checking case of odd amount of grades in array sent to method ,below the limit range (0.1)
	 * expected: median calculation (expected = 74)
	 * input: GradesArrayForCheck = {74,76,75}
	 */
	@Test
	public void testMedianNotSuccessWithOddAmountOfValuesBelowTheMedian() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(75);

		float expected = (float) 74;
		float acctualRes;

		acctualRes = TSC.medianCalc(GradesArrayForCheck);

		assertNotEquals(expected, acctualRes, 0.1);

	}

	/* testing medianCalc method. checking case of empty array sent to method.
	 * expected: median calculation (expected = 0)
	 * input: GradesArrayForCheck = {}
	 */
	@Test
	public void testMedianSuccessWithEmptyGradesArray() {
		GradesArrayForCheck = new ArrayList<Integer>();
		float expected = (float) 0;
		float acctualRes;
		acctualRes = TSC.medianCalc(GradesArrayForCheck);
		assertEquals(expected, acctualRes, 0.1);

	}

	/* testing medianCalc method. checking case of null array sent to method.
	 * expected: Null Pointer Exception
	 * input: GradesArrayForCheck = null
	 */
	@Test
	public void testMedianSuccessWithNullGradesArray() {
		GradesArrayForCheck = null;

		try {
			TSC.gradesAverageCalc("123456");
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

	/* testing setGradesInHistogram method. checking case of putting the number at the correct place in array that representing the histogram bars(deciles).
	 * expected: 3 in correct place in array (cell 7)
	 * input: GradesArrayForCheck = {74,76,75}
	 */
	@Test
	public void testForAppropriateDecilesValue() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(74);
		GradesArrayForCheck.add(76);
		GradesArrayForCheck.add(75);

		TSC.setGrades(GradesArrayForCheck);
		
		int expected = 3;
		int[] resultFromMethod = TSC.setGradesInHistogram("");
		assertEquals(expected, resultFromMethod[7]);
		assertEquals(0, resultFromMethod[6]);
		assertEquals(0, resultFromMethod[8]);

	}
	
	/* testing setGradesInHistogram method. case of border check (0,100) placed in array that representing the histogram bars(deciles).
	 * expected: every grade in correct cell
	 * input: GradesArrayForCheck = {0,100,54}
	 */
	@Test
	public void testForAppropriateDecilesValueDifferntIndexes() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(0);
		GradesArrayForCheck.add(100);
		GradesArrayForCheck.add(54);

		TSC.setGrades(GradesArrayForCheck);
		
		int expected = 1;
		int[] resultFromMethod = TSC.setGradesInHistogram("");
		assertEquals(expected, resultFromMethod[0]);
		assertEquals(expected, resultFromMethod[9]);
		assertEquals(expected, resultFromMethod[5]);

	}

	/* testing setGradesInHistogram method. case of near the limit grade.
	 * expected: 80 go to 8 cell
	 * input: GradesArrayForCheck = {80}
	 */
	@Test
	public void testForWrongAppropriateDecilesValueAbove() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(80);// should be in the 7th index in array
		TSC.setGrades(GradesArrayForCheck);
		
		int expected = 0;
		int[] resultFromMethod = TSC.setGradesInHistogram("");
		for (int i = 0; i < resultFromMethod.length; i++)
			expected += resultFromMethod[i];

		assertNotEquals(expected, resultFromMethod[8]);
		assertEquals(expected, GradesArrayForCheck.size());

	}
	
	/* testing setGradesInHistogram method. case of negative grades.
	 * expected: all array cells equals 0
	 * input: GradesArrayForCheck = {-1}
	 */
	@Test
	public void testForWrongAppropriateDecilesValueNeg() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(-1);// should Not return to any index in Histogram
		TSC.setGrades(GradesArrayForCheck);
		
		int expected = 0;
		int[] resultFromMethod = TSC.setGradesInHistogram("");
		for (int i = 0; i < resultFromMethod.length; i++)
			expected += resultFromMethod[i];
		assertEquals(expected,0);

	}
	
	/* testing setGradesInHistogram method. case of grade above 100.
	 * expected: every grade in correct cell
	 * input: GradesArrayForCheck = {101}
	 */
	@Test
	public void testForWrongAppropriateDecilesValueAbove100() {

		GradesArrayForCheck = new ArrayList<Integer>();
		GradesArrayForCheck.add(101);// should Not return to any index in Histogram
		TSC.setGrades(GradesArrayForCheck);
		
		int expected = 0;
		int[] resultFromMethod = TSC.setGradesInHistogram("");
		for (int i = 0; i < resultFromMethod.length; i++)
			expected += resultFromMethod[i];
		assertEquals(expected,0);

	}
	
	
	
	
	

}
