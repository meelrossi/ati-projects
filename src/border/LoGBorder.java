package border;

import matrix_operations.ZeroCrossing;
import matrix_operations.masks.LoGMask;
import model.ColorImage;

public class LoGBorder {
	
	public static ColorImage getBorderImage(ColorImage image, double threashold,int m, double sigma){
		
		LoGMask mask = new LoGMask(m,sigma);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		double[][] red = ZeroCrossing.process(mask.apply(image.getRedChannel()), threashold);
		double[][] green = ZeroCrossing.process(mask.apply(image.getGreenChannel()), threashold);
		double[][] blue = ZeroCrossing.process(mask.apply(image.getBlueChannel()), threashold);
		
		return new ColorImage(red, green, blue, width, height);
	}
	

}
