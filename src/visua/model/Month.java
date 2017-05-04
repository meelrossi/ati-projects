package visua.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Month implements Comparable {

	private int month;
	private List<Day> days;
	
	public Month(int month){
		this.month = month;
		days = new ArrayList<>();
	}
	
	
	
	public int getMonth() {
		return month;
	}



	public void setMonth(int month) {
		this.month = month;
	}



	public List<Day> getDays() {
		return days;
	}



	public void setDays(List<Day> days) {
		this.days = days;
	}



	@Override
	public int compareTo(Object arg0) {
		Month monthObj = (Month) arg0;
		return month - monthObj.getMonth();
	}



	public void addFlatenRepresentation(FlatenRepresentation fr) {
		int imageDay = fr.getDay();
		for(Day d : days){
			if(d.getDay() == imageDay){
				d.addFlatenRepresentation(fr);
				return;
			}
		}
		Day day = new Day(imageDay);
		days.add(day);
		day.addFlatenRepresentation(fr);
	}



	public void sort() {
		for(Day d : days){
			d.sort();
		}
		days.sort(new Comparator<Day>(){

			@Override
			public int compare(Day o1, Day o2) {
				return o1.getDay() - o2.getDay();
			}
			
		});
	}
	
	
}
