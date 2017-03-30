package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ImageManager;

public class ImageWithHistogramPane extends Pane {
	@FXML
	protected Button loadImageButton;
	@FXML
	protected ImageView image;
	@FXML
	private Button saveImageButton;
	@FXML
	private BarChart<String, Number> chart;
	@FXML
	private BarChart<String, Number> chartRed;
	@FXML
	private BarChart<String, Number> chartGreen;
	@FXML
	private BarChart<String, Number> chartBlue;
	@FXML
	private Button imageButton;
	@FXML
	private Button histogramButton;
	@FXML
	private TabPane histogramView;
	@FXML
	private Pane imagePane;

	protected ColorImage img;
	protected FileChooser fileChooser = new FileChooser();

	public ImageWithHistogramPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getResource());
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public URL getResource() {
		return getClass().getResource("/view/imageWithHistogramPane.fxml");
	}

	public void initialize(HistogramTab controller, boolean isResult) {
		imagePane.setVisible(false);
		histogramView.setVisible(false);
		loadImageButton.setVisible(!isResult);
		saveImageButton.setVisible(isResult);
		imageButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				imagePane.setVisible(true);
				histogramView.setVisible(false);
				setBackground(imageButton);
			}
		});
		histogramButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				imagePane.setVisible(false);
				histogramView.setVisible(true);
				setBackground(histogramButton);
			}
		});
		loadImageButton.setOnAction(new EventHandler<ActionEvent>() {
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
						controller.setImage(img);
						calculateHistograms();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		saveImageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(JavaFXApplication.primaryStage);
				if (file != null) {
					img.saveOn(file);
				}
			}
		});
	}
	
	public void setImage(ColorImage cImage) {
		img = cImage;
		image.setImage(SwingFXUtils.toFXImage(cImage.getBufferedImage(), null));
		calculateHistograms();
	}

	@SuppressWarnings("unchecked")
	public void calculateHistograms() {
		resetCharts();
		chartRed.getData().addAll(ImageManager.getHistogramSeries(img.getRedChannel(), img.getWidth(), img.getHeight()));
		chartRed.setLegendVisible(false);
		for (Node n : chartRed.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: red;");
		}

		chartGreen.getData()
				.addAll(ImageManager.getHistogramSeries(img.getGreenChannel(), img.getWidth(), img.getHeight()));
		chartGreen.setLegendVisible(false);
		for (Node n : chartGreen.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: green;");
		}

		chartBlue.getData()
				.addAll(ImageManager.getHistogramSeries(img.getBlueChannel(), img.getWidth(), img.getHeight()));
		chartBlue.setLegendVisible(false);
		for (Node n : chartBlue.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: blue;");
		}

		chart.getData().addAll(ImageManager.getHistogramSeries(img.getRedChannel(), img.getWidth(), img.getHeight()));
		chart.getData().addAll(ImageManager.getHistogramSeries(img.getGreenChannel(), img.getWidth(), img.getHeight()));
		chart.getData().addAll(ImageManager.getHistogramSeries(img.getBlueChannel(), img.getWidth(), img.getHeight()));
		chart.setLegendVisible(false);
		for (Node n : chart.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: red;");
		}
		for (Node n : chart.lookupAll(".default-color1.chart-bar")) {
			n.setStyle("-fx-bar-fill: green;");
		}
		for (Node n : chart.lookupAll(".default-color2.chart-bar")) {
			n.setStyle("-fx-bar-fill: blue;");
		}
	}

	public void resetCharts() {
		chart.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
		chartRed.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
		chartGreen.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
		chartBlue.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
	}

	public void setBackground(Button btn) {
		String notSelectedStyle = "-fx-background-color:white";
		String selectedStyle = "-fx-background-color:#1e90ff";
		histogramButton.setStyle(histogramButton != btn ? notSelectedStyle : selectedStyle);
		imageButton.setStyle(imageButton != btn ? notSelectedStyle : selectedStyle);
	}
}
