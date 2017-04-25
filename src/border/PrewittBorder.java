package border;

import matrix_operations.masks.MaskType;
import matrix_operations.masks.PrewittMask;
import matrix_operations.point_to_point.ModulePointToPointOperation;
import matrix_operations.point_to_point.PointToPointOperation;
import model.ColorImage;

public class PrewittBorder {

	public static ColorImage getBorderImage(ColorImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		
		PrewittMask xMask = new PrewittMask(MaskType.X);
		PrewittMask yMask = new PrewittMask(MaskType.Y);
		
		PointToPointOperation pointToPointOperation = new ModulePointToPointOperation();
		
		double[][] red = pointToPointOperation.apply(xMask.apply(image.getRedChannel()), yMask.apply(image.getRedChannel()));
		double[][] green = pointToPointOperation.apply(xMask.apply(image.getGreenChannel()), yMask.apply(image.getGreenChannel()));
		double[][] blue = pointToPointOperation.apply(xMask.apply(image.getBlueChannel()), yMask.apply(image.getBlueChannel()));
		
		return new ColorImage(red, green, blue, width, height);
	}
	
	public static ColorImage getParcialXImage(ColorImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		
		PrewittMask xMask = new PrewittMask(MaskType.X);
		
		double[][] red = xMask.apply(image.getRedChannel());
		double[][] green = xMask.apply(image.getGreenChannel());
		double[][] blue = xMask.apply(image.getBlueChannel());
		
		return new ColorImage(red, green, blue, width, height);
	}
	
	public static ColorImage getParcialYImage(ColorImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		
		PrewittMask yMask = new PrewittMask(MaskType.Y);
		
		double[][] red = yMask.apply(image.getRedChannel());
		double[][] green = yMask.apply(image.getGreenChannel());
		double[][] blue = yMask.apply(image.getBlueChannel());
		
		return new ColorImage(red, green, blue, width, height);
	}
	
	
}
