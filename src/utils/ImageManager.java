package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.chart.XYChart;
import model.ColorImage;
import visua.ColorProcessing;

public class ImageManager {

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
				if (img.getGrey(i, j) > limit) {
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

	public static XYChart.Series<String, Number> getHistogramSeries(double[][] channel, int width, int height) {
		double[] pixelColor = ImageManager.getHistogramData(channel, width, height);
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		for (int k = 0; k < 256; k++) {
			series.getData().add(new XYChart.Data<String, Number>("" + k, pixelColor[k]));
		}
		return series;
	}
	
	public static double[] getHistogramData (double[][] channel, int width, int height) {
		double[] pixelColor = new double[256];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int color = (int) channel[i][j];
				pixelColor[color] += 1;
			}
		}
		for (int k = 0; k < 256; k++) {
			pixelColor[k] = pixelColor[k] / (width * height);
		}
		return pixelColor;
	}

	public static double[] getAcumulativeHistogramData(double[][] channel, int width, int height){
		double[] pixelColor = getHistogramData(channel, width, height);
		for(int i=1; i < pixelColor.length; i++){
			pixelColor[i] += pixelColor[i-1];
		}
		return pixelColor;
	}
	
	public static double[] getAcumulativeMean (double[][] channel, int width, int height){
		double[] pixelColor = getHistogramData(channel, width, height);
		pixelColor[0] = 0;
		for(int i=1; i < pixelColor.length; i++){
			pixelColor[i] = pixelColor[i-1] + pixelColor[i]*i;
		}
		return pixelColor;
	}
	
	public static ColorImage calculateContrast(ColorImage img, Double r1, Double r2, Double s1, Double s2) {
		int width = img.getWidth();
		int height = img.getHeight();
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = contrastPixel(img.getRed(i, j), r1, r2, s1, s2);
				green[i][j] = contrastPixel(img.getGreen(i, j), r1, r2, s1, s2);
				blue[i][j] = contrastPixel(img.getBlue(i, j), r1, r2, s1, s2);
			}
		}
		return new ColorImage(red, green, blue, width, height);
	}

	public static double contrastPixel(double pixel, Double r1, Double r2, Double s1, Double s2) {
		if (pixel <= r1) {
			return contrastFunction(pixel, 0, 0, r1, s1);
		} else if (pixel <= r2) {
			return contrastFunction(pixel, r1, s1, r2, s2);
		} else {
			return contrastFunction(pixel, r2, s2, 255, 255);
		}
	}

	public static double contrastFunction(double pixel, double origin_x, double origin_y, double end_x, double end_y) {
		double m = (origin_y - end_y) / (origin_x - end_x);
		double b = origin_y - m * origin_x;
		return m * pixel + b;
	}

	public static ColorImage powerImage(ColorImage img, double value) {
		ColorProcessing.getMostUserColor8PerChannel(img);
		return ColorProcessing.to8ColorsPerChannel(img);
	}
	
	public static int[] getHistogramDataEqualize(double[][] channel, int width, int height) {
		double[] pixelColor = ImageManager.getHistogramData(channel, width, height);
		for (int i = 1; i < 256; i++) {
			pixelColor[i] = pixelColor[i] + pixelColor[i-1];
		}
		int[] equalizeColor = new int[256];
		double min = pixelColor[0];
		for (int j = 0; j < 256; j++) {
			equalizeColor[j] = (int) (((pixelColor[j] - min) / (1 + min) * 255) + 0.5);
		}
		return equalizeColor;
	}
	
	public static ColorImage equalizeImage(ColorImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		int[] redData = ImageManager.getHistogramDataEqualize(img.getRedChannel(), width, height);
		int[] greenData = ImageManager.getHistogramDataEqualize(img.getRedChannel(), width, height);
		int[] blueData = ImageManager.getHistogramDataEqualize(img.getRedChannel(), width, height);
		double[][] red = new double[width][height];
		double[][] green = new double[width][height];
		double[][] blue = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				red[i][j] = redData[(int)img.getRed(i, j)];
				green[i][j] = greenData[(int)img.getGreen(i, j)];
				blue[i][j] = blueData[(int)img.getBlue(i, j)];
			}
		}
		return new ColorImage(red, blue, green, width, height);
	}

	public static ColorImage readFromRaw(File file, int width, int height) {
		DataInputStream dis;
		double[][] image = new double[width][height];
		try {
			dis = new DataInputStream(new FileInputStream(file));
	        for (int i = 0; i < height; i++) {
	        	for(int j = 0; j < width; j++) {
	              image[j][i] = dis.readByte();
	              if (image[j][i] < 0) {
	            	  image[j][i] += 256;
	              }
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ColorImage(image, width, height);
	}
}
