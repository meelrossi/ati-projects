package model;

import border.CannyBorder;
import border.DirectionalBorder;
import border.LaplacianBorder;
import border.LoGBorder;
import border.PrewittBorder;
import border.SobelBorder;
import harris.HarrisBorderDetector;
import matrix_operations.masks.directional.KirshDirectionalMask;
import matrix_operations.masks.directional.MisteryDirectionalMask;
import matrix_operations.masks.directional.PrewittDirectionalMask;
import matrix_operations.masks.directional.SobelDirectionalMask;

public enum BorderDetectorsType {
	PREWITT,
	SOBEL,
	PREWITT_DIRECTIONAL,
	SOBEL_DIRECTIONAL,
	MISTERY_DIRECTIONAL,
	KIRSH_DIRECTIONAL,
	LAPLACIAN,
	LOG,
	CANNY,
	HARRIS;
	
	public ColorImage getBorderImage(ColorImage img, double threshold, int m, double sigma) {
		switch(this) {
		case PREWITT:
			return PrewittBorder.getBorderImage(img);
		case SOBEL:
			return SobelBorder.getBorderImage(img);
		case PREWITT_DIRECTIONAL:
			return DirectionalBorder.getBorderImage(img, new PrewittDirectionalMask());
		case SOBEL_DIRECTIONAL:
			return DirectionalBorder.getBorderImage(img, new SobelDirectionalMask());
		case MISTERY_DIRECTIONAL:
			return DirectionalBorder.getBorderImage(img, new MisteryDirectionalMask());
		case KIRSH_DIRECTIONAL:
			return DirectionalBorder.getBorderImage(img, new KirshDirectionalMask());
		case LAPLACIAN:
			return LaplacianBorder.getBorderImage(img, threshold);
		case LOG:
			return LoGBorder.getBorderImage(img, threshold, m, sigma);
		case CANNY:
			return CannyBorder.getBorderImage(img, threshold, m);
		case HARRIS:
			return HarrisBorderDetector.getHarrisBorders(img);
		default:
			return null;
		}
	}
}
