package harris;

import java.util.ArrayList;
import java.util.List;

import matrix_operations.BasicMatrixOp;
import matrix_operations.masks.MaskType;
import matrix_operations.masks.SobelMask;
import matrix_operations.point_to_point.MultiplyPointToPointOperation;
import matrix_operations.point_to_point.PointToPointOperation;
import matrix_operations.point_to_point.SumPointToPointOperation;
import model.ColorImage;
import utils.Pair;

public class HarrisBorderDetector {

	public static double K = 0.04;
	
	public static ColorImage getHarrisBorders(ColorImage image){
		double[][] band = image.getRedChannel();
		
		List<Pair> borders = getBorders(band);
		
		double[][] redResult = new double[image.getWidth()][image.getHeight()];
		double[][] greenResult = new double[image.getWidth()][image.getHeight()];
		double[][] blueResult = new double[image.getWidth()][image.getHeight()];
		
		for(int i=0; i<image.getWidth(); i++){
			for(int j=0; j<image.getHeight(); j++){
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
		
		
		return new ColorImage(redResult, greenResult, blueResult, image.getWidth(), image.getHeight());
	}

	private static List<Pair> getBorders(double[][] band) {
		List<Pair> borders = new ArrayList<>();
		
		SobelMask xMask = new SobelMask(MaskType.X);
		
		SobelMask yMask = new SobelMask(MaskType.Y);
		
		double[][] ix = xMask.apply(band);
		double[][] iy = yMask.apply(band);
		
		PointToPointOperation multiplyP2pOp = new MultiplyPointToPointOperation();
		PointToPointOperation sumP2pOp = new SumPointToPointOperation();
		
		double[][] ix2 = multiplyP2pOp.apply(ix, ix);
		double[][] iy2 = multiplyP2pOp.apply(iy, iy);
		
		double[][] ixy = multiplyP2pOp.apply(ix, iy);
		
		double[][] ix2byiy2 = multiplyP2pOp.apply(ix2, iy2);
		
		double[][] minusixy2 = BasicMatrixOp.scalarMult(multiplyP2pOp.apply(ixy, ixy), -1);
		
		double[][] firstTerm = sumP2pOp.apply(ix2byiy2, minusixy2);
		
		double[][] ix2sumiy2 = sumP2pOp.apply(ix2, iy2);
		
		double[][] ix2sumiy22 = multiplyP2pOp.apply(ix2sumiy2, ix2sumiy2);
		
		double[][] multk = BasicMatrixOp.scalarMult(ix2sumiy22, -K);
		
		double[][] r = sumP2pOp.apply(firstTerm, multk);
		
		for(int i=0; i<r.length; i++){
			for(int j=0; j<r[0].length; j++){
				if(r[i][j] > 0){
					borders.add(new Pair(i,j));
				}
			}
		}
		
		return borders;
		
	}
	
}
