package active_borders;

public enum PixelType {
	BORDER_IN(-1), BORDER_OUT(1), IN(-3), OUT(3);
	
	private int phi;
	
	PixelType(int phi){
		this.phi = phi;
	}
	
	public int getPhi(){
		return phi;
	}
	
	
	
}
