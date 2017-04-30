package controller.tab;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import components.EnumButton;
import components.view.OpenImage;
import components.view.SaveImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import matrix_operations.thresholdings.ThresholdType;
import model.ColorImage;

public class ThresholdTab extends Tab {
	@FXML
	private Button globalButton;
	@FXML
	private Button otsuButton;
	@FXML
	private OpenImage image;
	@FXML
	private SaveImage resultImage;

	private List<EnumButton> buttons;
	private EnumButton selectedButton;

	public ThresholdTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/thresholdTab.fxml"));
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
		buttons = new LinkedList<EnumButton>();
		buttons.add(new EnumButton(globalButton, ThresholdType.GLOBAL));
		buttons.add(new EnumButton(otsuButton, ThresholdType.OTSU));
		buttons.forEach((bd -> {
			bd.getButton().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setBackground(bd.getButton());
					selectedButton = bd;
					checkResults();
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
		if (image.getImage() == null) return;
		ColorImage result = ((ThresholdType)selectedButton.getType()).getImageWithThreshold(image.getImage());
		result.normalize();
		resultImage.setImage(result);
	}

}
