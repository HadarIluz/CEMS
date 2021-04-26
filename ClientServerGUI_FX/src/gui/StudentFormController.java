package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Faculty;
import logic.Student;

public class StudentFormController implements Initializable {
	private Student s;
		
	@FXML
	private Label lblName;
	@FXML
	private Label lblSurname;
	@FXML
	private Label lblFaculty;
	
	//new:
	@FXML
	private TextField txtstudID;
	//new:
	@FXML
	private Label IbStID;	
	
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSurname;
	
	@FXML
	private Button btnclose;  //change.
	
	@FXML
	private ComboBox cmbFaculty;

	@FXML
	private Button btnSave;  //new.
	
	ObservableList<String> list;
	
	//new getters:
	private String getID() {
		return txtstudID.getText();
	}
	private String getName() {
		return txtName.getText();
	}
	private String getSurname() {
		return txtSurname.getText();
	}
	private String getlblFaculty() {
		return cmbFaculty.getPromptText();
	}

	
	
		
	public void loadStudent(Student s1) {
		this.s=s1;
		this.txtstudID.setText(s.getId());
		this.txtName.setText(s.getPName());
		this.txtSurname.setText(s.getLName());		
		this.cmbFaculty.setValue(s.getFc().getFName());
	}
	
	// creating list of Faculties
	private void setFacultyComboBox() {
		ArrayList<String> al = new ArrayList<String>();	
		al.add("ME");
		al.add("IE");
		al.add("SE");

		list = FXCollections.observableArrayList(al);
		cmbFaculty.setItems(list);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		setFacultyComboBox();		
	}
	
	//fix: new button to student form for close.
	public void getBtnclose(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("Btnclose press");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/AcademicFrame.fxml").openStream());
		
		AcademicFrameController academicFrameController = loader.getController();		
		//academicFrameController.loadStudent(ChatClient.s1);
	
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
		primaryStage.setTitle("Academic Managment Tool");

		primaryStage.setScene(scene);		
		primaryStage.show();
	
	}
	
	public void getBtnSave(ActionEvent event) throws Exception {
		String id;
		String Name;
		String Surname;
		String Faculty;
		int flag=0;
		FXMLLoader loader = new FXMLLoader();
		
		//setters:
		id=getID();
		Name=getName();
		Surname=getSurname();
		Faculty=getlblFaculty();
		
		if(id.trim().isEmpty() || Name.trim().isEmpty() || Surname.trim().isEmpty() || Faculty.trim().isEmpty() )
		{

			System.out.println("You must enter an req filed");	
		}
		else
		{
			ClientUI.chat.accept(id);
			//new:
			ClientUI.chat.accept(Name);
			ClientUI.chat.accept(Surname);
			ClientUI.chat.accept(Faculty);
			
			
		//check if neded
			if(ChatClient.s1.getId().equals("Error"))
			{
				System.out.println("Student ID Not Found");
				
			}
			
			//new:
			if(id.trim().isEmpty())
			{
				flag = 1;
				System.out.println("You must enter an id number");	
			}
			if(Name.trim().isEmpty())
			{
				flag = 1;
				System.out.println("You must enter a name");	
			}
			if(Surname.trim().isEmpty())
			{
				flag = 1;
				System.out.println("You must enter a surname");	
			}
			if(flag == 0)
			{
				ClientUI.chat.accept(id);
				ClientUI.chat.accept(Name);
				ClientUI.chat.accept(Surname);
				ClientUI.chat.accept(Faculty);
			}
			//end new
			
			else {
				if(ChatClient.s1.getId().equals("Error") || ChatClient.s1.getPName().equals("Error") || ChatClient.s1.getLName().equals("Error") || ChatClient.s1.getFc().equals("Error"))
				{
					System.out.println("Error");
				}

				System.out.println("Student ID Found");
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/gui/AcademicFrame.fxml").openStream());
				StudentFormController studentFormController = loader.getController();		
				////studentFormController.loadStudent(ChatClient.s1);
			
				Scene scene = new Scene(root);			
				scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
				primaryStage.setTitle("AcademicFrame Managment Tool");
	
				primaryStage.setScene(scene);		
				primaryStage.show();
			}
		}
	
	}
	
}
