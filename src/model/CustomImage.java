package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;

public abstract class CustomImage {
	protected int width;
	protected int height;

	public CustomImage(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
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

	public abstract BufferedImage getBufferedImage();

	public abstract void setPixelColor(int x, int y, Color color);

	public abstract Color getColor(int x, int y);

	public abstract String getHexColor(int x, int y);

	public abstract Color getAverageColor(int x, int y, int recWidth, int recHeight);
	
	public abstract void normalize();
	
	public abstract void compressionDinamicRange();

}
