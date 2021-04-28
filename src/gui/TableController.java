package gui;

import java.awt.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.TestRow;
import logic.TestTableRequest;

public class TableController {
	private TestTableRequest tesTable; // Used for the test table shown on Screen
	public boolean ref = false;

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

//	@FXML
//	public void pressTable(ActionEvent event) {
//		ClientUI.chat.accept("Table"); // send message to get all table rows
//
//	}
	
	//display the "TestForm" after pressing btnTest from Main.
	public void pressUpdateTesFiledtBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		System.out.println("Test Fram Tool"); //message to console.

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary(Table) window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/TestForm.fxml").openStream());
		//TestController testController = loader.getController();	//ASK?	
		//testController.loadTable(ChatClient.testsTable.getTableData());
	
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/TestForm.css").toExternalForm());
		primaryStage.setTitle("Test Fram");

		primaryStage.setScene(scene);		
		primaryStage.show();
		
	}
	
	
	
	public TableView<TestRow> getTable() {
		return table;
	}

	FXMLLoader loader = new FXMLLoader();
	// think with yuval about, load loader.getController();
	//StudentFormController studentFormController = loader.getController();

	//Method that initialize the page with the names.
	@SuppressWarnings("unchecked")
	public void setTable() {
		
		// create observable object from testTableRequest arrayList
		// 
		TableColumn<TestRow, Integer> idColumn = new TableColumn<>("examID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("examID"));
		TableColumn<TestRow, String> profColumn = new TableColumn<>("profession");
		profColumn.setCellValueFactory(new PropertyValueFactory<>("profession"));
		TableColumn<TestRow, String> courseColumn = new TableColumn<>("course");
		courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
		TableColumn<TestRow, String> timeColumn = new TableColumn<>("time");
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		TableColumn<TestRow, String> pointsColumn = new TableColumn<>("points");
		pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

		table = (TableView<TestRow>) getTable();
		table.getColumns().addAll(idColumn, profColumn, courseColumn, timeColumn, pointsColumn);
	}
	
	
/*other try
	public void initialize(URL arg0, ResourceBundle arg1) {		
		// Get the caller fxml page
		examID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
		profession_col.setCellValueFactory(new PropertyValueFactory<>("profession"));
		course_col.setCellValueFactory(new PropertyValueFactory<>("course"));
		time_col.setCellValueFactory(new PropertyValueFactory<>("time"));
		points_col.setCellValueFactory(new PropertyValueFactory<>("points"));;
	}
	*/
	
	


}
