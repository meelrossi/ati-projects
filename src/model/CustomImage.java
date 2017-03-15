package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;

public class CustomImage {
	private BufferedImage bufferedImage;
	
	public CustomImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}
	
	public int[][] getMatrixOfImage() {
	    int width = bufferedImage.getWidth(null);
	    int height = bufferedImage.getHeight(null);
	    int[][] pixels = new int[width][height];
	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	            pixels[i][j] = bufferedImage.getRGB(i, j);
	        }
	    }
	    return pixels;
	}
	
	public void setPixelColor(int x, int y, Color color) {
		int red = (int) (color.getRed() * 255);
		int green = (int) (color.getGreen() * 255);
		int blue = (int) (color.getBlue() * 255);
		java.awt.Color c = new java.awt.Color(red, green, blue);
		bufferedImage.setRGB(x, y, c.getRGB());
	}
	
	public Color getColor(int x, int y) {
		Integer color = bufferedImage.getRGB(x, y);
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = (color >> 0) & 0xFF;
		return Color.rgb(red, green, blue);
	}
	
	public String getHexColor(int x, int y) {
		Integer color = bufferedImage.getRGB(x, y);
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = (color >> 0) & 0xFF;
		return String.format("#%02x%02x%02x", red, green, blue);
	}
	
	public void saveOn(File file) {
		try {
			ImageIO.write(this.bufferedImage, "", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAsRaw(File file) {
	}
}
