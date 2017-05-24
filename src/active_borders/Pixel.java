package active_borders;

import javafx.scene.paint.Color;

public class Pixel {

	private int x;
	private int y;
	private PixelType type;
	
	public Pixel(int x, int y, PixelType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public PixelType getType() {
		return type;
	}

	public void setType(PixelType type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
		
	
	
}
