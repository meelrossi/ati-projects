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
import javafx.scene.control.TextField;
import model.BorderDetectorsType;
import model.ColorImage;

public class BorderDetectorsTab extends Tab {
	@FXML
	private Button prewittButton;
	@FXML
	private Button sobelButton;
	@FXML
	private Button sobelDirectionalButton;
	@FXML
	private Button prewittDirectionalButton;
	@FXML
	private Button misteryDirectionalButton;
	@FXML
	private Button kirshDirectionalButton;
	@FXML
	private Button LoGButton;
	@FXML
	private Button laplacianButton;
	@FXML
	private Button cannyButton;

	@FXML
	private OpenImage image;
	@FXML
	private SaveImage resultImage;
	@FXML
	private Button calculateButton;
	@FXML
	private TextField thresholdLabel;
	@FXML
	private TextField mLabel;
	@FXML
	private TextField sigmaLabel;

	private List<EnumButton> buttons;
	private EnumButton selectedButton;

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
		buttons = new LinkedList<EnumButton>();
		buttons.add(new EnumButton(prewittButton, BorderDetectorsType.PREWITT));
		buttons.add(new EnumButton(sobelButton, BorderDetectorsType.SOBEL));
		buttons.add(new EnumButton(prewittDirectionalButton, BorderDetectorsType.PREWITT_DIRECTIONAL));
		buttons.add(new EnumButton(sobelDirectionalButton, BorderDetectorsType.SOBEL_DIRECTIONAL));
		buttons.add(new EnumButton(misteryDirectionalButton, BorderDetectorsType.MISTERY_DIRECTIONAL));
		buttons.add(new EnumButton(kirshDirectionalButton, BorderDetectorsType.KIRSH_DIRECTIONAL));
		buttons.add(new EnumButton(LoGButton, BorderDetectorsType.LOG));
		buttons.add(new EnumButton(laplacianButton, BorderDetectorsType.LAPLACIAN));
		buttons.add(new EnumButton(cannyButton, BorderDetectorsType.CANNY));
		buttons.forEach((bd -> {
			bd.getButton().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setBackground(bd.getButton());
					selectedButton = bd;
					thresholdLabel.setVisible(bd.getType() == BorderDetectorsType.LAPLACIAN
							|| bd.getType() == BorderDetectorsType.LOG || bd.getType() == BorderDetectorsType.CANNY);
					mLabel.setVisible(
							bd.getType() == BorderDetectorsType.LOG || bd.getType() == BorderDetectorsType.CANNY);
					sigmaLabel.setVisible(bd.getType() == BorderDetectorsType.LOG);
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
		if (image.getImage() == null)
			return;
		double threshold = !thresholdLabel.getText().equals("") ? Double.parseDouble(thresholdLabel.getText()) : 0;
		int m = !mLabel.getText().equals("") ? Integer.parseInt(mLabel.getText()) : 0;
		double sigma = !sigmaLabel.getText().equals("") ? Double.parseDouble(sigmaLabel.getText()) : 0;
		ColorImage result = ((BorderDetectorsType) selectedButton.getType()).getBorderImage(image.getImage(), threshold,
				m, sigma);
		result.normalize();
		resultImage.setImage(result);
	}

}
