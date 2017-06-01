package matrix_operations.non_maximum_suppression;

import matrix_operations.Normalizationer;
import matrix_operations.masks.MaskType;
import matrix_operations.masks.SobelMask;
import matrix_operations.point_to_point.ModulePointToPointOperation;
import matrix_operations.point_to_point.PointToPointOperation;

public class NonMaximumSuppressionAndSobel {

	
	public double[][] sobelAndSuppress(double[][] matrix){
		SobelMask xMask = new SobelMask(MaskType.X);
		SobelMask yMask = new SobelMask(MaskType.Y);
		PointToPointOperation pointToPointOperation = new ModulePointToPointOperation();
		
		double[][] dx = xMask.apply(matrix);
		double[][] dy = yMask.apply(matrix);
		double[][] sobel = pointToPointOperation.apply(dx,dy);
		
		for(int i=1; i < matrix.length-1; i++){
			for(int j=1; j < matrix[0].length-1; j++){
				BorderDirection direction = getBorderDirection(i,j,dx,dy);
				checkForSupression(i,j,sobel,direction);
			}
		}
				
		return sobel;
	}

	private void checkForSupression(int i, int j, double[][] sobel, BorderDirection direction) {
		if(sobel[i][j] <= sobel[i+direction.getDeltaX()][j+direction.getDeltaY()]){
			sobel[i][j] = 0;
			return;
		}
		if(sobel[i][j] <= sobel[i-direction.getDeltaX()][j-direction.getDeltaY()]){
			sobel[i][j] = 0;
			return;
		}
	}

	private BorderDirection getBorderDirection(int x, int y, double[][] dx, double[][] dy) {
		double angle = Math.atan((dy[x][y]+0.01)/(dx[x][y]+0.01));
		angle = (angle * 180 / Math.PI);
		if(angle < 0){
			angle += 180;
		}
		if(angle < 22.5 || angle > 157.5){
			return BorderDirection.HORIZONTAL;
		} else if(angle < 67.5){
			return BorderDirection.ASCENDING;
		} else if(angle < 112.5){
			return BorderDirection.VERTICAL;
		} else {
			return BorderDirection.DESCENDING;
		}
	}

	
}


