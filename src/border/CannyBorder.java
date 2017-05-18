package border;

import filter.GaussianFilter;
import matrix_operations.non_maximum_suppression.NonMaximumSuppressionAndSobel;
import matrix_operations.thresholdings.MeanAndStandardDeviationThreashholding;
import matrix_operations.thresholdings.ThreashholdingWithHystesis;
import model.ColorImage;

public class CannyBorder {

	public static ColorImage getBorderImage(ColorImage image, double stand, int windowSize){
		int width = image.getWidth();
		int height = image.getHeight();
		GaussianFilter filter = new GaussianFilter(stand);
		image = filter.filter(image, windowSize);		
		double[][] red = getBorderChannel(image.getRedChannel());
		double[][] green = getBorderChannel(image.getRedChannel());
		double[][] blue = getBorderChannel(image.getRedChannel());
		
		return new ColorImage(red, green, blue, width, height);
	}

	private static double[][] getBorderChannel(double[][] channel) {
		NonMaximumSuppressionAndSobel nonMaxAndSobel = new NonMaximumSuppressionAndSobel();
		double[][] result = nonMaxAndSobel.sobelAndSuppress(channel);
		MeanAndStandardDeviationThreashholding threashholder = new MeanAndStandardDeviationThreashholding(channel);
		return ThreashholdingWithHystesis.apply(result, threashholder.getT1(), threashholder.getT2());
	}
	
}
