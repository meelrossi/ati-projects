package border;

import matrix_operations.masks.directional.DirectionalMask;
import model.ColorImage;

public class DirectionalBorder {

	public static ColorImage getBorderImage(ColorImage image, DirectionalMask mask){
		int width = image.getWidth();
		int height = image.getHeight();
		
		double[][] red = mask.apply(image.getRedChannel());
		double[][] green = mask.apply(image.getGreenChannel());
		double[][] blue = mask.apply(image.getBlueChannel());
		
		return new ColorImage(red, green, blue, width, height);
	}
	
}
