package controller;


import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class JavaFXApplication extends Application {
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = FXMLLoader.load(JavaFXApplication.class.getResource("/view/index.fxml"));
			Scene scene = new Scene(root, 800, 630);

			File f = new File("resources/application.css");
			scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

			primaryStage.setTitle("ATI");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			JavaFXApplication.primaryStage = primaryStage;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
