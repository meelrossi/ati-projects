package harris;

import java.util.ArrayList;
import java.util.List;

import matrix_operations.BasicMatrixOp;
import matrix_operations.masks.LoGMask;
import matrix_operations.masks.MaskType;
import matrix_operations.masks.SobelMask;
import matrix_operations.point_to_point.MultiplyPointToPointOperation;
import matrix_operations.point_to_point.PointToPointOperation;
import matrix_operations.point_to_point.SumPointToPointOperation;
import model.ColorImage;
import utils.Pair;

public class HarrisBorderDetector {


	private static int WINDOWS_SIZE = 7;
	private static double SIGMA = 2;
	public static double K = 0.04;
	
	public static ColorImage getHarrisBorders(ColorImage image){
		double[][] band = image.getRedChannel();
		
		List<Pair> borders = getBorders(band);
		
		double[][] redResult = new double[image.getWidth()][image.getHeight()];
		double[][] greenResult = new double[image.getWidth()][image.getHeight()];
		double[][] blueResult = new double[image.getWidth()][image.getHeight()];
		
		for(int i=1; i<image.getWidth()-1; i++){
			for(int j=1; j<image.getHeight()-1; j++){
				redResult[i][j] = band[i][j];
				greenResult[i][j] = band[i][j];
				blueResult[i][j] = band[i][j];
			}
		}
		
		for(Pair p: borders){
			redResult[p.getX()][p.getY()] = 255;
			greenResult[p.getX()][p.getY()] = 0;
			blueResult[p.getX()][p.getY()] = 0;
		}
		
		System.out.println(borders.size());
		return new ColorImage(redResult, greenResult, blueResult, image.getWidth(), image.getHeight());
	}

	private static List<Pair> getBorders(double[][] band) {
		List<Pair> borders = new ArrayList<>();
		
		SobelMask xMask = new SobelMask(MaskType.X);
		
		SobelMask yMask = new SobelMask(MaskType.Y);
		
		double[][] ix = xMask.apply(band);
		double[][] iy = yMask.apply(band);
		
		PointToPointOperation multiplyP2pOp = new MultiplyPointToPointOperation();
		
		double[][] ix2 = soften(multiplyP2pOp.apply(ix, ix));
		double[][] iy2 = soften(multiplyP2pOp.apply(iy, iy));
		double[][] ixy = soften(multiplyP2pOp.apply(ix, iy));
		
		double[][] r = new double[band.length][band[0].length];
		
		for(int i=0; i<r.length; i++){
			for(int j=0; j<r[0].length; j++){
				double ix2a = ix2[i][j];
				double iy2a = iy2[i][j];
				double ixya = ixy[i][j];
				r[i][j] = (ix2a * iy2a - ixya * ixya) - K * Math.pow(ix2a+iy2a, 2);
			}
		}
		
		
		double max = -100000000;
		
		for(int i=0; i<r.length; i++){
			for(int j=0; j<r[0].length; j++){
				if(r[i][j] > 0){
					if(r[i][j] > max){
						max = r[i][j];
					}
				}
			}
		}
		System.out.println(max);
		for(int i=0; i<r.length; i++){
			for(int j=0; j<r[0].length; j++){
				if(r[i][j] > max*0.1){
					borders.add(new Pair(i,j));
				}
			}
		}
		
		
		return borders;
		
	}

	private static double[][] soften(double[][] matrix) {
		double[][] result =  new double[matrix.length][matrix[0].length];
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				if(i<(WINDOWS_SIZE-1)/2+1 || i>matrix.length-(WINDOWS_SIZE-1)/2-1 || j<(WINDOWS_SIZE-1)/2+1 || j>matrix[0].length-(WINDOWS_SIZE-1)/2-1 ){
					result[i][j] = matrix[i][j];
				} else {
					result[i][j] = softenValue(i,j,matrix);					
				}
			}
		}
		return result;
	}
	
	private static double softenValue(int x, int y, double[][] matrix) {
		double sum = 0;
		int move = (WINDOWS_SIZE - 1) / 2;
		for (int i = x - move; i <= x + move; i++) {
			for (int j = y - move; j <= y + move; j++) {
				if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length) {
					int dx = Math.abs(x - i);
					int dy = Math.abs(y - j);
					sum += gaussianValue(dx, dy) * matrix[i][j];
				}
			}
		}
		return sum;
	}

	private static double gaussianValue(double x, double y) {
		return (1 / (2 * Math.PI * SIGMA * SIGMA)) * (Math.exp(-(x * x + y * y) / 2 * (SIGMA * SIGMA)));
	}
	
}
