package logic;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TestTableRequest implements Serializable{
	private ArrayList<TestRow> testTableData;

	public TestTableRequest() {
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
