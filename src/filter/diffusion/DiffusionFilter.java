package filter.diffusion;

import filter.Filter;
import model.ColorImage;

public abstract class DiffusionFilter extends Filter {

	private Function c;
	private double LAMBDA = 0.25;

	public DiffusionFilter(Function c) {
		this.c = c;
	}

	@Override
	public ColorImage filter(ColorImage img, int iterations) {
		int width = img.getWidth();
		int height = img.getHeight();

		double[][] red = img.getRedChannel();
		double[][] green = img.getGreenChannel();
		double[][] blue = img.getBlueChannel();

		for (int i = 0; i < iterations; i++) {
			red = nextIteration(red);
			green = nextIteration(green);
			blue = nextIteration(blue);
		}
		
		ColorImage result = new ColorImage(red, green, blue, width, height);
		result.normalize();
		return result;
	}

	private double[][] nextIteration(double[][] matrix) {
		double[][] result = new double[matrix.length][matrix[0].length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (j == 0 || j == matrix[0].length - 1 || i == 0 || i == matrix.length - 1) {
					result[i][j] = matrix[i][j];
				} else {
					result[i][j] = nextValue(matrix, i, j);
				}
			}
		}

		return result;
	}

	private double nextValue(double[][] matrix, int i, int j) {
		double prevValue = matrix[i][j];

		double dN = matrix[i + 1][j] - prevValue;
		double dS = matrix[i - 1][j] - prevValue;
		double dE = matrix[i][j + 1] - prevValue;
		double dO = matrix[i][j - 1] - prevValue;

		double cN = c.apply(dN);
		double cS = c.apply(dS);
		double cE = c.apply(dE);
		double cO = c.apply(dO);


		return prevValue + LAMBDA * (dN * cN + dS * cS + dE * cE + dO * cO);
	}

}
