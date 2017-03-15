package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageManager {
	private static int IMAGE_SIZE = 200;
	private static int SQUARE_L = 10;
	private static int CIRCLE_D = 160;

	public static BufferedImage getBinaryCircle() {
		BufferedImage circle = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g2d = circle.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setPaint(Color.WHITE);
		g2d.fillOval((IMAGE_SIZE / 2) - (CIRCLE_D / 2), (IMAGE_SIZE / 2) - (CIRCLE_D / 2), CIRCLE_D, CIRCLE_D);
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
}
