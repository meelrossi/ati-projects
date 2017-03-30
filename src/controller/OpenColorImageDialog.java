package controller;

import javafx.scene.control.ChoiceDialog;
import model.ColorImageType;

public class OpenColorImageDialog extends ChoiceDialog<ColorImageType>{
	
	public OpenColorImageDialog () {
		super(ColorImageType.COLOR, ColorImageType.values());
		setTitle("Color Image");
		setHeaderText("Open Color Image");
		setContentText("Choose how to open image:");
	}

}
