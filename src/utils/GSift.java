package utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;

import model.ColorImage;

public class GSift {

	private static GSift instance;
	private Map<String, MatOfKeyPoint> trainingImagesDescriptors = new HashMap<String, MatOfKeyPoint>();
	private DescriptorExtractor descriptorExtractor;
	private int gridSize = 10;

	private GSift() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		File folder = new File("resources/yaleBfaces/subset0");
		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			trainingImagesDescriptors.put(file.getName(), getDescriptors(file));
		}
	}
	
	public static ColorImage getImage(String name) {
		File file = new File("resources/yaleBfaces/subset0/" + name);
		ColorImage img = null;
		try {
			img = new ColorImage(ImageIO.read(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}

	public static GSift getInstance() {
		if (instance == null) {
			instance = new GSift();
		}
		return instance;
	}

	public String findImage(File imageFile) {
		MatOfKeyPoint descriptors = getDescriptors(imageFile);
		double minDistance = Double.MAX_VALUE;
		String fileName = null;
		for (Entry<String, MatOfKeyPoint> trainingImage : trainingImagesDescriptors.entrySet()) {
			double dist = getDistance(descriptors, trainingImage.getValue());
			if (dist < minDistance) {
				minDistance = dist;
				fileName = trainingImage.getKey();
			}
		}

		return fileName;
	}

	public MatOfKeyPoint getDescriptors(File file) {
		Mat image = Highgui.imread(file.getAbsolutePath());
		MatOfKeyPoint keyPoints = getKeyPoints(image);
		MatOfKeyPoint descriptors = new MatOfKeyPoint();
		descriptorExtractor.compute(image, keyPoints, descriptors);
		return descriptors;
	}

	public MatOfKeyPoint getKeyPoints(Mat image) {

		List<KeyPoint> keyPoints = new LinkedList<KeyPoint>();

		int width = image.width();
		int height = image.height();

		int wStep = width / (gridSize + 2);
		int hStep = height / (gridSize + 2);

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				KeyPoint kp = new KeyPoint(wStep * (i + 1), hStep * (j + 1), 1);
				keyPoints.add(kp);
			}
		}
		MatOfKeyPoint result = new MatOfKeyPoint();
		result.fromList(keyPoints);
		return result;
	}

	public double getDistance(MatOfKeyPoint descriptors1, MatOfKeyPoint descriptors2) {
		double distance = 0;
		for (int i = 0; i < (gridSize * gridSize); i++) {
			for (int j = 0 ; j < 128; j++) {
				distance += Math.pow(descriptors1.get(i,j)[0] - descriptors2.get(i,j)[0], 2);
			}
		}
		return Math.sqrt(distance);
	}
}
