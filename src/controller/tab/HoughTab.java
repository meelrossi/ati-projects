package controller.tab;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import components.EnumButton;
import components.view.OpenImage;
import components.view.SaveImage;
import hough.HoughType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import model.ColorImage;

public class HoughTab extends Tab {
	@FXML
	private Button linearButton;
	@FXML
	private Button circularButton;
	@FXML
	private OpenImage image;
	@FXML
	private SaveImage resultImage;
	@FXML
	private TextField stepsField;
	@FXML
	private TextField titaStepsField;
	@FXML
	private TextField epsilonField;
	@FXML
	private Button calculateButton;

	private List<EnumButton> buttons;
	private EnumButton selectedButton;

	public HoughTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/houghTab.fxml"));
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
		buttons.add(new EnumButton(linearButton, HoughType.LINEAR));
		buttons.add(new EnumButton(circularButton, HoughType.CIRCULAR));
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
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				checkResults();
			}
		});
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
		ColorImage result = ((HoughType)selectedButton.getType()).getResult(image.getImage(), Integer.parseInt(stepsField.getText()), Double.parseDouble(epsilonField.getText()), Integer.parseInt(titaStepsField.getText()));
		resultImage.setImage(result);
	}

}
