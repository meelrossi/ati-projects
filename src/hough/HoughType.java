package hough;

import model.ColorImage;

public enum HoughType {
	LINEAR,
	CIRCULAR;
	
	public ColorImage getResult (ColorImage img, int steps, double epsilon, int titaSteps) {
		switch(this) {
		case LINEAR:
			LinealHough lh = new LinealHough();
			return lh.getImageWithLine(img, titaSteps, steps, epsilon);
		case CIRCULAR:
			CircularHough ch = new CircularHough();
			return ch.getImageWithLine(img, steps, epsilon);
		default:
			return null;
		}
	}
}
