package hough;

import java.util.LinkedList;
import java.util.List;

import model.ColorImage;
import utils.Pair;

public class CircularHough {
	private List<Pair> whitePoints;

	public CircularHough() {
	}

	public ColorImage getImageWithLine(ColorImage image, int rSteps, double epsilon) {
		getWhitePoints(image.getRedChannel());

		int[] circularScore = new int[rSteps];
		double[] rValues = new double[rSteps];

		double r1 = 0;
		double r2 = Math.max(image.getHeight(), image.getWidth()) * Math.sqrt(2);

		double rStep = (r2 - r1) / (rSteps - 1);
		rValues[0] = r1;
		for (int i = 1; i < rSteps; i++) {
			rValues[i] = rValues[i - 1] + rStep;
		}

		int xCenter = (image.getWidth() - 1) / 2;
		int yCenter = (image.getHeight() - 1) / 2;

		for (int i = 0; i < circularScore.length; i++) {
			for (Pair p : whitePoints) {
				if (belongsToCircle(rValues[i], epsilon, p.getX(), p.getY(), xCenter, yCenter)) {
					circularScore[i]++;
				}
			}
		}

		int maxCircle = getMax(circularScore);
		double circlesThreashhold = 0.8 * maxCircle;

		double[][] newImage = cloneMatrix(image.getRedChannel());

		for (int i = 0; i < circularScore.length; i++) {
			if (circularScore[i] > circlesThreashhold) {
				System.out.println(circularScore[i]);
				drawCircle(rValues[i], xCenter, yCenter, newImage);
			}

		}

		return new ColorImage(newImage, image.getWidth(), image.getHeight());
	}

	private boolean belongsToCircle(double r, double epsilon, int x, int y, int xCenter, int yCenter) {
		return Math.abs((Math.pow(x - xCenter, 2) + Math.pow(y - yCenter, 2) - Math.pow(r, 2))) <= epsilon;
	}

	private void drawCircle(double r, int xCenter, int yCenter, double[][] newImage) {
		for (int x = 0; x < newImage.length; x++) {
			for (int y = 0; y < newImage[0].length; y++) {
				if (belongsToCircle(r, 200, x, y, xCenter, yCenter)) {
					newImage[x][y] = 128;
				}
			}
		}
	}

	private double[][] cloneMatrix(double[][] matrix) {
		double[][] newMatrix = new double[matrix.length][matrix[0].length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				newMatrix[i][j] = matrix[i][j];
			}
		}

		return newMatrix;
	}

	private int getMax(int[] array) {
		int max = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}

	private void getWhitePoints(double[][] matrix) {
		whitePoints = new LinkedList<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 255) {
					whitePoints.add(new Pair(i, j));
				}
			}
		}
	}

}
