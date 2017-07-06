package utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;

public class SiftMatcher {

	public static void match(File image1File, File image2File) throws IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat matImage1 = Highgui.imread(image1File.getAbsolutePath());
		Mat matImage2 = Highgui.imread(image2File.getAbsolutePath());

		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SIFT);
		DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		Scalar keyPointColor = new Scalar(0, 255, 0);

		MatOfKeyPoint keyPoints1 = new MatOfKeyPoint();
		MatOfKeyPoint descriptors1 = new MatOfKeyPoint();
		featureDetector.detect(matImage1, keyPoints1);
		descriptorExtractor.compute(matImage1, keyPoints1, descriptors1);
		Mat image1WithKeypoints = new Mat(matImage1.rows(), matImage1.cols(), Highgui.CV_LOAD_IMAGE_COLOR);
		Features2d.drawKeypoints(matImage1, keyPoints1, image1WithKeypoints, keyPointColor, 0);
		Highgui.imwrite("image1WithKeyPoints.jpg", image1WithKeypoints);

		MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
		MatOfKeyPoint descriptors2 = new MatOfKeyPoint();
		featureDetector.detect(matImage2, keyPoints2);
		descriptorExtractor.compute(matImage2, keyPoints2, descriptors2);
		System.out.println(descriptors2.row(1));
		Mat image2WithKeypoints = new Mat(matImage2.rows(), matImage2.cols(), Highgui.CV_LOAD_IMAGE_COLOR);
		Features2d.drawKeypoints(matImage2, keyPoints2, image2WithKeypoints, keyPointColor, 0);
		Highgui.imwrite("image2WithKeyPoints.jpg", image2WithKeypoints);

		Mat matchoutput = new Mat(matImage2.rows() * 2, matImage2.cols() * 2, Highgui.CV_LOAD_IMAGE_COLOR);
		Scalar matchestColor = new Scalar(0, 0, 255);

		List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
		DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);

		MatOfDMatch matchesRaw = new MatOfDMatch();
		descriptorMatcher.match(descriptors1, descriptors2, matchesRaw);
		descriptorMatcher.knnMatch(descriptors1, descriptors2, matches, 2);

		LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();

		float nndrRatio = 0.7f;

		for (int i = 0; i < matches.size(); i++) {
			MatOfDMatch matofDMatch = matches.get(i);
			DMatch[] dmatcharray = matofDMatch.toArray();
			DMatch m1 = dmatcharray[0];
			DMatch m2 = dmatcharray[1];

			if (m1.distance <= m2.distance * nndrRatio) {
				goodMatchesList.addLast(m1);

			}
		}

		if (goodMatchesList.size() >= (0.3 * Math.min(keyPoints2.toList().size(), keyPoints1.toList().size()))) {
			System.out.println("Same image");
			System.out.println("Matches: " + goodMatchesList.size());
		} else {
			System.out.println("Different Image");
		}
		List<KeyPoint> objKeypointlist = keyPoints1.toList();
		List<KeyPoint> scnKeypointlist = keyPoints2.toList();

		LinkedList<Point> objectPoints = new LinkedList<>();
		LinkedList<Point> scenePoints = new LinkedList<>();

		for (int i = 0; i < goodMatchesList.size(); i++) {
			objectPoints.addLast(objKeypointlist.get(goodMatchesList.get(i).queryIdx).pt);
			scenePoints.addLast(scnKeypointlist.get(goodMatchesList.get(i).trainIdx).pt);
		}


		MatOfDMatch goodMatches = new MatOfDMatch();
		goodMatches.fromList(goodMatchesList);

		Features2d.drawMatches(matImage1, keyPoints1, matImage2, keyPoints2, goodMatches, matchoutput, matchestColor,
				keyPointColor, new MatOfByte(), 2);

		Highgui.imwrite("matchoutput.jpg", matchoutput);
		
		System.out.println("Raw matches amount: " + matchesRaw.toArray().length);
		System.out.println("Good matches amount: " + goodMatchesList.size());
		return;
	}
}