package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import utils.ImageOperation;

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
	private Button negativeButton;
	@FXML
	private Button limitImageButton;
	@FXML
	private Button limitImageWithColorButton;

	@FXML
	private OperationImagesPane operationImagesPane;
	@FXML
	private ScalarProductImagePane scalarProductImagePane;
	@FXML
	private NegativeImagePane negativeImagePane;
	@FXML
	private LimitImagePane limitImagePane;
	@FXML
	private LimitImageWithColorPane limitImageWithColorPane;

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
		scalarProductImagePane.setVisible(false);
		operationImagesPane.setVisible(false);
		negativeImagePane.setVisible(false);
		limitImagePane.setVisible(false);
		limitImageWithColorPane.setVisible(false);
		sumButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				operationImagesPane.setOperation(ImageOperation.SUM);
				setVisible(operationImagesPane);
				setBackground(sumButton);
			}
		});
		productButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				operationImagesPane.setOperation(ImageOperation.PRODUCT);
				setVisible(operationImagesPane);
				setBackground(productButton);
			}
		});
		substractionButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				operationImagesPane.setOperation(ImageOperation.SUSTRACTION);
				setVisible(operationImagesPane);
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
		negativeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setVisible(negativeImagePane);
				setBackground(negativeButton);
			}
		});
		limitImageWithColorButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setVisible(limitImageWithColorPane);
				setBackground(limitImageWithColorButton);				
			}
		});
		limitImageButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setVisible(limitImagePane);
				setBackground(limitImageButton);				
			}
		});
	}

	public void setVisible(Pane pane) {
		operationImagesPane.setVisible(operationImagesPane == pane);
		scalarProductImagePane.setVisible(scalarProductImagePane == pane);
		negativeImagePane.setVisible(negativeImagePane == pane);
		limitImagePane.setVisible(limitImagePane == pane);
		limitImageWithColorPane.setVisible(limitImageWithColorPane == pane);
	}

	public void setBackground(Button btn) {
		String style = "-fx-background-color:";
		sumButton.setStyle(btn != sumButton ? style + "white" : style + "#1e90ff");
		scalarProductButton.setStyle(btn != scalarProductButton ? style + "white" : style + "#1e90ff");
		productButton.setStyle(btn != productButton ? style + "white" : style + "#1e90ff");
		substractionButton.setStyle(btn != substractionButton ? style + "white" : style + "#1e90ff");
		negativeButton.setStyle(btn != negativeButton ? style + "white" : style + "#1e90ff");
		limitImageButton.setStyle(btn != limitImageButton ? style + "white" : style + "#1e90ff");
		limitImageWithColorButton.setStyle(btn != limitImageWithColorButton ? style + "white" : style + "#1e90ff");
	}
}
