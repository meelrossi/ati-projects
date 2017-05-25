package border;

import java.util.LinkedList;
import java.util.List;

import model.ColorImage;
import utils.Pair;

public class SusanBorderAndCorner {

	private int t = 27;
	private double tolerance = 0.1;
	private static int MASK_ONES = 37;
	private static int MASK_SIZE = 7;
	private static int[][] mask = new int[][] { { 0, 0, 1, 1, 1, 0, 0 }, { 0, 1, 1, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 1, 1, 0 },
			{ 0, 0, 1, 1, 1, 0, 0 } };

	public SusanBorderAndCorner() {
	}

	public SusanBorderAndCorner(int t, double tolerance) {
		this.t = t;
		this.tolerance = tolerance;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	private List<Pair> getCornerList(double[][] matrix) {
		return getPairsAround(matrix, 0.75);
	}

	private List<Pair> getBorderList(double[][] matrix) {
		return getPairsAround(matrix, 0.5);
	}

	private List<Pair> getPairsAround(double[][] matrix, double around) {
		List<Pair> points = new LinkedList<Pair>();
		for (int i = 3; i < matrix.length - 3; i++) {
			for (int j = 3; j < matrix[0].length - 3; j++) {
				if (isARemarkablePair(i, j, matrix, around)) {
					points.add(new Pair(i, j));
				}
			}
		}
		return points;
	}

	private boolean isARemarkablePair(int x, int y, double[][] matrix, double around) {
		int count = 0;

		for (int i = 0; i < MASK_SIZE; i++) {
			for (int j = 0; j < MASK_SIZE; j++) {
				if (mask[i][j] == 1) {
					if (Math.abs(matrix[x][y] - matrix[x + i - 3][y + j - 3]) < t) {
						count++;
					}
				}
			}
		}
		double s = (1 - ((double) count) / ((double) MASK_ONES));
		return s <= (around + tolerance) && s >= (around - tolerance);
	}

	public ColorImage markBorders(ColorImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];

		double[][] sampleChannel = img.getRedChannel();

		List<Pair> borders = getBorderList(sampleChannel);
		System.out.println(borders.size());

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = sampleChannel[i][j];
				green[i][j] = sampleChannel[i][j];
				blue[i][j] = sampleChannel[i][j];
			}
		}

		for (Pair p : borders) {
			red[p.getX()][p.getY()] = 255;
			green[p.getX()][p.getY()] = 0;
			blue[p.getX()][p.getY()] = 0;
		}

		return new ColorImage(red, green, blue, width, height);
	}

	public ColorImage markCorners(ColorImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];

		double[][] sampleChannel = img.getRedChannel();

		List<Pair> corners = getCornerList(sampleChannel);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = sampleChannel[i][j];
				green[i][j] = sampleChannel[i][j];
				blue[i][j] = sampleChannel[i][j];
			}
		}

		for (Pair p : corners) {
			red[p.getX()][p.getY()] = 0;
			green[p.getX()][p.getY()] = 255;
			blue[p.getX()][p.getY()] = 0;
		}

		return new ColorImage(red, green, blue, width, height);
	}

}
