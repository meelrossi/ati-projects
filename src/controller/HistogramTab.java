package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import model.ColorImage;

public abstract class HistogramTab extends Tab {

	@FXML
	protected ImageWithHistogramPane mainImagePane;
	@FXML
	protected ImageWithHistogramPane resultImagePane;
	
	public abstract void setImage(ColorImage img);

}
