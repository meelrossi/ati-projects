package controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

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
	@FXML
	private Button loadImageButton1;
	@FXML
	private ImageView image;
	@FXML
	private ImageView resultImage;
	
	private ColorImage img;
	private ColorImage result;
	private List<FilterButton> buttons;
	private FilterButton selectedButton;
	private FileChooser fileChooser = new FileChooser();
	
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
		loadImageButton1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					try {
						if (file.getName().toLowerCase().contains(".raw")) {
							OpenRawImageDialog dialog = new OpenRawImageDialog();
							Optional<Pair<Integer, Integer>> result = dialog.showAndWait();
							result.ifPresent(d -> {
							    img = ImageManager.readFromRaw(file, result.get().getKey(), result.get().getValue());
							});
						} else {
							OpenColorImageDialog dialog = new OpenColorImageDialog();
							Optional<ColorImageType> result = dialog.showAndWait();
							if (result.isPresent()){
								img = new ColorImage(ImageIO.read(file));
							    if (result.get() == ColorImageType.BLACK_AND_WHITE) {
							    	img.toBlackAndWhite();
							    }
							}
						}
						image.setImage(SwingFXUtils.toFXImage(img.getBufferedImage(), null));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (img != null) {
					result = selectedButton.getFilterType().getFilter(Double.parseDouble(textField1.getText())).filter(img, Integer.parseInt(windowSize.getText()));
					resultImage.setImage(SwingFXUtils.toFXImage(result.getBufferedImage(), null));
				} 
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

}
