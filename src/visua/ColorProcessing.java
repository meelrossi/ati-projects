package visua;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ColorImage;
import visua.model.Color;

public class ColorProcessing {

	public static ColorImage to8ColorsPerChannel(ColorImage img){
		int width = img.getWidth();
		int height = img.getHeight();
		
		double[][] red = matrixTo8Colors(img.getRedChannel());
		double[][] green = matrixTo8Colors(img.getGreenChannel());
		double[][] blue = matrixTo8Colors(img.getBlueChannel());
		
		return new ColorImage(red, green, blue, width, height);
	}

	private static double[][] matrixTo8Colors(double[][] matrix) {
		int width = matrix.length;
		int height = matrix[0].length;
		
		double[][] result = new double[width][height];
		
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				result[i][j] = valueTo8Colors(matrix[i][j]);
			}
		}
		
		return result;
	}

	private static double valueTo8Colors(double d) {
		int multiplier = (int)((d+4)/8);
		return multiplier == 32 ? 255 : multiplier*8;
	}
	
	public static Color getMostUserColor8PerChannel(ColorImage img){
		return getMostUsedColor(to8ColorsPerChannel(img));
	}
	
	public static Color getMostUsedColor(ColorImage img){
		int width = img.getWidth();
		int height = img.getHeight();

		Map<visua.model.Color, Integer> colors = new HashMap<>();
		
		for(int i=0; i<img.getWidth(); i++){
			for(int j=0; j<img.getHeight(); j++){
				visua.model.Color color = new visua.model.Color(img.getRed(i, j), img.getGreen(i, j), img.getBlue(i, j));
				
				if(colors.get(color) != null){
					colors.put(color, colors.get(color)+1);
				} else {
					colors.put(color, 1);
				}
			}
		}
		int max = 0;
		visua.model.Color maxColor = null;
		for(Entry<visua.model.Color, Integer> entry : colors.entrySet()){
			if(entry.getValue() > max){
				maxColor = entry.getKey();
				max = entry.getValue();
			}
		}
		System.out.println("R: "+maxColor.getR()+"G: "+maxColor.getG()+"B: "+maxColor.getB());
		return maxColor;		
	}
		
}
