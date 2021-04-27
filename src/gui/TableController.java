package gui;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import logic.TestRow;
import logic.TestTableRequest;

public class TableController {
	private TestTableRequest tesTable; // Used for the test table shown on Screen


    @FXML
    private Button btnTest;

    @FXML
    private Button btnTable;

    @FXML
    private Font x1;

    @FXML
    private TableView<TestRow> table;

    @FXML
    private TableColumn<TestRow, Integer> examID_col;

    @FXML
    private TableColumn<TestRow, String> profession_col;

    @FXML
    private TableColumn<TestRow, String> course_col;

    @FXML
    private TableColumn<TestRow, String> time_col;

    @FXML
    private TableColumn<TestRow, String> points_col;

    @FXML
    private Font x3;

	@FXML
	public void pressTable(ActionEvent event) {
		ClientUI.chat.accept("Table"); // send message to get all table rows

	}


	public void setTable() {


	}
	
	
	
	

}
