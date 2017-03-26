package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
	private Button histogramColorButton;
	@FXML
	private Button contrastButton;

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
	@FXML
	private HistogramColorPane histogramColorPane;
	@FXML
	private ContrastPane contrastPane;
	@FXML
	private PowerPane powerPane;

	private List<ButtonPane> panes;

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
		panes = new LinkedList<ButtonPane>();
		panes.add(new ButtonPane(scalarProductButton, scalarProductImagePane));
		panes.add(new ButtonPane(negativeButton, negativeImagePane));
		panes.add(new ButtonPane(limitImageButton, limitImagePane));
		panes.add(new ButtonPane(limitImageWithColorButton, limitImageWithColorPane));
		panes.add(new ButtonPane(histogramColorButton, histogramColorPane));
		panes.add(new ButtonPane(sumButton, operationImagesPane, ImageOperation.SUM));
		panes.add(new ButtonPane(productButton, operationImagesPane, ImageOperation.PRODUCT));
		panes.add(new ButtonPane(substractionButton, operationImagesPane, ImageOperation.SUSTRACTION));
		panes.add(new ButtonPane(contrastButton, contrastPane));
		panes.add(new ButtonPane(powerButton, powerPane));

		panes.forEach((bp -> {
			bp.getPane().setVisible(false);
			bp.getButton().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (bp.getOperation() != null) {
						((OperationImagesPane) bp.getPane()).setOperation(bp.getOperation());
					}
					setVisible(bp.getPane());
					setBackground(bp.getButton());
				}
			});
		}));
	}

	public void setVisible(Pane pane) {
		panes.forEach(bp -> {
			Pane p = bp.getPane();
			p.setVisible(p == pane);
		});
	}

	public void setBackground(Button btn) {
		String notSelectedStyle = "-fx-background-color:white";
		String selectedStyle = "-fx-background-color:#1e90ff";
		panes.forEach(bp -> {
			Button b = bp.getButton();
			b.setStyle(b != btn ? notSelectedStyle : selectedStyle);
		});
	}
}
