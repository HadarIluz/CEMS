package gui_teacher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui_cems.GuiCommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class UploadManualExam extends GuiCommon{

    @FXML
    private ImageView imgNotification;

    @FXML
    private Button btnBrowse;

    @FXML
    private TextField txtPath;

    @FXML
    private Button btnUpload;

    @FXML
    private Button btnBack;

    @FXML
    void btnBack(ActionEvent event) {
    	try {
			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_step1.fxml"));
			newPaneRight.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			TeacherController.root.add(newPaneRight, 1, 0);

		} catch (IOException e) {
			System.out.println("Couldn't load!");
			e.printStackTrace();
		}
    }

    @FXML
    void btnUploadPress(ActionEvent event) {

    }

    @FXML
    void onClickBroswe(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	File selectedExamFile = fc.showOpenDialog(null);
    	if(selectedExamFile != null) {
    		txtPath.setText(selectedExamFile.getAbsolutePath());
    		
    	}else
    		popUp("File is not valid !");
    
    }


}
