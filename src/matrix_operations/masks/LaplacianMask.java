package matrix_operations.masks;

public class LaplacianMask extends Mask {

	private static double[][] maskMatrix = new double[][]{
		{0,-1,0},
		{-1,4,-1},
		{0,-1,0}
	};
	
	public LaplacianMask(){
		super(maskMatrix, 0);
	}
	
	
}
