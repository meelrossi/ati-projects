package controller;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.util.Pair;

public class OpenRawImageDialog extends Dialog<Pair<Integer, Integer>> {

	public OpenRawImageDialog() {
		super();
		setTitle("Open raw dialog");
		setHeaderText("Set raw image width and height");
		initModality(Modality.APPLICATION_MODAL);

		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField widthField = new TextField();
		widthField.setPromptText("Width");
		TextField heightField = new TextField();
		heightField.setPromptText("Height");

		grid.add(new Label("Width:"), 0, 0);
		grid.add(widthField, 1, 0);
		grid.add(new Label("Height:"), 0, 1);
		grid.add(heightField, 1, 1);
		
		Node okButton = getDialogPane().lookupButton(ButtonType.OK);
		okButton.setDisable(true);

		widthField.textProperty().addListener((observable, oldValue, newValue) -> {
		    okButton.setDisable(newValue.trim().isEmpty() || heightField.getText().isEmpty());
		});
		heightField.textProperty().addListener((observable, oldValue, newValue) -> {
		    okButton.setDisable(newValue.trim().isEmpty() || heightField.getText().isEmpty());
		});

		getDialogPane().setContent(grid);

		Platform.runLater(() -> widthField.requestFocus());

		setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return new Pair<>(Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
		    }
		    return null;
		});
	}
}
