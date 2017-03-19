package controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

public class FileMenu extends Menu {
	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem openFile;
	@FXML
	private MenuItem saveRaw;
	@FXML
	private MenuItem openRaw;

	private FileChooser fileChooser = new FileChooser();

	public FileMenu() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fileMenu.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize(ImageTab controller) {
		openFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					controller.openImage(file);
				}
			}
		});

		saveFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					controller.saveImage(file);
				}
			}

		});
	}
}
