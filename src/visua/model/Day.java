package visua.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day implements Comparable{

	private int day;
	private List<ImageRepresentation> images;
	
	public Day(int day){
		this.day = day;
		images = new ArrayList<>();
	}

	
	
	public int getDay() {
		return day;
	}



	public void setDay(int day) {
		this.day = day;
	}



	public List<ImageRepresentation> getImages() {
		return images;
	}



	public void setImages(List<ImageRepresentation> images) {
		this.images = images;
	}



	@Override
	public int compareTo(Object arg0) {
		Day dayObj = (Day) arg0;
		return day - dayObj.getDay();
	}



	public void addFlatenRepresentation(FlatenRepresentation fr) {
		images.add(new ImageRepresentation(fr.getName(), fr.getMaxColor()));
	}



	public void sort() {
		images.sort(new Comparator<ImageRepresentation>(){

			@Override
			public int compare(ImageRepresentation o1, ImageRepresentation o2) {
				return o1.getMaxColor().compareTo(o2.getMaxColor());
			}
			
		});
	}
	
	
	
}
