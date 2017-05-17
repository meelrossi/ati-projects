package border;

import filter.GaussianFilter;
import matrix_operations.non_maximum_suppression.NonMaximumSuppressionAndSobel;
import matrix_operations.thresholdings.ThreashholdingWithHystesis;
import model.ColorImage;

public class CannyBorder {

	public static ColorImage getBorderImage(ColorImage image, double stand, int windowSize, int t1, int t2){
		int width = image.getWidth();
		int height = image.getHeight();
		GaussianFilter filter = new GaussianFilter(stand);
		image = filter.filter(image, windowSize);		
		double[][] red = getBorderChannel(image.getRedChannel(),t1,t2);
		double[][] green = getBorderChannel(image.getRedChannel(),t1,t2);
		double[][] blue = getBorderChannel(image.getRedChannel(),t1,t2);
		
		return new ColorImage(red, green, blue, width, height);
	}

	private static double[][] getBorderChannel(double[][] channel,int t1, int t2) {
		NonMaximumSuppressionAndSobel nonMaxAndSobel = new NonMaximumSuppressionAndSobel();
		double[][] result = nonMaxAndSobel.sobelAndSuppress(channel);
		return ThreashholdingWithHystesis.apply(result, t1, t2);
	}
	
}
