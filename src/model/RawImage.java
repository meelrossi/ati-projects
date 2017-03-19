package model;

import java.awt.image.BufferedImage;

import javafx.scene.paint.Color;

public class RawImage extends CustomImage {
	private int[][] grey;
	private int width;
	private int height;
	
	public RawImage(int[][] grey, int width, int height) {
		super(width, height);
		this.grey = grey;
	}
	
	public int getGrey(int x, int y) {
		return grey[x][y];
	}
	
	public BufferedImage getBufferedImage() {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				java.awt.Color c = new java.awt.Color(grey[i][j], grey[i][j], grey[i][j]);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}

	@Override
	public void setPixelColor(int x, int y, Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHexColor(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getAverageColor(int x, int y, int recWidth, int recHeight) {
		// TODO Auto-generated method stub
		return null;
	}
}
