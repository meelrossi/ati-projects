package controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import components.OpenImage;
import components.SaveImage;
import filter.FilterButton;
import filter.FilterType;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ImageManager;

public class FilterTab extends Tab {
	
	@FXML
	private OpenImage image;
	@FXML
	private SaveImage result;
	@FXML
	private Button meanButton;
	@FXML
	private Button medianButton;
	@FXML
	private Button weightedMedianButton;
	@FXML
	private Button gaussianButton;
	@FXML
	private Button borderButton;
	@FXML
	private TextField textField1;
	@FXML
	private TextField textField2;
	@FXML
	private TextField windowSize;
	@FXML
	private Button calculateButton;
	
	private List<FilterButton> buttons;
	private FilterButton selectedButton;
	
	public FilterTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/filterTab.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void initialize() {
		image.initialize(this::calculateResult);
		buttons = new LinkedList<FilterButton>();
		buttons.add(new FilterButton(meanButton, FilterType.MEAN));
		buttons.add(new FilterButton(medianButton, FilterType.MEDIAN));
		buttons.add(new FilterButton(weightedMedianButton, FilterType.WEIGHTED_MEDIAN));
		buttons.add(new FilterButton(gaussianButton, FilterType.GAUSSIAN));
		buttons.add(new FilterButton(borderButton, FilterType.BORDER));
		buttons.forEach(fb -> {
			fb.getButton().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					setBackground(fb.getButton());
					selectedButton = fb;
				}
			});
		});
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				calculateResult();
			}
		});
	}
	
	public void setBackground(Button btn) {
		String notSelectedStyle = "-fx-background-color:white";
		String selectedStyle = "-fx-background-color:#1e90ff";
		buttons.forEach(nb -> {
			Button b = nb.getButton();
			b.setStyle(b != btn ? notSelectedStyle : selectedStyle);
		});
	}
	
	public void calculateResult(){
		ColorImage img = image.getImage();
		if (img != null) {
			result.setImage(selectedButton.getFilterType().getFilter(Double.parseDouble(textField1.getText())).filter(img, Integer.parseInt(windowSize.getText())));
		} 
	}

}
