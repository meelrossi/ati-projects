package controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import components.OpenImage;
import components.SaveImage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import model.ColorImage;
import utils.ImageManager;

public class ImageWithHistogramPane extends Pane {
	
	@FXML
	protected OpenImage image;
	@FXML
	protected SaveImage result;
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
	
	private HistogramTab controller;
	private boolean isResult;
	
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
		this.isResult = isResult;
		this.controller = controller;
		imagePane.setVisible(false);
		histogramView.setVisible(false);
		image.setVisible(!isResult);
		result.setVisible(isResult);
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
		image.initialize(this::onLoadImage);
		result.initialize();
	}
	
	public void setImage(ColorImage cImage) {
		result.setImage(cImage);
		calculateHistograms();
	}

	@SuppressWarnings("unchecked")
	public void calculateHistograms() {
		ColorImage img = isResult ? result.getImage() : image.getImage();
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
	
	public void onLoadImage(){
		controller.setImage(image.getImage());
		calculateHistograms();
	}
}
