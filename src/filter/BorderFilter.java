package filter;

import matrix_operations.Mask;
import model.ColorImage;

public class BorderFilter extends Filter {
	
	@Override
	public ColorImage filter(ColorImage img, int windowSize) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		double[][] maskMatrix = new double[windowSize][windowSize];
		for(int i = 0; i < windowSize; i++){
			for(int j = 0; j < windowSize; j++){
				maskMatrix[i][j] = -1;
			}
		}
		maskMatrix[(windowSize-1)/2][(windowSize-1)/2] = windowSize*windowSize-1;
		Mask mask = new Mask(maskMatrix);
		double[][] red = mask.apply(img.getRedChannel());
		double[][] green = mask.apply(img.getGreenChannel());
		double[][] blue = mask.apply(img.getBlueChannel());
		ColorImage result = new ColorImage(red, green, blue, width, height);
		result.normalize();
		return result;
	}

}
