package active_borders;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import model.ColorImage;
import sun.util.resources.cldr.aa.CurrencyNames_aa_ER;
import utils.Pair;

public class PixelExchangeBorder {

	private ColorImage currentImage;
	
	private Map<Pair, Pixel> pixels;
	private List<Pixel> lin;
	private List<Pixel> lout;
	
	private Color insideColor;
	private Color outsideColor;
	
	private static int WINDOWS_SIZE = 5;
	private static double SIGMA = 1;
	private static int Na = 1000;
	
	public PixelExchangeBorder(ColorImage initialImage, int initialX, int initialY, int rectWidth, int rectHeight){
		currentImage = initialImage;
		initializeData(initialX, initialY, rectWidth, rectHeight);
	}
	
	
	public void setNewImage(ColorImage image){
		currentImage = image;
	}
	
	
	public void rearrangeBorders(){
		
	}
	
	
	
	private void initializeData(int initialX, int initialY, int rectWidth, int rectHeight) {
		pixels = new HashMap<>();
		lin = new LinkedList<>();
		lout = new LinkedList<>();
		
		double[][] red = currentImage.getRedChannel();
		double[][] green = currentImage.getGreenChannel();
		double[][] blue = currentImage.getBlueChannel();
		
		int outCount = 0;
		int inCount = 0;
		
		double[] inColor = new double[3];
		double[] outColor = new double[3];
		
		for(int i=0; i<currentImage.getWidth(); i++){
			for(int j=0; j<currentImage.getHeight(); j++){
				
				if(isExternalBorder(i,j,initialX,initialY,rectWidth,rectHeight)){
					Pixel p = new Pixel(i,j, PixelType.BORDER_OUT);
					pixels.put(new Pair(i,j), p);
					lout.add(p);
					outColor[0] += red[i][j];
					outColor[1] += green[i][j];
					outColor[2] += blue[i][j];
					outCount++;
				} else if(isInternalBorder(i,j,initialX,initialY,rectWidth,rectHeight)){
					Pixel p = new Pixel(i,j, PixelType.BORDER_IN) ;
					pixels.put(new Pair(i,j), p);
					lin.add(p);
					inColor[0] += red[i][j];
					inColor[1] += green[i][j];
					inColor[2] += blue[i][j];
					inCount++;
				} else if(isInternal(i,j,initialX,initialY,rectWidth,rectHeight)){
					pixels.put(new Pair(i,j), new Pixel(i,j, PixelType.IN));
					inColor[0] += red[i][j];
					inColor[1] += green[i][j];
					inColor[2] += blue[i][j];
					inCount++;
				} else {
					pixels.put(new Pair(i,j), new Pixel(i,j, PixelType.OUT));
					outColor[0] += red[i][j];
					outColor[1] += green[i][j];
					outColor[2] += blue[i][j];
					outCount++;
				}
			}
			
			insideColor = Color.rgb((int)inColor[0]/inCount,(int)inColor[1]/inCount,(int)inColor[2]/inCount);
			outsideColor = Color.rgb((int)outColor[0]/outCount,(int)outColor[1]/outCount,(int)outColor[2]/outCount);
					
		}
		
	}

	private boolean isInternal(int x, int y,int initialX, int initialY, int rectWidth, int rectHeight) {
		return (x >= initialX+2 && x <= initialX+rectWidth-2) && (y >= initialY+2 && y <= initialY+rectHeight-2);
	}

	private boolean isInternalBorder(int x, int y,int initialX, int initialY, int rectWidth, int rectHeight) {
		if((x == initialX+1 || x == initialX+rectWidth-1) && (y >= initialY+1 && y <= initialY+rectHeight-1)){
			return true;
		}
		if((y == initialY+1 || y == initialY+rectHeight-1) && (x >= initialX+1 && x <= initialX+rectWidth-1 )){
			return true;
		}
		return false;
	}

	private boolean isExternalBorder(int x, int y,int initialX, int initialY, int rectWidth, int rectHeight) {
		if((x == initialX || x == initialX+rectWidth) && (y >= initialY && y <= initialY+rectHeight)){
			return true;
		}
		if((y == initialY || y == initialY+rectHeight) && (x >= initialX && x <= initialX+rectWidth )){
			return true;
		}
		return false;
	}

	private double ForceDLog(int x, int y){
		Color pColor = currentImage.getColor(x, y);
		return Math.log(subsAndApplyNorm2(pColor, outsideColor)/subsAndApplyNorm2(pColor, insideColor));
	}
	
	private double ForceD(int x, int y){
		Color pColor = currentImage.getColor(x, y);
		return 1 - subsAndApplyNorm2(pColor, insideColor)/256 > 0.75 ? 1 : -1 ;
	}

	private double ForceS(int x, int y){
		double sum = 0;
		int move = (WINDOWS_SIZE - 1) / 2;
		for (int i = x - move; i <= x + move; i++) {
			for (int j = y - move; j <= y + move; j++) {
				if (i >= 0 && i < currentImage.getWidth() && j >= 0 && j < currentImage.getHeight() && !(x == i && y == j)) {
					int dx = Math.abs(x - i);
					int dy = Math.abs(y - j);
					sum += gaussianValue(dx, dy) * pixels.get(new Pair(i,j)).getType().getPhi();
				}
			}
		}
		return sum;
	}
	
	private double gaussianValue(double x, double y) {
		return 1 / (2 * Math.PI * SIGMA*SIGMA) * Math.exp(-(x*x + y*y) / (SIGMA * SIGMA));
	}

	private double subsAndApplyNorm2(Color c1, Color c2){
		double rSquared = Math.pow(c1.getRed() - c2.getRed(), 2);
		double gSquared = Math.pow(c1.getGreen() - c2.getGreen(), 2);
		double bSquared = Math.pow(c1.getBlue() - c2.getBlue(), 2);
		return Math.sqrt(rSquared+gSquared+bSquared);
	}
	
	public ColorImage getImageWithBorder(){
		double[][] red = new double[currentImage.getWidth()][currentImage.getHeight()];
		double[][] green = new double[currentImage.getWidth()][currentImage.getHeight()];
		double[][] blue = new double[currentImage.getWidth()][currentImage.getHeight()];
		
		for(int i=0; i<currentImage.getWidth(); i++){
			for(int j=0; j<currentImage.getHeight(); j++){
				red[i][j] = currentImage.getRed(i, j);
				green[i][j] = currentImage.getGreen(i, j);
				blue[i][j] = currentImage.getBlue(i, j);
			}
		}
		for(Pixel p : lin){
			red[p.getX()][p.getY()] = 255;
			green[p.getX()][p.getY()] = 0;
			blue[p.getX()][p.getY()] = 0;
		}
		for(Pixel p : lout){
			red[p.getX()][p.getY()] = 0;
			green[p.getX()][p.getY()] = 0;
			blue[p.getX()][p.getY()] = 255;
		}
		
		return new ColorImage(red, green, blue, currentImage.getWidth(), currentImage.getHeight());
	}
	
	
	
}