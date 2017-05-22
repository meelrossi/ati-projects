package hough;

import java.util.LinkedList;
import java.util.List;

import model.ColorImage;
import utils.Pair;

public class LinealHough {

	private List<Pair> whitePoints;

	public LinealHough() {
	}


	public ColorImage getImageWithLine(ColorImage image, int titaSteps, int roSteps, double epsilon){
		getWhitePoints(image.getRedChannel());
		
		int[][] linesScore = new int[titaSteps][roSteps];
		double[] titaValues = new double[titaSteps];
		double[] roValues = new double[roSteps];

		double tita1 = -Math.PI/2;
		double tita2 = Math.PI/2;
		
		double ro1 = 0;
		double ro2 = Math.max(image.getHeight(), image.getWidth()) * Math.sqrt(2);
		
		
		double titaStep = (tita2-tita1)/(titaSteps-1);
		titaValues[0] = tita1;
		for(int i=1; i<titaSteps; i++){ 
			titaValues[i] = titaValues[i-1] + titaStep;
		}
		double roStep = (ro2-ro1)/(roSteps-1);
		roValues[0] = ro1;
		for(int i=1; i<roSteps; i++){
			roValues[i] = roValues[i-1] + roStep;
		}
		
		for(int i=0; i<linesScore.length; i++){
			for(int j=0; j<linesScore[0].length; j++){
				for(Pair p : whitePoints){
					if(belongsToLine(titaValues[i], roValues[j], epsilon, p.getX(),p.getY())){
						linesScore[i][j]++;
					}
				}
			}
		}
		
		int maxLine = getMax(linesScore);
		double linesThreashhold = 0.8 * maxLine;
		
		double[][] newImage = cloneMatrix(image.getRedChannel());
		
		for(int i=0; i<linesScore.length; i++){
			for(int j=0; j<linesScore[0].length; j++){
				if(linesScore[i][j] > linesThreashhold){
					drawLine(titaValues[i], roValues[j], newImage);
				}
			}
		}
		
		return new ColorImage(newImage, image.getWidth(), image.getHeight());
	}
	
	

	private void drawLine(double tita, double ro, double[][] newImage) {
		for(int x=0; x<newImage.length; x++){
			int y = (int) Math.round((ro - x*Math.cos(tita))/Math.sin(tita));
			if(y>=0 && y<=newImage[0].length){
				newImage[x][y] = 128;
			}
		}
	}


	private double[][] cloneMatrix(double[][] matrix) {
		double[][] newMatrix = new double[matrix.length][matrix[0].length];
		
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				newMatrix[i][j] = matrix[i][j];
			}
		}
		
		return newMatrix;
	}


	private int getMax(int[][] matrix) {
		int max = 0 ;
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				if(matrix[i][j] > max){
					max = matrix[i][j];
				}
			}
		}
		return max;
	}


	private boolean belongsToLine(double tita, double ro, double epsilon, int x, int y) {
		return Math.abs(ro - x*Math.cos(tita) - y*Math.sin(tita)) <= epsilon;
	}


	private void getWhitePoints(double[][] matrix) {
		whitePoints = new LinkedList<>();
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				if(matrix[i][j] == 255){
					whitePoints.add(new Pair(i,j));
				}
			}
		}
	}
	
}
