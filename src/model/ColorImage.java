package model;

import java.awt.image.BufferedImage;

import javafx.scene.paint.Color;
import utils.ImageManager;

public class ColorImage extends CustomImage {
	private double[][] red;
	private double[][] green;
	private double[][] blue;
	ColorImageType imageType;

	public ColorImage(double[][] red, double[][] green, double[][] blue, int width, int height) {
		super(width, height);
		this.red = red;
		this.green = green;
		this.blue = blue;
		imageType= ColorImageType.COLOR;
	}
	
	public ColorImage(double[][] grey, int width, int height) {
		super(width, height);
		this.red = new double[width][height];
		this.green = new double[width][height];
		this.blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = grey[i][j];
				green[i][j] = grey[i][j];
				blue[i][j] = grey[i][j];
			}
		}
		imageType= ColorImageType.BLACK_AND_WHITE;
		
	}

	public ColorImage(BufferedImage bufferedImage) {
		super(bufferedImage.getWidth(), bufferedImage.getHeight());
		red = new double[width][height];
		green = new double[width][height];
		blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Integer color = bufferedImage.getRGB(i, j);
				red[i][j] = (color >> 16) & 0xFF;
				green[i][j] = (color >> 8) & 0xFF;
				blue[i][j] = (color >> 0) & 0xFF;
			}
		}
	}

	public double getRed(int x, int y) {
		if (x >= width || y >= height) {
			return 0;
		}
		return red[x][y];
	}

	public double getGreen(int x, int y) {
		if (x >= width || y >= height) {
			return 0;
		}
		return green[x][y];
	}

	public double getBlue(int x, int y) {
		if (x >= width || y >= height) {
			return 0;
		}
		return blue[x][y];
	}
	
	public double getGrey(int x, int y) {
		return (this.getRed(x, y) + this.getGreen(x, y) + this.getBlue(x, y)) / 3;
	}

	public double[][] getRedChannel() {
		return red;
	}

	public double[][] getGreenChannel() {
		return green;
	}

	public double[][] getBlueChannel() {
		return blue;
	}

	public BufferedImage getBufferedImage() {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				java.awt.Color c = new java.awt.Color((int) red[i][j], (int) green[i][j], (int) blue[i][j]);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}

	public void setPixelColor(int x, int y, Color color) {
		red[x][y] = (int) (color.getRed() * 255);
		green[x][y] = (int) (color.getGreen() * 255);
		blue[x][y] = (int) (color.getBlue() * 255);
	}

	public Color getColor(int x, int y) {
		if (x < width && y < height) {
			return Color.rgb((int) red[x][y], (int) green[x][y], (int) blue[x][y]);
		}
		return Color.rgb(255, 255, 255);
	}

	public String getHexColor(int x, int y) {
		return String.format("#%02x%02x%02x", red[x][y], green[x][y], blue[x][y]);
	}

	public Color getAverageColor(int x, int y, int recWidth, int recHeight) {
		double avRed = 0;
		double avGreen = 0;
		double avBlue = 0;
		int size = recWidth * recHeight;
		for (int i = 0; i < recWidth; i++) {
			for (int j = 0; j < recHeight; j++) {
				if (x + i < width && y + j < height) {
					avRed += red[x + i][y + j];
					avGreen += green[x + i][y + j];
					avBlue += blue[x + i][y + j];

				}
			}
		}
		return Color.rgb((int) (avRed / size), (int) (avGreen / size), (int) (avBlue / size));
	}

	@Override
	public void normalize() {
		double minRed = Integer.MAX_VALUE;
		double maxRed = 0;
		double minGreen = Integer.MAX_VALUE;
		double maxGreen = 0;
		double minBlue = Integer.MAX_VALUE;
		double maxBlue = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double r = red[i][j];
				minRed = Math.min(r, minRed);
				maxRed = Math.max(r, maxRed);

				double g = green[i][j];
				minGreen = Math.min(g, minGreen);
				maxGreen = Math.max(g, maxGreen);

				double b = blue[i][j];
				minBlue = Math.min(b, minBlue);
				maxBlue = Math.max(b, maxBlue);
			}
		}

		for (int k = 0; k < width; k++) {
			for (int r = 0; r < height; r++) {
				red[k][r] = (255 * red[k][r]) / (maxRed - minRed) - (255 * minRed) / (maxRed - minRed);
				green[k][r] = (255 * green[k][r]) / (maxGreen - minGreen) - (255 * minGreen) / (maxGreen - minGreen);
				blue[k][r] = (255 * blue[k][r]) / (maxBlue - minBlue) - (255 * minBlue) / (maxBlue - minBlue);
			}
		}

	}

	public void compressionDinamicRange() {
		double maxRed = 0;
		double maxGreen = 0;
		double maxBlue = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double r = red[i][j];
				maxRed = Math.max(r, maxRed);

				double g = green[i][j];
				maxGreen = Math.max(g, maxGreen);

				double b = blue[i][j];
				maxBlue = Math.max(b, maxBlue);
			}
		}
		for (int k = 0; k < width; k++) {
			for (int r = 0; r < height; r++) {
				red[k][r] = ImageManager.compression(red[k][r], maxRed);
				green[k][r] = ImageManager.compression(green[k][r], maxGreen);
				blue[k][r] = ImageManager.compression(blue[k][r], maxBlue);
			}
		}
	}

	public ColorImage getNegative() {
		double[][] r = new double[width][height];
		double[][] g = new double[width][height];
		double[][] b = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				r[i][j] = 255 - red[i][j];
				g[i][j] = 255 - green[i][j];
				b[i][j] = 255 - blue[i][j];
			}
		}
		return new ColorImage(r, g, b, width, height);
	}
	
	public void toBlackAndWhite() {
		imageType = ColorImageType.BLACK_AND_WHITE;
		double[][] grey = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grey[i][j] = (red[i][j] + green[i][j] + blue[i][j]) / 3;
			}
		}
		red = grey;
		green = grey;
		blue = grey;
	}

	public ColorImageType colorImageType() {
		return this.imageType;
	}
}
