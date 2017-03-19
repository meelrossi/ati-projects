package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import model.ColorImage;

public class ImageManager {
	private static int IMAGE_SIZE = 200;
	private static int SQUARE_L = 10;
	private static int CIRCLE_D = 160;

	public static BufferedImage getBinaryCircle() {
		BufferedImage circle = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g2d = circle.createGraphics();
		int size = (IMAGE_SIZE / 2);
		int radius = (CIRCLE_D / 2);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setPaint(Color.WHITE);
		g2d.fillOval(size - radius, size - radius, CIRCLE_D, CIRCLE_D);
		g2d.dispose();
		return circle;
	}

	public static BufferedImage getBinarySquare() {
		BufferedImage square = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_BYTE_BINARY);
		for (int i = 0; i < IMAGE_SIZE; i++) {
			for (int j = 0; j < IMAGE_SIZE; j++) {
				if (((i >= SQUARE_L && i <= (IMAGE_SIZE - SQUARE_L)) && j >= SQUARE_L && j <= IMAGE_SIZE - SQUARE_L)
						|| ((j >= SQUARE_L && j <= (IMAGE_SIZE - SQUARE_L)) && i >= SQUARE_L
								&& i <= IMAGE_SIZE - SQUARE_L)) {
					square.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		}
		return square;
	}

	public static ColorImage sum(ColorImage img1, ColorImage img2) {
		int img1_h = img1.getHeight();
		int img1_w = img1.getWidth();

		int img2_h = img2.getHeight();
		int img2_w = img2.getWidth();

		int width = Math.max(img1_w, img2_w);
		int height = Math.max(img1_h, img2_h);
		int[][] result_r = new int[width][height];
		int[][] result_g = new int[width][height];
		int[][] result_b = new int[width][height];

		int minRed = Integer.MAX_VALUE;
		int maxRed = 0;
		int minGreen = Integer.MAX_VALUE;
		int maxGreen = 0;
		int minBlue = Integer.MAX_VALUE;
		int maxBlue = 0;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int red = img1.getRed(i, j) + img2.getRed(i, j);
				minRed = Math.min(red, minRed);
				maxRed = Math.max(red, maxRed);
				result_r[i][j] = red;

				int green = img1.getGreen(i, j) + img2.getGreen(i, j);
				minGreen = Math.min(green, minGreen);
				maxGreen = Math.max(green, maxGreen);
				result_g[i][j] = green;

				int blue = img1.getBlue(i, j) + img2.getBlue(i, j);
				minBlue = Math.min(blue, minBlue);
				maxBlue = Math.max(blue, maxBlue);
				result_b[i][j] = blue;
			}
		}
		
		for (int k = 0; k < width; k++) {
			for (int r = 0; r < height; r++) {
				result_r[k][r] = (255 * result_r[k][r]) / (maxRed - minRed) - (255 * minRed) / (maxRed - minRed);
				result_g[k][r] = (255 * result_g[k][r]) / (maxGreen - minGreen) - (255 * minGreen) / (maxGreen - minGreen);
				result_b[k][r] = (255 * result_b[k][r]) / (maxBlue - minBlue) - (255 * minBlue) / (maxBlue - minBlue);
			}
		}
		return new ColorImage(result_r, result_g, result_b, width, height);
	}
	
	public static ColorImage sustraction(ColorImage img1, ColorImage img2) {
		int img1_h = img1.getHeight();
		int img1_w = img1.getWidth();

		int img2_h = img2.getHeight();
		int img2_w = img2.getWidth();

		int width = Math.max(img1_w, img2_w);
		int height = Math.max(img1_h, img2_h);
		int[][] result_r = new int[width][height];
		int[][] result_g = new int[width][height];
		int[][] result_b = new int[width][height];

		int minRed = Integer.MAX_VALUE;
		int maxRed = Integer.MIN_VALUE;
		int minGreen = Integer.MAX_VALUE;
		int maxGreen = Integer.MIN_VALUE;
		int minBlue = Integer.MAX_VALUE;
		int maxBlue = Integer.MIN_VALUE;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int red = img1.getRed(i, j) - img2.getRed(i, j);
				minRed = Math.min(red, minRed);
				maxRed = Math.max(red, maxRed);
				result_r[i][j] = red;

				int green = img1.getGreen(i, j) - img2.getGreen(i, j);
				minGreen = Math.min(green, minGreen);
				maxGreen = Math.max(green, maxGreen);
				result_g[i][j] = green;

				int blue = img1.getBlue(i, j) - img2.getBlue(i, j);
				minBlue = Math.min(blue, minBlue);
				maxBlue = Math.max(blue, maxBlue);
				result_b[i][j] = blue;
			}
		}
		
		for (int k = 0; k < width; k++) {
			for (int r = 0; r < height; r++) {
				result_r[k][r] = (255 * result_r[k][r]) / (maxRed - minRed) - (255 * minRed) / (maxRed - minRed);
				result_g[k][r] = (255 * result_g[k][r]) / (maxGreen - minGreen) - (255 * minGreen) / (maxGreen - minGreen);
				result_b[k][r] = (255 * result_b[k][r]) / (maxBlue - minBlue) - (255 * minBlue) / (maxBlue - minBlue);
			}
		}
		return new ColorImage(result_r, result_g, result_b, width, height);
	}
	
	public static ColorImage product(ColorImage img1, ColorImage img2) {
		int img1_h = img1.getHeight();
		int img1_w = img1.getWidth();

		int img2_h = img2.getHeight();
		int img2_w = img2.getWidth();

		int width = Math.max(img1_w, img2_w);
		int height = Math.max(img1_h, img2_h);
		int[][] result_r = new int[width][height];
		int[][] result_g = new int[width][height];
		int[][] result_b = new int[width][height];

		int minRed = Integer.MAX_VALUE;
		int maxRed = Integer.MIN_VALUE;
		int minGreen = Integer.MAX_VALUE;
		int maxGreen = Integer.MIN_VALUE;
		int minBlue = Integer.MAX_VALUE;
		int maxBlue = Integer.MIN_VALUE;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int red = img1.getRed(i, j) * img2.getRed(i, j);
				minRed = Math.min(red, minRed);
				maxRed = Math.max(red, maxRed);
				result_r[i][j] = red;

				int green = img1.getGreen(i, j) * img2.getGreen(i, j);
				minGreen = Math.min(green, minGreen);
				maxGreen = Math.max(green, maxGreen);
				result_g[i][j] = green;

				int blue = img1.getBlue(i, j) * img2.getBlue(i, j);
				minBlue = Math.min(blue, minBlue);
				maxBlue = Math.max(blue, maxBlue);
				result_b[i][j] = blue;
			}
		}
		
		for (int k = 0; k < width; k++) {
			for (int r = 0; r < height; r++) {
				result_r[k][r] = (255 * result_r[k][r]) / (maxRed - minRed) - (255 * minRed) / (maxRed - minRed);
				result_g[k][r] = (255 * result_g[k][r]) / (maxGreen - minGreen) - (255 * minGreen) / (maxGreen - minGreen);
				result_b[k][r] = (255 * result_b[k][r]) / (maxBlue - minBlue) - (255 * minBlue) / (maxBlue - minBlue);
			}
		}
		return new ColorImage(result_r, result_g, result_b, width, height);
	}
}
