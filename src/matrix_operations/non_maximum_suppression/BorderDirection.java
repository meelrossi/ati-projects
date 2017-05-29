package matrix_operations.non_maximum_suppression;

public enum BorderDirection {
	VERTICAL(0,1),HORIZONTAL(1,0),ASCENDING(1,1),DESCENDING(1,-1),INCALCULABLE(0,0);
	
	private int deltaX;
	private int deltaY;
	
	private BorderDirection(int deltaX, int deltaY){
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}
	
	
}
