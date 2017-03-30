package filter;

import model.ColorImage;

public abstract class Filter {

	public abstract ColorImage filter(ColorImage img, int windowSize);
}
