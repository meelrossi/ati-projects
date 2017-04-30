package controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import controller.tab.ImageTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import utils.ImageManager;

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
		openRaw.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					if (file.getName().toLowerCase().contains(".raw")) {
						OpenRawImageDialog dialog = new OpenRawImageDialog();
						dialog.initOwner(getParentPopup());
						Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
						result.ifPresent(usernamePassword -> {
						    ImageManager.readFromRaw(file, result.get().getKey(), result.get().getValue());
						});
					}
				}
			}
		});
	}
}
