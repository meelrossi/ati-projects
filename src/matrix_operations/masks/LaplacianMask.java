package matrix_operations.masks;

public class LaplacianMask extends Mask {

	private static double[][] maskMatrix = new double[][]{
		{-1,-2,-1},
		{0,0,0},
		{1,2,1}
	};
	
	public LaplacianMask(){
		super(maskMatrix, 0);
	}
	
	
}
