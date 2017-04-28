package matrix_operations.masks.directional;

public class SobelDirectionalMask extends DirectionalMask {

	private static double[][] sobelMask = new double[][]{
		{1,0,-1},
		{2,0,-2},
		{1,0,-1}
	};
	
	public SobelDirectionalMask(){
		super(sobelMask);
	}
	
}
