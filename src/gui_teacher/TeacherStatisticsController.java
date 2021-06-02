package gui_teacher;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TeacherStatisticsController {

    @FXML
    private TextField textExamID;

    @FXML
    private Label textMedian;

    @FXML
    private Label textCourse;

    @FXML
    private BarChart<?, ?> StudentsHisto;

    @FXML
    private Button btnShowStatistic;

    @FXML
    private Label textErrorMessage;

    @FXML
    private Label textProfession;

    @FXML
    private Label textAverage;
    
    private TeacherController teacherController; //we will use it for load the next screen ! (using root).

    @FXML
    void btnShowStatistic(MouseEvent event) {

    }

}
