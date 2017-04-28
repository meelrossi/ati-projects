package matrix_operations.masks;

public class PrewittMask extends Mask {

	private static double[][] yMask = new double[][]{
		{1,1,1},
		{0,0,0},
		{-1,-1,-1}
	};
	private static double[][] xMask = new double[][]{
		{1,0,-1},
		{1,0,-1},
		{1,0,-1}
	};
	
	public PrewittMask(MaskType maskType){
		super(getMaskMatrix(maskType), 0);
	}

	private static double[][] getMaskMatrix(MaskType maskType) {
		switch (maskType) {
		case X:
			return xMask;
		case Y:
			return yMask;
		default:
			return new double[3][3];
		}
	}
	
	
}
