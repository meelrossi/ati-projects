package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class CreateImageManager {
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
	
	public static BufferedImage getGreyImage() {
		BufferedImage bi = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				java.awt.Color c = new java.awt.Color(i, i, i);
				bi.setRGB(i, j, c.getRGB());
			}
		}
		return bi;
	}
	
	public static List<BufferedImage> getSyntheticImages() {
		BufferedImage blackImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
		BufferedImage redImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
		BufferedImage whiteImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		for (int i = 0; i < IMAGE_SIZE; i++) {
			for (int j = 0; j < IMAGE_SIZE; j++) {
				blackImage.setRGB(i, j, Color.BLACK.getRGB());
				whiteImage.setRGB(i, j, Color.WHITE.getRGB());
				redImage.setRGB(i, j, Color.RED.getRGB());
			}
		}
		images.add(blackImage);
		images.add(whiteImage);
		images.add(redImage);
		return images;
	}
}
