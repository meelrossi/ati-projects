package matrix_operations.masks.directional;

public class KirshDirectionalMask extends DirectionalMask {

	private static double[][] kirshMask = new double[][]{
		{5,-3,-3},
		{5,0,-3},
		{5,-3,-3}
	};
	
	public KirshDirectionalMask(){
		super(kirshMask);
	}
	
}
