package matrix_operations.masks.directional;

public class MisteryDirectionalMask extends DirectionalMask{

	private static double[][] misteryMask = new double[][]{
		{1,1,-1},
		{1,-2,-1},
		{1,1,-1}
	};
	
	public MisteryDirectionalMask(){
		super(misteryMask);
	}
	
	
}
