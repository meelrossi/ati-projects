package model;

import border.PrewittBorder;
import border.SobelBorder;

public enum BorderDetectorsType {
	PREWITT,
	SOBEL;
	
	public ColorImage getBorderImage(ColorImage img) {
		switch(this) {
		case PREWITT:
			return PrewittBorder.getBorderImage(img);
		case SOBEL:
			return SobelBorder.getBorderImage(img);
		default:
			return null;
		}
	}
	
	public ColorImage getPartialX(ColorImage img) {
		switch(this) {
		case PREWITT:
			return PrewittBorder.getParcialXImage(img);
		case SOBEL:
			return SobelBorder.getParcialXImage(img);
		default:
			return null;
		}
	}
	
	public ColorImage getPartialY(ColorImage img) {
		switch(this) {
		case PREWITT:
			return PrewittBorder.getParcialYImage(img);
		case SOBEL:
			return SobelBorder.getParcialYImage(img);
		default:
			return null;
		}
	}
}
