package matrix_operations.thresholdings;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import utils.Pair;

public class ThreashholdingWithHystesis {

	public static double[][] apply(double[][] matrix, int t1, int t2){
		double[][] result = new double[matrix.length][matrix[0].length];
		List<Pair> hysteresisPoints = new LinkedList<Pair>();
		boolean iterationWithChanges = true;
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				result[i][j] = matrix[i][j];
			}
		}
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				if(result[i][j]>t2){
					result[i][j] = 255;
				} else if(result[i][j]>t2){
					result[i][j] = 0;
				} else {
					hysteresisPoints.add(new Pair(i, j));
				}
			}
		}
		
		while(iterationWithChanges && !hysteresisPoints.isEmpty()){
			iterationWithChanges = false;
			Iterator<Pair> it = hysteresisPoints.iterator();
			while(it.hasNext()){
				Pair p = it.next();
				if(processHysteresis(matrix, p.getX(), p.getY())){
					it.remove();
					iterationWithChanges = true;
				}
			}
		}
		for(Pair p : hysteresisPoints){
			result[p.getX()][p.getY()] = 0;
		}
		
		return result;
	}

	private static boolean processHysteresis(double[][] matrix, int x, int y) {
		int[][] mask = new int[][]{
			{1,1,1},
			{1,1,1},
			{1,1,1}
		};
		if(x == 0){
			for(int i=0; i<2; i++){
				mask[0][i] = 0;
			}
		}
		if(x == matrix.length-1){
			for(int i=0; i<2; i++){
				mask[2][i] = 0;
			}
		}
		if(y == 0){
			for(int i=0; i<2; i++){
				mask[i][0] = 0;
			}
		}
		if(y == matrix[0].length-1){
			for(int i=0; i<2; i++){
				mask[i][2] = 0;
			}
		}
		
		for(int i=0; i<2; i++){
			for(int j=0; j<2; j++){
				if(mask[i][j] == 1 && matrix[x+i-1][y+j-1] == 255){
					matrix[x][y] = 255;
					return true;
				}
			}
		}
		return false;
	}

	
	
}
