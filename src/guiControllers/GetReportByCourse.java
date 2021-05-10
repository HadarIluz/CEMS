package guiControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class GetReportByCourse implements Initializable {

	@FXML
	private BarChart<String, Number> CourseHisto;
	@FXML
	private CategoryAxis ca;

	@FXML
	private NumberAxis na;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		CourseHisto.setTitle("HEY");
		ca = new CategoryAxis();
		na = new NumberAxis();
	//	CourseHisto = new BarChart<String, Number>(ca, na);
		XYChart.Series<String, Number> chart = new XYChart.Series<String, Number>();// table of x and y
		
		CourseHisto.setBarGap(10);
		chart.setName("check");
		chart.getData().add(new XYChart.Data<>("100280", 70));
		chart.getData().add(new XYChart.Data<>("213123", 55));
		chart.getData().add(new XYChart.Data<>("102930", 80));
		
		CourseHisto.getData().add(chart);
		

	}

}