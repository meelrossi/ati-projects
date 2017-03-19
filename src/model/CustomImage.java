package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;

public class CustomImage {
	private int[][] red;
	private int[][] green;
	private int[][] blue;
	private int width;
	private int height;

	public CustomImage(int[][] red, int[][] green, int[][] blue, int width, int height) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.width = width;
		this.height = height;
	}

	public CustomImage(BufferedImage bufferedImage) {
		width = bufferedImage.getWidth();
		height = bufferedImage.getHeight();
		red = new int[width][height];
		green = new int[width][height];
		blue = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Integer color = bufferedImage.getRGB(i, j);
				red[i][j] = (color >> 16) & 0xFF;
				green[i][j] = (color >> 8) & 0xFF;
				blue[i][j] = (color >> 0) & 0xFF;
			}
		}
	}

	public int getRed(int x, int y) {
		if (x >= width || y >= height) {
			return 0;
		}
		return red[x][y];
	}

	public int getGreen(int x, int y) {
		if (x >= width || y >= height) {
			return 0;
		}
		return green[x][y];
	}

	public int getBlue(int x, int y) {
		if (x >= width || y >= height) {
			return 0;
		}
		return blue[x][y];
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int[][] getRedChannel() {
		return red;
	}

	public int[][] getGreenChannel() {
		return green;
	}

	public int[][] getBlueChannel() {
		return blue;
	}

	public BufferedImage getBufferedImage() {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				java.awt.Color c = new java.awt.Color(red[i][j], green[i][j], blue[i][j]);
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
			return Color.rgb(red[x][y], green[x][y], blue[x][y]);
		}
		return Color.rgb(255, 255, 255);
	}

	public String getHexColor(int x, int y) {
		return String.format("#%02x%02x%02x", red[x][y], green[x][y], blue[x][y]);
	}

	public void saveOn(File file) {
		try {
			System.out.println(file);
			ImageIO.write(this.getBufferedImage(), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveAsRaw(File file) {
	}

	public Color getAverageColor(int x, int y, int recWidth, int recHeight) {
		int avRed = 0;
		int avGreen = 0;
		int avBlue = 0;
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
		return Color.rgb(avRed / size, avGreen / size, avBlue / size);
	}
}
