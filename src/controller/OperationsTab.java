package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class OperationsTab extends Tab {
	@FXML
	private Button sumButton;
	@FXML
	private Button substractionButton;
	@FXML
	private Button productButton;
	@FXML
	private Button scalarProductButton;
	@FXML
	private Button powerButton;
	@FXML
	private Button compressionButton;

	@FXML
	private SumImagesPane sumImagesPane;
	@FXML
	private SubstractImagesPane substractImagesPane;
	@FXML
	private ScalarProductImagePane scalarProductImagePane;
	@FXML
	private ProductImagesPane productImagesPane;

	public OperationsTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/operationsTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {
		sumImagesPane.setVisible(false);
		substractImagesPane.setVisible(false);
		scalarProductImagePane.setVisible(false);
		productImagesPane.setVisible(false);
		sumButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("dlaskjdalsjdkls");
				setVisible(sumImagesPane);
				setBackground(sumButton);
			}
		});
		productButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setVisible(productImagesPane);
				setBackground(productButton);
			}
		});
		substractionButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setVisible(substractImagesPane);
				setBackground(substractionButton);
			}
		});
		scalarProductButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setVisible(scalarProductImagePane);
				setBackground(scalarProductButton);
			}
		});
	}

	public void setVisible(Pane pane) {
		sumImagesPane.setVisible(sumImagesPane == pane);
		productImagesPane.setVisible(productImagesPane == pane);
		scalarProductImagePane.setVisible(scalarProductImagePane == pane);
		substractImagesPane.setVisible(substractImagesPane == pane);
	}

	public void setBackground(Button btn) {
		String style = "-fx-background-color:";
		sumButton.setStyle(btn != sumButton ? style + "white" : style + "#1e90ff");
		scalarProductButton.setStyle(btn != scalarProductButton ? style + "white" : style + "#1e90ff");
		productButton.setStyle(btn != productButton ? style + "white" : style + "#1e90ff");
		substractionButton.setStyle(btn != substractionButton ? style + "white" : style + "#1e90ff");
	}
}
