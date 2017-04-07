package utils;

import model.ColorImage;

public class ClipBoard {

	private static ColorImage clippedImage;
	
	public static void copyImage(ColorImage image) {
		clippedImage = image;
	}
	
	public static ColorImage pasteImage() {
		return clippedImage;
	}
	
}
