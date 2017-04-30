package matrix_operations.masks.directional;

public class PrewittDirectionalMask extends DirectionalMask {

	private static double[][] prewittMask = new double[][]{
		{1,0,-1},
		{1,0,-1},
		{1,0,-1}
	};
	
	public PrewittDirectionalMask(){
		super(prewittMask);
	}
	
}
