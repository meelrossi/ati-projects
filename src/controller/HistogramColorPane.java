package controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import javax.imageio.ImageIO;

import components.OpenImage;
import components.SaveImage;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.ColorImage;
import model.ColorImageType;
import utils.ImageManager;

public class HistogramColorPane extends Pane {
	
	@FXML
	protected OpenImage image;
	@FXML
	private BarChart<String, Number> chart;
	@FXML
	private BarChart<String, Number> chartRed;
	@FXML
	private BarChart<String, Number> chartGreen;
	@FXML
	private BarChart<String, Number> chartBlue;

	public HistogramColorPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/histogramColorPane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void initialize() {
		image.initialize(this::calculateHistograms);
	}
	
	@SuppressWarnings("unchecked")
	public void calculateHistograms() {
		resetCharts();
		ColorImage img = image.getImage();
        chartRed.getData().addAll(ImageManager.getHistogramSeries(img.getRedChannel(), img.getWidth(), img.getHeight()));
        chartRed.setLegendVisible(false);
        for(Node n:chartRed.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: red;");
        }

		
        chartGreen.getData().addAll(ImageManager.getHistogramSeries(img.getGreenChannel(), img.getWidth(), img.getHeight()));
        chartGreen.setLegendVisible(false);
        for(Node n:chartGreen.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: green;");
        }

		
        chartBlue.getData().addAll(ImageManager.getHistogramSeries(img.getBlueChannel(), img.getWidth(), img.getHeight()));
        chartBlue.setLegendVisible(false);
        for(Node n:chartBlue.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: blue;");
        }
        
		chart.getData().addAll(ImageManager.getHistogramSeries(img.getRedChannel(), img.getWidth(), img.getHeight()));
		chart.getData().addAll(ImageManager.getHistogramSeries(img.getGreenChannel(), img.getWidth(), img.getHeight()));
		chart.getData().addAll(ImageManager.getHistogramSeries(img.getBlueChannel(), img.getWidth(), img.getHeight()));
		chart.setLegendVisible(false);
        for(Node n:chart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: red;");
        }
        for(Node n:chart.lookupAll(".default-color1.chart-bar")) {
            n.setStyle("-fx-bar-fill: green;");
        }
        for(Node n:chart.lookupAll(".default-color2.chart-bar")) {
            n.setStyle("-fx-bar-fill: blue;");
        }
	}
	
	public void resetCharts() {
		chart.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
		chartRed.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
		chartGreen.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
		chartBlue.setData(FXCollections.observableList(new LinkedList<XYChart.Series<String, Number>>()));
	}

}
