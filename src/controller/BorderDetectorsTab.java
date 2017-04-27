package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import components.OpenImage;
import components.SaveImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import model.BorderDetectorButton;
import model.BorderDetectorsType;
import model.ColorImage;

public class BorderDetectorsTab extends Tab {
	@FXML
	private Button prewittButton;
	@FXML
	private Button sobelButton;
	@FXML
	private OpenImage image;

	@FXML
	private SaveImage borderImage;
	@FXML
	private SaveImage xImage;
	@FXML
	private SaveImage yImage;
	
	private List<BorderDetectorButton> buttons;
	private BorderDetectorButton selectedButton;

	public BorderDetectorsTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/borderDetectorsTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void initialize() {
		image.initialize(this::checkResults);
		buttons = new LinkedList<BorderDetectorButton>();
		buttons.add(new BorderDetectorButton(prewittButton, BorderDetectorsType.PREWITT));
		buttons.add(new BorderDetectorButton(sobelButton, BorderDetectorsType.SOBEL));
		buttons.forEach((bd -> {
			bd.getButton().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setBackground(bd.getButton());
					selectedButton = bd;
				}
			});
		}));
		selectedButton = buttons.get(0);
	}

	public void setBackground(Button btn) {
		String notSelectedStyle = "-fx-background-color:#F0F8FF";
		String selectedStyle = "-fx-background-color:#1e90ff";
		buttons.forEach(nb -> {
			Button b = nb.getButton();
			b.setStyle(b != btn ? notSelectedStyle : selectedStyle);
		});
	}
	
	public void checkResults() {
		ColorImage result = selectedButton.getType().getBorderImage(image.getImage());
		result.normalize();
		borderImage.setImage(result);
		ColorImage x = selectedButton.getType().getPartialX(image.getImage());
		x.normalize();
		xImage.setImage(x);
		ColorImage y = selectedButton.getType().getPartialY(image.getImage());
		y.normalize();
		yImage.setImage(y);
	}

}
