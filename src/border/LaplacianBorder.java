package border;

import matrix_operations.ZeroCrossing;
import matrix_operations.masks.LaplacianMask;
import model.ColorImage;

public class LaplacianBorder {

	public static ColorImage getBorderImage(ColorImage image){
		return getBorderImage(image, 0);
	}
	
	public static ColorImage getBorderImage(ColorImage image, double threashold){
		
		LaplacianMask mask = new LaplacianMask();
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		double[][] red = ZeroCrossing.process(mask.apply(image.getRedChannel()), threashold);
		double[][] green = ZeroCrossing.process(mask.apply(image.getGreenChannel()), threashold);
		double[][] blue = ZeroCrossing.process(mask.apply(image.getBlueChannel()), threashold);
		
		return new ColorImage(red, green, blue, width, height);
	}
	
	
}
