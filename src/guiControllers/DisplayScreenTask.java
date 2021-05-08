package guiControllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DisplayScreenTask extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			Scene scene = new Scene(root, 1004, 626);
			Pane newMnueLeft = FXMLLoader.load(getClass().getResource("StudentMenuLeft.fxml"));
			root.add(newMnueLeft, 0, 0);
			// root.getChildren().add(newMnueLeft);
			// --

			Pane newPaneRight = FXMLLoader.load(getClass().getResource("CreateExam_step1.fxml"));
			root.add(newPaneRight, 1, 0);
			// root.getChildren().add(newPaneRight);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
