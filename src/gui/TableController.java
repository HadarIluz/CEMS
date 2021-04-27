package gui;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import logic.TestTableRequest;

public class TableController {
	private TestTableRequest tesTable; //Used for the test table shown on Screen

    @FXML
    private Button btnTest;

    @FXML
    private Button btnTable;

    @FXML
    private Font x1;

    @FXML
    private Font x3;
    
    
    
    
    
    
	/*
	 * 
	 * //need to be on Table form....THINK. //the function refresh and update the
	 * table filed. private void refreshTable() {
	 * 
	 * }
	 */
    
    @FXML
    void pressTable(ActionEvent event) {
    	ClientUI.chat.accept("Table"); //send message to get all table rows

    }

	public void loadTable(TestTableRequest table) {
		
		//
		
	}
    
}
