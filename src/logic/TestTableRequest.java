package logic;

import java.util.ArrayList;

public class TestTableRequest{
	private ArrayList<TestRow> testTableData;

	public TestTableRequest() {
		super();
		testTableData = new ArrayList<>();
	}
	
	public void addRow(TestRow row) {
		testTableData.add(row);
	}
	
	public ArrayList<TestRow> getTableData() {
		return testTableData;
	}
	
	public String toString() {
		return testTableData.toString();
	}
	
	
}
