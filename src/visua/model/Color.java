package visua.model;

public class Color implements Comparable{

	private double r;
	private double g;
	private double b;
	
	public Color(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(b);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(g);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(r);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Color other = (Color) obj;
		if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
			return false;
		if (Double.doubleToLongBits(g) != Double.doubleToLongBits(other.g))
			return false;
		if (Double.doubleToLongBits(r) != Double.doubleToLongBits(other.r))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		visua.model.Color colorO = (visua.model.Color) o;
		float[] hsvThis = new float[3];
		float[] hsvOther = new float[3];
		java.awt.Color.RGBtoHSB((int)r,(int)g,(int)b,hsvThis);
		java.awt.Color.RGBtoHSB((int)colorO.getR(), (int)colorO.getG(), (int)colorO.getB(), hsvOther);
		return (int)(hsvThis[0]*1000 - hsvOther[0]*1000);
	}
	
	public static void main(String[] args) {
		Color a = new Color(10.0,20.0,30.0);
		Color b = new Color(10.0,20.0,30.0);
		System.out.println(a.equals(a));
		System.out.println(b.equals(a));
	}
	
	
	
}
