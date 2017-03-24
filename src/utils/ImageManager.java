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

	public static ColorImage calculate(ColorImage img1, ColorImage img2, ImageOperation op) {
		int img1_h = img1.getHeight();
		int img1_w = img1.getWidth();

		int img2_h = img2.getHeight();
		int img2_w = img2.getWidth();

		int width = Math.max(img1_w, img2_w);
		int height = Math.max(img1_h, img2_h);
		double[][] result_r = new double[width][height];
		double[][] result_g = new double[width][height];
		double[][] result_b = new double[width][height];


		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				result_r[i][j] = op.apply(img1.getRed(i, j), img2.getRed(i, j));
				result_g[i][j] = op.apply(img1.getGreen(i, j), img2.getGreen(i, j));
				result_b[i][j] = op.apply(img1.getBlue(i, j), img2.getBlue(i, j));
			}
		}
		ColorImage img = new ColorImage(result_r, result_g, result_b, width, height);
		img.normalize();
		return img;
	}
	
	public static ColorImage scalarProduct(ColorImage img, double scalar) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] result_r = new double[width][height];
		double[][] result_g = new double[width][height];
		double[][] result_b = new double[width][height];


		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				result_r[i][j] = img.getRed(i, j) * scalar;
				result_g[i][j] = img.getGreen(i, j) * scalar;
				result_b[i][j] = img.getBlue(i, j) * scalar;
			}
		}
		ColorImage result = new ColorImage(result_r, result_g, result_b, width, height);
		result.compressionDinamicRange();
		return result;
	}
	
	public static double compression(double r, double R) {
		double c = 255 / Math.log(1 + R);
		return c * Math.log(1 + r);
	}
	
	public static ColorImage limitImage(ColorImage img, double limit) {
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				if(img.getGrey(i, j) < limit) {
					result.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		}
		return new ColorImage(result);
	}
	
	public static ColorImage limitImageWithColor(ColorImage img, double limit) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] result_r = new double[width][height];
		double[][] result_g = new double[width][height];
		double[][] result_b = new double[width][height];

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				result_r[i][j] = img.getRed(i, j) < limit ? 0 : 255;
				result_g[i][j] = img.getGreen(i, j) < limit ? 0 : 255;
				result_b[i][j] = img.getBlue(i, j) < limit ? 0 : 255;
			}
		}
		return new ColorImage(result_r, result_g, result_b, width, height);
	}
}
