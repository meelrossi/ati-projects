package border;

import matrix_operations.masks.MaskType;
import matrix_operations.masks.PrewittMask;
import matrix_operations.masks.SobelMask;
import matrix_operations.point_to_point.ModulePointToPointOperation;
import matrix_operations.point_to_point.PointToPointOperation;
import model.ColorImage;

public class SobelBorder {

	public static ColorImage getBorderImage(ColorImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		
		SobelMask xMask = new SobelMask(MaskType.X);
		SobelMask yMask = new SobelMask(MaskType.Y);
		
		PointToPointOperation pointToPointOperation = new ModulePointToPointOperation();
		
		double[][] red = pointToPointOperation.apply(xMask.apply(image.getRedChannel()), yMask.apply(image.getRedChannel()));
		double[][] green = pointToPointOperation.apply(xMask.apply(image.getGreenChannel()), yMask.apply(image.getGreenChannel()));
		double[][] blue = pointToPointOperation.apply(xMask.apply(image.getBlueChannel()), yMask.apply(image.getBlueChannel()));
		
		return new ColorImage(red, green, blue, width, height);
	}
	
	public static ColorImage getParcialXImage(ColorImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		
		SobelMask xMask = new SobelMask(MaskType.X);
		
		double[][] red = xMask.apply(image.getRedChannel());
		double[][] green = xMask.apply(image.getGreenChannel());
		double[][] blue = xMask.apply(image.getBlueChannel());
		
		return new ColorImage(red, green, blue, width, height);
	}
	
	public static ColorImage getParcialYImage(ColorImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		
		SobelMask yMask = new SobelMask(MaskType.Y);
		
		double[][] red = yMask.apply(image.getRedChannel());
		double[][] green = yMask.apply(image.getGreenChannel());
		double[][] blue = yMask.apply(image.getBlueChannel());
		
		return new ColorImage(red, green, blue, width, height);
	}
	
	
}
