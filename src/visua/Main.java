package visua;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import model.ColorImage;
import visua.model.FlatenRepresentation;
import visua.model.Year;

public class Main {

	public static void main(String[] args) throws IOException {
		String basePath = "C:\\cygwin\\Camera\\";
		String fileNames = "C:\\cygwin\\Camera\\hola.txt";
		Scanner sc = new Scanner(new File(fileNames));
		List<FlatenRepresentation> pictures = new ArrayList<>();
		while(sc.hasNextLine()){
			String pictureName = sc.nextLine();
			System.out.println(pictureName);
			File file = new File(basePath+pictureName);
			ColorImage img = new ColorImage(ImageIO.read(file));
			visua.model.Color maxColor = ColorProcessing.getMostUserColor8PerChannel(img);
			pictures.add(new FlatenRepresentation(maxColor, pictureName));
		}
		Year year = new Year(2017);
		for(FlatenRepresentation fr: pictures){
			year.addFlatenRepresentation(fr);
		}
		year.sort();
		Gson gson = new Gson();
		try{
		    PrintWriter writer = new PrintWriter("32colors.json", "UTF-8");
		    writer.println(gson.toJson(year));
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		System.out.println("FINISHED");
	}
	
}
